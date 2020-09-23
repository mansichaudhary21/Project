package com.capg.login.util;

import org.slf4j.LoggerFactory;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capg.login.security.services.UserDetailsServiceImpl;

public class JwtAuthTokenFilter extends OncePerRequestFilter{
	
	  @Autowired
	  private JwtProvider tokenProvider;
	 
	  @Autowired
	  private UserDetailsServiceImpl userDetailsService;
	 
	  private static final Logger loggerJwtAuthToken = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
	 
	  /*********************************************************************************************
	   * - Method Name 			: 	doFilterInternal
	   * - Input Parameters 	: 	HttpServletRequest request, HttpServletResponse response,FilterChain filterChain
	   * - Return Type 			:	void
	   * - Author				:	Capgemini
	   * - Creation Date		:	12-08-2020
	   * - Description			:	This method examines the incoming request for Jwt in the header then  
	   * 							perform validation to get valid header token and save these details into security context.
	   * *******************************************************************************************/
	
	  @Override
	  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	      throws ServletException, IOException {
	    try {
	 	
	     final String authHeader = request.getHeader("Authorization");
	     
	     String jwt = null;
	     
	     String username = null;
	     
	     if(authHeader!=null && authHeader.startsWith("Bearer ")) {
	    	 jwt = authHeader.substring(7);
	    	 username = tokenProvider.getUserNameFromJwtToken(jwt);
	     }
	     
	    	
	     if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	            userDetails, null, userDetails.getAuthorities());
	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	 
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	      }
	    } catch (Exception e) {
	   
	    	loggerJwtAuthToken.error("Can not set user authentication -> Message: {}", e);
	     }
	     
	 
	    filterChain.doFilter(request, response);
	  }
	 

}
