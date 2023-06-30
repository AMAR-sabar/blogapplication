package com.blogapp;

import com.blogapp.config.ConstantValue;
import com.blogapp.entities.Role;
import com.blogapp.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class BlogapplicationApplication implements  CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogapplicationApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Amar"));

		try
		{
			Role role = new Role();
			role.setId(ConstantValue.NORMAL_USER);
			role.setName("ROLE_NORMAL");

			Role role1 = new Role();
			role1.setId(ConstantValue.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			List<Role> roles =new ArrayList<>();
			roles.add(role);
			roles.add(role1);
			List<Role> result = roleRepo.saveAll(roles);
		}
		catch (Exception e)
		{

		}
		
	}
}
