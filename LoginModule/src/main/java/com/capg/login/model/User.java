package com.capg.login.model;

import java.util.HashSet;



import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Entity
@Table(name="users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	 
		@NotBlank
		@Size(min=10)		
		private String name;
		
		@NotBlank
		@Size(min=10)
	    private String username;
		
		@NotBlank
	    private String email;
	 
		@NotBlank
		@Size(min = 6)
	    private String password;
	 
	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "user_roles", 
	      joinColumns = @JoinColumn(name = "user_id"), 
	      inverseJoinColumns = @JoinColumn(name = "role_id"))
	    private Set<Role> roles = new HashSet<>();
	 
	    public User() {}
	 
	    public User(String name,String username, String email, String password) {
	     
	    	this.name=name;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	    }
	 
	    public int getId() {
	        return id;
	    }
	 
	    public void setId(int id) {
	        this.id = id;
	    }
	 
	    public String getName()
	    {
	    	return name;
	    }
	    public void setName(String name)
	    {
	    	this.name = name;
	    }
	    public String getUsername() {
	        return username;
	    }
	 
	    public void setUsername(String username) {
	        this.username = username;
	    }
	 
	    public String getEmail() {
	        return email;
	    }
	    
	    public String getPassword() {
	        return password;
	    }
	 
	    public void setPassword(String password) {
	        this.password = password;
	    }
	 
	    public Set<Role> getRoles() {
	        return roles;
	    }
	 
	    public void setRoles(Set<Role> roles) {
	        this.roles = roles;
	    }
}
