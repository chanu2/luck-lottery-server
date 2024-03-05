package uttugseuja.lucklotteryserver.domain.credential.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "인증 관련 컨트롤러", description = "oauth, token refresh 기능을 담당")
public class CredentialController {

    private final CredentialService credentialService;

    @Operation(summary = "테스트용")
    @PostMapping("/login2/{userId}")
    public AccessTokenDto loginTest(@PathVariable("userId") Long userId){
        AccessTokenDto result = credentialService.login(userId);
        return result;
    }

    @Operation(summary = "테스트용")
    @PostMapping("/singup2")
    public void signUptTest(@RequestBody RegisterRequest registerRequest){
        credentialService.singUpTest(registerRequest);
    }

    @Operation(summary = "카카오 링크 받기 테스트용")
    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }

    @Operation(summary = "카카오에서 Id token 테스트용")
    @GetMapping("/oauth/kakao")
    public AfterOauthResponse kakaoAuth(OauthCodeRequest oauthCodeRequest) {
        log.info("code = {}",oauthCodeRequest.getCode());
        return credentialService.getTokenToCode(OauthProvider.KAKAO, oauthCodeRequest.getCode());
    }

    @Operation(summary = "구글 로그인 링크 받기 테스트용")
    @GetMapping("/oauth/link/google")
    public OauthLoginLinkResponse getGoogleOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.GOOGLE));
    }

    @Operation(summary = "구글 Id token 벋기 테스트용")
    @GetMapping("/oauth/google")
    public AfterOauthResponse googleAuth(OauthCodeRequest oauthCodeRequest) {
        log.info("code = {}",oauthCodeRequest.getCode());
        return credentialService.getTokenToCode(OauthProvider.GOOGLE, oauthCodeRequest.getCode());
    }

    @Operation(summary = "Id Token 검증")
    @GetMapping("/oauth/valid/register")
    @Parameters({
            @Parameter(name = "idToken", description = "idToken", required = true),
            @Parameter(name = "provider", description = "idToken 제공자", required = true)
    })
    public CheckRegisteredResponse valid(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) {
        log.info("controller token = {}",token);
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

    @Operation(summary = "로그인")
    @Parameters({
            @Parameter(name = "idToken", description = "idToken", required = true),
            @Parameter(name = "provider", description = "idToken 제공자", required = true)
    })
    @PostMapping("/login")
    public AuthTokensResponse loginUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) {
        return credentialService.loginUserByOCIDToken(token, oauthProvider);
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/register")
    @Parameters({
            @Parameter(name = "idToken", description = "idToken", required = true),
            @Parameter(name = "provider", description = "idToken 제공자", required = true)
    })
    public AuthTokensResponse registerUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider,
            @Valid @RequestBody RegisterRequest registerRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return credentialService.registerUserByOCIDToken(token, registerRequest, oauthProvider);
    }

    @Operation(summary = "토큰 리프레쉬")
    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return credentialService.tokenRefresh(tokenRefreshRequest.getRefreshToken());
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout() {
        credentialService.logoutUser();
    }

    @Operation(summary = "회원 탈퇴기능 입니다.  카카오,구글 oauth 연결도 unlink 합니다. ")
    @DeleteMapping("/delete/me")
    public void deleteUser(@RequestParam(value = "oauth_access_token", required = false) String token) {
        credentialService.deleteUser(token);
    }

}
