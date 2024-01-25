package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.api.client.KakaoOauthClient;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeysResponse;
import uttugseuja.lucklotteryserver.global.property.OauthProperties;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@AllArgsConstructor
@Component("KAKAO")
@Slf4j
public class KaKaoOauthStrategy implements OauthStrategy {

    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private static final String ISSUER = "https://kauth.kakao.com";
    private static final String QUERY_STRING = "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";


    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCKeysResponse oidcKakaoKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(token,ISSUER,oauthProperties.getKakaoAppId(),oidcKakaoKeysResponse);
    }

    @Override
    public String getOauthLink() {
        return oauthProperties.getKakaoBaseUrl()
                + String.format(
                QUERY_STRING,
                oauthProperties.getKakaoClientId(),
                oauthProperties.getKakaoRedirectUrl());
    }

    @Override
    public String getIdToken(String code) {
        return null;
    }
}
