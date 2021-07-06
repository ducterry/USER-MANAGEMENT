package com.ndangducbn.ducterrybase.filter;

import com.ndangducbn.ducterrybase.security.JwtUserDetailsService;
import com.ndangducbn.ducterrybase.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final static String HEADER = "Authorization";
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    public AuthorizationFilter(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        /*
            - Lấy token từ header
         */
        String token = httpServletRequest.getHeader(HEADER);

        /*
            - Parse thông tin từ token
         */
        Claims claims = jwtTokenUtil.getClaimsFromToken(token);
        if (claims == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        /*
            - Kiểm tra hạn token
         */
        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        /*
            - Tạo object Authentication
         */
        UsernamePasswordAuthenticationToken authenticationObject = getAuthentication(claims);
        if (authenticationObject == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        /*
            - Xác thực thành công
            - Lưu object Authentication vào SecurityContextHolder
         */
        SecurityContextHolder.getContext().setAuthentication(authenticationObject);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Claims claims) {
        String username = claims.getSubject();
        if (username != null) {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
}
