package cd.project.frontend.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtHelper {
    private static Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET"));

    public static String createToken(String username, int userId) {
        Date nowTimestamp = new Date(System.currentTimeMillis());
        Date expireTimestamp = new Date(System.currentTimeMillis() + 6 * 60 * 60 * 1000);   // 6h expire time

        return JWT.create()
                .withSubject(String.valueOf(username))
                .withIssuedAt(nowTimestamp)
                .withExpiresAt(expireTimestamp)
                .withClaim("id", userId)
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public static int getUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("id").asInt();
    }
}
