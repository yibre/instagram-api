package com.insta.instagram_api.config;

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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//5번째 강의 10:49
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
            String jwt = Jwts.builder().setIssuer("instagram").setIssuedAt(new Date())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .claim("username",authentication.getName())
                    .setExpiration(new Date(new Date().getTime()+300000000))
                    .signWith(key).compact();

            response.setHeader(SecurityContext.HEADER, jwt);
        }
        
        filterChain.doFilter(request, response);

    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority: collection) {
            authorities.add(authority.getAuthority());
        }
        return String.join(",", authorities);
    }

    // 이게 뭘 위한 코드지? 24:10
    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
        return !req.getServletPath().equals("/signin");
    }
}

// 5강 1:00:10 하는 중