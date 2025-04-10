package com.goblin.openchatservice.global.security;

import com.goblin.openchatservice.domain.auth.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider implements TokenProvider {

    private static final String BEARER = "Bearer ";
    private final Key key;

    public JwtProvider(@Value("${jwt.secret.key}") String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decode);
    }

    public boolean validateToken(String token) {
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
            isExpiredToken(claimsFromToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다. Token: {}", token);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT, 만료된 JWT 입니다. Token: {}", token);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT, 지원되지 않는 JWT 입니다. Token: {}", token);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 입니다. Token: {}", token);
        }
        return false;
    }

    @Override
    public String createToken(Long memberId) {
        Date expirationDate = createExpirationDate(60 * 60 * 1000);
        return BEARER + Jwts.builder()
            .setSubject(String.valueOf(memberId))
            .setExpiration(expirationDate)
            .setIssuedAt(new Date())
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    @Override
    public String getSubjectFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }



    private Date createExpirationDate(long expirationTime) {
        long currentTimeMillis = System.currentTimeMillis();
        return new Date(currentTimeMillis + expirationTime);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(key)
            .build()

            .parseClaimsJws(token)
            .getBody();
    }

    private void isExpiredToken(Claims claims) {
        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())) {
            throw new ExpiredJwtException(null, claims, "만료된 토큰입니다");
        }
    }

}