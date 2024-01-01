package cd.project.frontend.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthMiddleware extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (AuthenticationHelpers.endpointIsProtected(uri)) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                response.sendError(401, "Missing 'Authorization' header");
                return;
            }

            if (!authHeader.startsWith("Bearer ")) {
                response.sendError(401, "Invalid 'Authorization' header. Must use Bearer scheme");
                return;
            }

            String token = authHeader.split(" ")[1];
            DecodedJWT jwt = JwtHelper.verifyToken(token);
            if (jwt == null) {
                response.sendError(401, "JWT is not valid");
                return;
            }
            System.out.println("(PROTECTED) " + request.getMethod() + " at " + request.getRequestURI() + " by '" +
            jwt.getSubject() + "' (id: " + JwtHelper.getUserId(token) + ")");
        } else {
            System.out.println(request.getMethod() + " at " + request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
