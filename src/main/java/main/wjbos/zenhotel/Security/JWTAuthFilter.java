package main.wjbos.zenhotel.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.wjbos.zenhotel.Service.CustomUserDetailsService;
import main.wjbos.zenhotel.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;

@Component
public class JWTAuthFilter extends OncePerRequestFilter { //first line of Security

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        // Extract the JWT token from the Authorization header
        final String jwtToken;
        // Extract the email from the JWT token
        final String email;

        // If the Authorization header is null or blank, skip authentication
        if(authHeader == null || authHeader.isBlank()){
            filterChain.doFilter(request,response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwtToken = authHeader.substring(7);
        // Extract the email from the JWT token
        email = jwtUtils.extractUsername(jwtToken);

        // If the email is valid and the security context is not yet set, authenticate the user
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Load the user from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            // Check if the JWT token is valid
            if(jwtUtils.isValidToken(jwtToken,userDetails)){
                // Create a new security context
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                // Create a new authentication token
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                // Set the details of the authentication token
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication token in the security context
                securityContext.setAuthentication(token);
                // Set the security context in the security holder
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Continue with the filter chain
        filterChain.doFilter(request,response);
    }
}
