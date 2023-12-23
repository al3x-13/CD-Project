package cd.project.frontend.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtHelper {
    private static Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET"));

    public static String createToken(String username) {
        Date nowTimestamp = new Date(System.currentTimeMillis());
        Date expireTimestamp = new Date(System.currentTimeMillis() + 6 * 60 * 60 * 1000);   // 6h expire time

        return JWT.create()
                .withSubject(String.valueOf(username))
                .withIssuedAt(nowTimestamp)
                .withExpiresAt(expireTimestamp)
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
}
