package com.capg.login.security.services;

import java.util.Collection;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.capg.login.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserPrinciple implements UserDetails{
		
		private static final long serialVersionUID = 1L;
	 
	  	private int id;
	 
	    private String name;
	    
	    private String username;
	 
	    private String email;
	 
	    @JsonIgnore
	    private String password;
	 
	    private Collection<? extends GrantedAuthority> authorities;
	 
	    public UserPrinciple(int id,String name,  
	              String username, String email, String password, 
	              Collection<? extends GrantedAuthority> authorities) {
	        this.id = id;
	        this.name = name;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	        this.authorities = authorities;
	    }
	 
	    public static UserPrinciple build(User user) {
	        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
	                new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
	 
	        return new UserPrinciple(
	                user.getId(),
	                user.getName(),
	                user.getUsername(),
	                user.getEmail(),
	                user.getPassword(),
	                authorities
	        );
	    }
	 
	    public int getId() {
	        return id;
	    }
	 
	    public String getName() {
	    	return name;
	    }
	    
	    public String getEmail() {
	        return email;
	    }
	 
	    @Override
	    public String getUsername() {
	        return username;
	    }
	 
	    @Override
	    public String getPassword() {
	        return password;
	    }
	 
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }
	 
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }
	 
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }
	 
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }
	 
	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			UserPrinciple user = (UserPrinciple) obj;
			return Objects.equals(id, user.id);
		}
	 
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((password == null) ? 0 : password.hashCode());
			result = prime * result + ((username == null) ? 0 : username.hashCode());
			return result;
		}

		
	    
	
}


