package uttugseuja.lucklotteryserver.domain.credential.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.request.OauthCodeRequest;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.request.RegisterRequest;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.request.TokenRefreshRequest;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.*;
import uttugseuja.lucklotteryserver.domain.credential.service.CredentialService;
import uttugseuja.lucklotteryserver.domain.credential.service.OauthProvider;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
@Slf4j
public class CredentialController {

    private final CredentialService credentialService;

    @PostMapping("/login2/{userId}")
    public AccessTokenDto loginTest(@PathVariable("userId") Long userId){
        AccessTokenDto result = credentialService.login(userId);
        return result;
    }

    @PostMapping("/singup2")
    public void signUptTest(@RequestBody RegisterRequest registerRequest){
        credentialService.singUpTest(registerRequest);
    }

    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }

    @GetMapping("/oauth/kakao")
    public AfterOauthResponse kakaoAuth(OauthCodeRequest oauthCodeRequest) {
        log.info("code = {}",oauthCodeRequest.getCode());
        return credentialService.getIdTokenToCode(OauthProvider.KAKAO, oauthCodeRequest.getCode());
    }

    @GetMapping("/oauth/valid/register")
    public CheckRegisteredResponse valid(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("controller token = {}",token);
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

    @PostMapping("/login")
    public AuthTokensResponse loginUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.loginUserByOCIDToken(token, oauthProvider);
    }

    @PostMapping("/register")
    public AuthTokensResponse registerUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider,
            @Valid @RequestBody RegisterRequest registerRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.registerUserByOCIDToken(token, registerRequest, oauthProvider);
    }

    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return credentialService.tokenRefresh(tokenRefreshRequest.getRefreshToken());
    }

}
