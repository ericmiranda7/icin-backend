package com.example.icinbackend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.icinbackend.services.MySQLUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final MySQLUserDetailsService userDetailsService;

    private final JWTUtil jwtUtil;

    public JWTFilter(MySQLUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(("Bearer "))) {
            String jwt = authHeader.substring(7);

            if (jwt.isEmpty() || jwt.isBlank()) {
                // todo(): api response
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
                System.out.println("Nuh uh.");
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);


                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("US")));
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        System.out.println("YES NULL");
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException ex) {
                    // todo(): api response
                    System.out.println(ex.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}