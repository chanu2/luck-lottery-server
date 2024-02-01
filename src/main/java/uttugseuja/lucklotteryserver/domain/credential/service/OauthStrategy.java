package uttugseuja.lucklotteryserver.domain.credential.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface OauthStrategy {
    OIDCDecodePayload getOIDCDecodePayload(String token);

    String getOauthLink();

    String getIdToken(String code);
}
