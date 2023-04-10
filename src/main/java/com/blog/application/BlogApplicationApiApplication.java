package com.blog.application;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.application.entities.Role;
import com.blog.application.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplicationApiApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("mahi"));
		
		try{
			Role role1 = new Role();
			
			role1.setId(501);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			
			role2.setId(502);
			role2.setName("ROLE_USER");
			
			List<Role> roles = List.of(role1, role2);
			
			List<Role> savedRoles = this.roleRepo.saveAll(roles);
			
			savedRoles.forEach(r -> {System.out.println(r.getName());});
		}catch(Exception e){
			e.printStackTrace();
			e.getMessage();
		}
	}

}
