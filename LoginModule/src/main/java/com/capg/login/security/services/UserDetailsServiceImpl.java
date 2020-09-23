package com.capg.login.security.services;

import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capg.login.dao.UserDAO;
import com.capg.login.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	  @Autowired
	  UserDAO userRepository;
	 
	  @Override
	  @Transactional
	  public UserDetails loadUserByUsername(String username) {
	 
	    User user = userRepository.findByUsername(username).orElseThrow(
	        () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
	 
	    return UserPrinciple.build(user);
	  }

}
