package uttugseuja.lucklotteryserver.domain.credential.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uttugseuja.lucklotteryserver.domain.credential.presentation.dto.response.CheckRegisteredResponse;
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


    @GetMapping("/oauth/valid/register")
    public CheckRegisteredResponse valid(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("controller token = {}",token);
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

}
