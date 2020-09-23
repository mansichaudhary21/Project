package com.capg.login.controller;

import java.util.HashSet;




import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.login.dao.RoleRepository;
import com.capg.login.dao.UserDAO;
import com.capg.login.exception.RoleNotFoundException;
import com.capg.login.exception.UserNotFoundException;
import com.capg.login.model.request.LoginForm;
import com.capg.login.model.request.SignUpForm;
import com.capg.login.model.response.JwtResponse;
import com.capg.login.model.response.ResponseMessage;
import com.capg.login.model.Role;
import com.capg.login.model.RoleName;
import com.capg.login.model.User;
import com.capg.login.util.JwtProvider;

/**
 * 
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	  @Autowired
	  AuthenticationManager authenticationManager;
	 
	  @Autowired
	  UserDAO userRepository;
	 
	  
	  @Autowired
	  RoleRepository roleRepository;
	 
	  @Autowired
	  PasswordEncoder encoder;
	 
	  @Autowired
	  JwtProvider jwtProvider;
	 
	  
	  /*********************************************************************************************
	   * - Method Name 			: 	authenticateUser
	   * - Input Parameters 	: 	username,password
	   * - Return Type 			:	JwtResponse
	   * - End Type Url			:	/signin
	   * - Request Method Type	:	PostMapping
	   * - Author				:	Capgemini
	   * - Creation Date		:	15-08-2020
	   * - Description			:	This method authenticates and authorize the user to get logged in the application.
	   * *******************************************************************************************/
	  
	  @PostMapping("/signin")
	  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) throws Exception {
	 
		Authentication authentication = authenticationManager.authenticate(
			  			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	 
	    String jwt = jwtProvider.generateJwtToken(authentication);
	 
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    
	   	return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	   
	   }
	 
	  /*********************************************************************************************
	   * - Method Name 			: 	registerUser
	   * - Input Parameters 	: 	name,username,email,role,password
	   * - Return Type 			:	ResponseMessage
	   * - End Type Url			:	/signin
	   * - Request Method Type	:	PostMapping
	   * - Author				:	Capgemini
	   * - Creation Date		:	15-08-2020
	   * - Description			:	This method registers the user to access the application.
	   * *******************************************************************************************/
	  
	  @PostMapping("/signup")
	  public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
	    
		  if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	      return new ResponseEntity<>(new ResponseMessage("This username is already taken!"),
	          HttpStatus.BAD_REQUEST);
	    }
	 
	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return new ResponseEntity<>(new ResponseMessage("This email is already in use!"),
	          HttpStatus.BAD_REQUEST);
	    }
	    
	    
	    User user = new User( signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
	        encoder.encode(signUpRequest.getPassword()));
	 
	    Set<String> strRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();
	 
	    strRoles.forEach(role -> {
	    	 switch (role) 
	    	 {
	         	case "admin":
	         			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).
	         			orElseThrow(() -> new RoleNotFoundException("The role of the user not found."));
	         			roles.add(adminRole);
	         			break;
	            default:
	            		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	            		.orElseThrow(() -> new RoleNotFoundException("The role of the user not found."));
	            		roles.add(userRole);
	         }
	       });
	    
	      user.setRoles(roles);
	      userRepository.save(user);
	 
	      return new ResponseEntity<>(new ResponseMessage("User Registered Successfully!"), HttpStatus.OK);
	  }
	  	
	  /*@PostMapping("/forget-password")
	   public ResponseEntity<> updatePassword(@RequestBody SignUpForm request)
	   {
	    	User existingUser = userRepository.findByEmailIgnoreCase()
	   }*/
	 
}
