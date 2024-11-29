package project.blobus.Backend.member.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Log4j2
public class JWTUtil {
    private static String key = "1234567890123456789012345678901234567890";

    public static String generateToken(Map<String, Object> valueMap, int min) {
        log.info("Generate Token");

        SecretKey key;
        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        String jwtStr = Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
        return jwtStr;
    }

    public static Map<String, Object> validateToken(String token) {
        log.info("Validate Token");

        Map<String, Object> claim;
        try {
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException malformedJwtException) {
            log.info("MalFormed");
            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("Expired");
            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException invalidClaimException) {
            log.info("Invalid");
            throw new CustomJWTException("Invalid");
        } catch (JwtException jwtException) {
            log.info("JWTError");
            throw new CustomJWTException("JWTError");
        } catch (Exception e) {
            log.info("Error");
            throw new CustomJWTException("Error");
        }
        return claim;
    }
}
