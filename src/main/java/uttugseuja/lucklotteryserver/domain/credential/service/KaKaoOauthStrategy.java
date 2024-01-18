package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.api.client.GoogleAuthClient;
import uttugseuja.lucklotteryserver.global.api.client.KakaoOauthClient;
import uttugseuja.lucklotteryserver.global.property.OauthProperties;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@AllArgsConstructor
@Component("KAKAO")
@Slf4j
public class KaKaoOauthStrategy implements OauthStrategy {

    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private static final String ISSUER = "https://kauth.kakao.com";

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return null;
    }
}
