package com.blogapp;

import ch.qos.logback.core.CoreConstants;
import com.blogapp.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogapplicationApplicationTests {
	@Autowired
	private UserRepo userRepo;
	@Test
	void contextLoads() {
	}
	public void repoTest()
	{
		String classname=this.userRepo.getClass().getName();
		String packName= String.valueOf(this.userRepo.getClass().getPackage());
		System.out.println(classname);
		System.out.println(packName);
	}

}
