package com.capg.login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
public class TestController {

	  /*********************************************************************************************
	   * - Method Name 			: 	userAccess
	   * - Input Parameters 	: 	none
	   * - Return Type 			:	String
	   * - End Type Url			:	/api/test/user
	   * - Request Method Type	:	GetMapping
	   * - Author				:	Capgemini
	   * - Creation Date		:	16-08-2020
	   * - Description			:	This method helps to display the contents that can access by the user.
	   ********************************************************************************************/
	  
	 	@GetMapping("/api/test/user")

	 	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	 	public String userAccess() {
	 		return ">>> User Contents!";
	 	}
	 
	  /********************************************************************************************
	   * - Method Name 			: 	adminAccess
	   * - Input Parameters 	: 	none
	   * - Return Type 			:	String
	   * - End Type Url			:	/api/test/admin
	   * - Request Method Type	:	GetMapping
	   * - Author				:	Capgemini
	   * - Creation Date		:	16-08-2020
	   * - Description			:	This method helps to display the contents that can access by the admin.
	   * *******************************************************************************************/
	  
	 	@GetMapping("/api/test/admin")

	 	@PreAuthorize("hasRole('ADMIN')")
	 	public String adminAccess() {
	 		return ">>> Admin Contents";
	 	}
}
