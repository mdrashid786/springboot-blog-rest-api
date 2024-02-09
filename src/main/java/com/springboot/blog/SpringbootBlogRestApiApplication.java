package com.springboot.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.blog.entity.Roles;
import com.springboot.blog.repository.RolesRepository;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Documentation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringbootBlogRestApiApplication implements CommandLineRunner{

	public static void main(String[] args) { 
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.springboot.blog")).build();
	}
	
	@Autowired
	private RolesRepository rolesRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Roles adminRole= new Roles();
		adminRole.setName("ROLE_ADMIN");
		rolesRepository.save(adminRole);
		
		Roles userRole= new Roles();
		userRole.setName("ROLE_USER");
		rolesRepository.save(userRole);
		
	}
	
 
	
	
	
	
	
	
	
	
	
	
	
	
}
  