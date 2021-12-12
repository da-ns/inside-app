package lu.dans.insideapp.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {
    private static final Logger LOG = LoggerFactory.getLogger(JwtService.class);

    @Value("$(jwt.secret)")
    String jwtSecret;

    private static final String BEARER = "Bearer ";
    private static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static int EXPIRATION_DAYS_COUNT = 7;

    public String generateToken(String login) {
        Date expirationDate = Date.from(LocalDate.now().plusDays(EXPIRATION_DAYS_COUNT)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        // generate token with user login as payload, expiration date, signature HS256
        // and key from application.properties
        return Jwts.builder()
                .claim("login", login)
                .setExpiration(expirationDate)
                .signWith(SIGNATURE_ALGORITHM, jwtSecret)
                .compact();
    }

    public Claims decodeJWT(String authorization) {
        String token = authorization != null && authorization.startsWith(BEARER)
                ? authorization.substring(BEARER.length())
                : null;

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)//DatatypeConverter.parseBase64Binary(jwtSecret))
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean isValidToken(String authorization, String login) {
        String token = authorization != null && authorization.startsWith(BEARER)
                ? authorization.substring(BEARER.length())
                : null;

        try {
            Jwts.parser()
                .require("login", login)
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLoginFromToken(String authorization) {
        Claims claims = decodeJWT(authorization);
        return (String) claims.get("login");
    }
}
