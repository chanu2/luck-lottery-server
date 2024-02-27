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
    private static final String QUERY_STRING =
            "/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";
    private static final String scope = "https://www.googleapis.com/auth/userinfo.email";

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCKeysResponse oidcGoogleKeysResponse = googleAuthClient.getGoogleOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(token,ISSUER,oauthProperties.getGoogleAppId(),oidcGoogleKeysResponse);
    }

    @Override
    public String getOauthLink() {

        return oauthProperties.getGoogleBaseUrl()
                + String.format(
                QUERY_STRING,
                oauthProperties.getGoogleClientId(),
                oauthProperties.getGoogleRedirectUrl(),
                scope);
    }

    @Override
    public String getIdToken(String code) {
        return null;
    }


}
