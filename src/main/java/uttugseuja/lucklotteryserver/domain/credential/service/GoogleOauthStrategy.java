package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.api.client.GoogleAuthClient;
import uttugseuja.lucklotteryserver.global.property.OauthProperties;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@AllArgsConstructor
@Component("GOOGLE")
public class GoogleOauthStrategy implements OauthStrategy{

    private final OauthProperties oauthProperties;
    private final GoogleAuthClient googleAuthClient;
    private static final String ISSUER = "https://accounts.google.com";
    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return null;
    }


}
