package com.blog.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
