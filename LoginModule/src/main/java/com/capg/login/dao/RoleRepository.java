package com.capg.login.dao;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.login.model.Role;
import com.capg.login.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByName(RoleName roleName);

}
