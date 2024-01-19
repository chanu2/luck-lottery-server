package uttugseuja.lucklotteryserver.global.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.exception.ExpiredTokenException;
import uttugseuja.lucklotteryserver.global.exception.InvalidTokenException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

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


    private PublicKey getRSAPublicKey(String modulus, String exponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        BigInteger n = new BigInteger(1, Base64.getDecoder().decode(modulus));
        BigInteger e = new BigInteger(1, Base64.getDecoder().decode(exponent));
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n,e);
        return keyFactory.generatePublic(keySpec);
    }




}
