package uttugseuja.lucklotteryserver.global.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.exception.ExpiredTokenException;
import uttugseuja.lucklotteryserver.global.exception.InvalidTokenException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtOIDCProvider {

    private final String KID = "kid";

    public String getKidFromParsedJwtHeader(String token, String iss, String aud) {

        String kid = (String) getRemovedSignatureParsedJwt(token, iss, aud).getHeader().get(KID);
        return kid;
    }

    private String removeSignatureFromToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw InvalidTokenException.EXCEPTION;
        return splitToken[0] + "." + splitToken[1] + ".";
    }

    private Jwt<Header, Claims> getRemovedSignatureParsedJwt(String token, String iss, String aud) {
        try {
            Jwt<Header, Claims> parsedJwt = Jwts.parserBuilder()
                    .requireAudience(aud)
                    .requireIssuer(iss)
                    .build()
                    .parseClaimsJwt(removeSignatureFromToken(token));
            return parsedJwt;

        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            log.error(e.toString());
            throw InvalidTokenException.EXCEPTION;
        }
    }



}
