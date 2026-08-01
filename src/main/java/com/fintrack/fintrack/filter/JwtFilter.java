package com.fintrack.fintrack.filter;

import com.fintrack.fintrack.service.CustomUserDetailService;
import com.fintrack.fintrack.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path=request.getServletPath();
        if(path.startsWith("/auth"))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String header=request.getHeader("Authorization");
        if(header ==null || !header.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token =header.substring(7);
        String userName=jwtService.extractSubject(token);
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails user= customUserDetailService.loadUserByUsername(userName);
            if(jwtService.isValidToken(token,user))
            {
                UsernamePasswordAuthenticationToken details=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(details);
            }


        }
        filterChain.doFilter(request,response);



    }
}
