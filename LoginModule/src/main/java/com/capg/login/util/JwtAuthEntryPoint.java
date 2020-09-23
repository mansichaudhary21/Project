package com.capg.login.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint{


    private static final Logger loggerAuthEntryPoint = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
    
    /*********************************************************************************************
	   * - Method Name 			: 	commence
	   * - Input Parameters 	: 	HttpServletRequest request, HttpServletResponse response,AuthenticationException e
	   * - Return Type 			:	void
	   * - Author				:	Capgemini
	   * - Creation Date		:	12-08-2020
	   * - Description			:	This method authenticates and authorize the user to get logged in the application.
	   * *******************************************************************************************/
	  
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) 
                             throws IOException, ServletException {
      
    	loggerAuthEntryPoint.error("Unauthorized error. Message - {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Username or Password");
    }
}
