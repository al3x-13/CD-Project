package cd.project.frontend.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthMiddleware extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO: remove this
        System.out.println(request.getMethod() + " at " + request.getRequestURI());
        System.out.println("Auth header: " + request.getHeader("Authorization"));

        // TODO: auth validation
        if (AuthenticationHelpers.endpointIsProtected(request.getServletPath())) {
            System.out.println("PROTECTED ENDPOINT");
        }

        filterChain.doFilter(request, response);
    }
}
