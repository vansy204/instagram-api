package org.instagramapi.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.Authenticator;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
            String jwt = Jwts.builder()
                    .setIssuer("instagram")
                    .setIssuedAt(new Date())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .claim("username", authentication.getName())
                    .setExpiration(new Date(new Date().getTime() + 300000000))
                    .signWith(key).compact();
            response.setHeader(SecurityContext.HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/signin");
    }
    public String populateAuthorities(Collection<? extends GrantedAuthority> collection ) {
        Set<String> authorities = new HashSet<>();
        for(GrantedAuthority grantedAuthority : collection) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return String.join(",", authorities);
    }
}