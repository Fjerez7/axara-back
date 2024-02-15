package com.axara.backend.config;

import com.axara.backend.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Gets the value of the "Authorization" header of the HTTP request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // Checks if the "Authorization" header exists and if it starts with "Bearer". If not, simply pass the request to the next filter
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        // Extracts the JWT token from the "Authorization" header by removing the prefix.
        jwt = authHeader.substring(7);
        // Gets the email associated with the JWT token.
        userEmail = jwtService.getUserName(jwt);
        // Checks if the email is not null and if there is no current authentication in the security context.
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Load the user details using the userDetailsService service.
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            // Validates the token using the jwtService service and the obtained user details.
            if(jwtService.validateToken(jwt,userDetails)){
                // Create a new authentication token with the users details and authorities.
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Configures authentication details with additional information from the HTTP request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Sets the authentication token to the security context, which authenticates the user for the current request.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
