package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.api.client.GoogleAuthClient;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeysResponse;
import uttugseuja.lucklotteryserver.global.property.OauthProperties;


@AllArgsConstructor
@Component("GOOGLE")
public class GoogleOauthStrategy implements OauthStrategy{

    private final OauthProperties oauthProperties;
    private final GoogleAuthClient googleAuthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private static final String ISSUER = "https://accounts.google.com";

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCKeysResponse oidcKakaoKeysResponse = googleAuthClient.getGoogleOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(token,ISSUER,oauthProperties.getKakaoAppId(),oidcKakaoKeysResponse);
    }


}
