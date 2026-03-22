package com.matias.bingo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.matias.bingo.services.impl.CustomUserDetailService;
import com.matias.bingo.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
                                    throws ServletException, IOException {

        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String username = jwtUtils.extractUserName(token);

                if (username != null && 
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService
                                                .loadUserByUsername(username);

                    if (jwtUtils.isValidToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                userDetails, 
                                null, 
                                userDetails.getAuthorities()
                            );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                        SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);
                    }
                }

            } catch (io.jsonwebtoken.security.SignatureException ex) {
                System.err.println("Invalid JWT signature: " + ex.getMessage());
            } catch (io.jsonwebtoken.ExpiredJwtException ex) {
                System.err.println("JWT token is expired: " + ex.getMessage());
            } catch (io.jsonwebtoken.MalformedJwtException ex) {
                System.err.println("Invalid JWT token format: " + ex.getMessage());
            } catch (Exception ex) {
                System.err.println("Authentication error: " + ex.getMessage());
            }
        }

        chain.doFilter(req, res);
    }
}