package uttugseuja.lucklotteryserver.global.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uttugseuja.lucklotteryserver.domain.user.domain.AccountRole;
import uttugseuja.lucklotteryserver.global.exception.ExpiredTokenException;
import uttugseuja.lucklotteryserver.global.exception.InvalidTokenException;
import uttugseuja.lucklotteryserver.global.property.JwtProperties;
import uttugseuja.lucklotteryserver.global.security.auth.AuthDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";
    private final String ROLE = "role";
    private final String TYPE = "type";
    private final String ISSUER = "lucklottery";

    public String generateAccessToken(Long id, AccountRole accountRole) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * 1000);
        return createAccessToken(id, issuedAt, accessTokenExpiresIn, accountRole);
    }

    public String generateRefreshToken(Long id) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getRefreshExp() * 1000);
        return createRefreshToken(id, issuedAt, refreshTokenExpiresIn);
    }

    public String resolveToken(HttpServletRequest request) {
        String rawHeader = request.getHeader(jwtProperties.getHeader());

        if (rawHeader != null
                && rawHeader.length() > jwtProperties.getPrefix().length()
                && rawHeader.startsWith(jwtProperties.getPrefix())) {
            return rawHeader.substring(jwtProperties.getPrefix().length() + 1);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        String id = getJws(token).getBody().getSubject();
        String role = (String) getJws(token).getBody().get(ROLE);
        UserDetails userDetails = new AuthDetails(id, role);
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    public Long getRefreshTokenTTlSecond() {
        return jwtProperties.getRefreshExp();
    }

    public boolean isRefreshToken(String token) {
        return getJws(token).getBody().get(TYPE).equals(REFRESH_TOKEN);
    }

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private String createAccessToken(
            Long id, Date issuedAt, Date accessTokenExpiresIn, AccountRole accountRole) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TYPE, ACCESS_TOKEN)
                .claim(ROLE, accountRole.getValue())
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    private String createRefreshToken(Long id, Date issuedAt, Date accessTokenExpiresIn) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TYPE, REFRESH_TOKEN)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }




}
