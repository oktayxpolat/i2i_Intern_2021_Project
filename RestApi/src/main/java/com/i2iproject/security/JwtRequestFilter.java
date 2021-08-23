package com.i2iproject.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.i2iproject.security.utils.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	class UserNameAndJwtModel{
		private String username;
		private String jwt;
		
		public UserNameAndJwtModel(String username, String jwt) {
			this.username = username;
			this.jwt = jwt;
		}

		public String getUsername() {
			return username;
		}

		public String getJwt() {
			return jwt;
		}
	}
	
	private MyUserDetailsService userDetailsService;
	private JwtUtil jwtUtil;
	
	public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		
		final UserNameAndJwtModel userNameAndJwt = extractUserNameAndJwtFromAuthorizationHeader(authorizationHeader);
		
		authenticate(userNameAndJwt,request);
	
		filterChain.doFilter(request,response);
	}
	
	private UserNameAndJwtModel extractUserNameAndJwtFromAuthorizationHeader(String authorizationHeader) {
		UserNameAndJwtModel userNameAndJwt=null;
		if(isAuthorizationHeaderGivenCorrectly(authorizationHeader)) {
			final int startIndexOfTheJwt = 7;
			final String jwt = authorizationHeader.substring(startIndexOfTheJwt);
			final String username = jwtUtil.extractUsername(jwt);
			userNameAndJwt = new UserNameAndJwtModel(username,jwt);
		}
		return userNameAndJwt;
	}
	
	private void authenticate(UserNameAndJwtModel userNameAndJwt, HttpServletRequest request) {
		if(isUserNameExtracted(userNameAndJwt) && !isAuthenticationAlreadyDone()) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userNameAndJwt.getUsername());
			if (jwtUtil.validateToken(userNameAndJwt.getJwt(), userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
						);
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
	}
	
	private boolean isAuthorizationHeaderGivenCorrectly(String authorizationHeader) {
		final String beginningStringToMatch = "Bearer ";
		return authorizationHeader != null && authorizationHeader.startsWith(beginningStringToMatch);
	}
	
	private boolean isUserNameExtracted(UserNameAndJwtModel userNameAndJwt) {
		return userNameAndJwt != null && userNameAndJwt.getUsername() != null;
	}
	
	private boolean isAuthenticationAlreadyDone() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}
}
