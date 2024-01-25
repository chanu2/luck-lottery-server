package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.credential.domain.RefreshTokenRedisEntity;
import uttugseuja.lucklotteryserver.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import uttugseuja.lucklotteryserver.domain.credential.exception.RefreshTokenExpiredException;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.request.RegisterRequest;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.AuthTokensResponse;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.CheckRegisteredResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.user.domain.repository.UserRepository;
import uttugseuja.lucklotteryserver.global.exception.InvalidTokenException;
import uttugseuja.lucklotteryserver.global.exception.UserNotFoundException;
import uttugseuja.lucklotteryserver.global.security.JwtTokenProvider;
import uttugseuja.lucklotteryserver.global.utils.user.UserUtils;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CredentialService {

    private final UserRepository userRepository;
    private final OauthFactory oauthFactory;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisEntityRepository refreshTokenRedisEntityRepository;
    private final UserUtils userUtils;

    public CheckRegisteredResponse getUserAvailableRegister(String token, OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        OauthStrategy oauthstrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthstrategy.getOIDCDecodePayload(token);
        Boolean isRegistered = !checkUserCanRegister(oidcDecodePayload, oauthProvider);
        return new CheckRegisteredResponse(isRegistered);
    }

    private Boolean checkUserCanRegister(
            OIDCDecodePayload oidcDecodePayload, OauthProvider oauthProvider) {
        Optional<User> user =
                userRepository.findByOauthIdAndOauthProvider(
                        oidcDecodePayload.getSub(), oauthProvider.getValue());
        return user.isEmpty();
    }

    public AuthTokensResponse loginUserByOCIDToken(String token, OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        User user =
                userRepository
                        .findByOauthIdAndOauthProvider(
                                oidcDecodePayload.getSub(), oauthProvider.getValue())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole());

        String refreshToken = generateRefreshToken(user.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public AuthTokensResponse registerUserByOCIDToken(
            String token, RegisterRequest registerUserRequest, OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        User user =
                User.builder()
                        .oauthProvider(oauthProvider.getValue())
                        .oauthId(oidcDecodePayload.getSub())
                        .email(oidcDecodePayload.getEmail())
                        .profilePath(oidcDecodePayload.getProfile())
                        .nickname(registerUserRequest.getNickname())
                        .build();
        userRepository.save(user);

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole());
        String refreshToken = generateRefreshToken(user.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokensResponse tokenRefresh(String requestRefreshToken) {

        Optional<RefreshTokenRedisEntity> entityOptional =
                refreshTokenRedisEntityRepository.findByRefreshToken(requestRefreshToken);

        RefreshTokenRedisEntity refreshTokenRedisEntity =
                entityOptional.orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);

        Long userId = jwtTokenProvider.parseRefreshToken(requestRefreshToken);

        if (!userId.toString().equals(refreshTokenRedisEntity.getId())) {
            throw InvalidTokenException.EXCEPTION;
        }

        User user = userUtils.getUserById(userId);

        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        String refreshToken = generateRefreshToken(userId);

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateRefreshToken(Long userId) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);
        Long tokenExpiredAt = jwtTokenProvider.getRefreshTokenTTlSecond();
        RefreshTokenRedisEntity build =
                RefreshTokenRedisEntity.builder()
                        .id(userId.toString())
                        .refreshTokenTtl(tokenExpiredAt)
                        .refreshToken(refreshToken)
                        .build();
        refreshTokenRedisEntityRepository.save(build);
        return refreshToken;
    }

}

