package com.crm.app.user.profile.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.crm.app.user.profile.service.UserService;
import com.crm.app.user.profile.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter
public class AuthTokenFilter extends OncePerRequestFilter  {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;
	
	public static final String TOKEN_PREFIX = "Bearer ";

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        	String jwtToken = null;
        	String username = null;
        	 final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        	 
        	if (header != null && header.startsWith(TOKEN_PREFIX)) {
        		jwtToken = header.substring(7);
        			username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        				//sonar fix - using primitive boolean expression
        				if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {
        					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        							userDetails, null, userDetails.getAuthorities());
        							usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        							SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        				}
        				
        			}
        	}
            
        } catch (Exception e) {
        	log.error("Can not set user authentication -> ", e);
        }

        filterChain.doFilter(request, response);
    }

}
