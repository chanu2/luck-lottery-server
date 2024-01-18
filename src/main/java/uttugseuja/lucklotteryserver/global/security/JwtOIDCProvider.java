package uttugseuja.lucklotteryserver.global.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.global.exception.InvalidTokenException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtOIDCProvider {

    private final String KID = "kid";

    private String removeSignatureFromToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw InvalidTokenException.EXCEPTION;
        return splitToken[0] + "." + splitToken[1] + ".";
    }

}
