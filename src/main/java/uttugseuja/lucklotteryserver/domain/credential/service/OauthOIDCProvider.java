package uttugseuja.lucklotteryserver.domain.credential.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeyDto;
import uttugseuja.lucklotteryserver.global.api.dto.OIDCKeysResponse;
import uttugseuja.lucklotteryserver.global.security.JwtOIDCProvider;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthOIDCProvider {

    private final JwtOIDCProvider jwtOIDCProvider;

    private String getKidFromParsedJwtIdToken(String token, String iss, String aud) {
        log.info(iss, aud);
        return jwtOIDCProvider.getKidFromParsedJwtHeader(token, iss, aud);
    }

    public OIDCDecodePayload getPayloadFromIdToken(
            String token, String iss, String aud, OIDCKeysResponse oidcPublicKeysResponse) {
        String kid = getKidFromParsedJwtIdToken(token, iss, aud);

        OIDCKeyDto oidcKeyDto =
                oidcPublicKeysResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();

        return (OIDCDecodePayload)
                jwtOIDCProvider.getOIDCTokenBody(
                        token, oidcKeyDto.getN(), oidcKeyDto.getE());
    }


}