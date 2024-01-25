package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uttugseuja.lucklotteryserver.domain.credential.domain.RefreshTokenRedisEntity;
import uttugseuja.lucklotteryserver.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.CheckRegisteredResponse;
import uttugseuja.lucklotteryserver.domain.user.domain.User;
import uttugseuja.lucklotteryserver.domain.user.domain.repository.UserRepository;
import uttugseuja.lucklotteryserver.global.security.JwtTokenProvider;


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

