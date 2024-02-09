package com.springboot.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.EncryptionUtil;
import com.springboot.blog.entity.Roles;
import com.springboot.blog.entity.Users;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RolesRepository;
import com.springboot.blog.repository.UsersRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// http://localhost:8585/api/auth/signin
//	@PostMapping("/signin")
//	public ResponseEntity<String> authenticationUser(@RequestBody LoginDto loginDto) {
//		System.out.println("User Name : "+loginDto.getUsernameOrEmail()+ " password : "+loginDto.getPassword());
//	 Authentication authentication	=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//				loginDto.getUsernameOrEmail(), passwordEncoder.encode(loginDto.getPassword())));
//	 
//	 System.out.println("Yes i am here");
//	 
//	 SecurityContextHolder.getContext().setAuthentication(authentication);
//	 return new ResponseEntity<>("User Sign in Successfully ",HttpStatus.OK);
//		
//	}
	
	// http://localhost:8585/api/auth/signin
	@PostMapping("/signin")
	public ResponseEntity<String> authenticationUser(@RequestBody LoginDto loginDto) {
		System.out.println("User Name : " + loginDto.getUsernameOrEmail() + " password : " + loginDto.getPassword());

		Users existsUser = usersRepository.findByEmailAndPassword(loginDto.getUsernameOrEmail(),
				EncryptionUtil.encode(loginDto.getPassword()));

				

		if (existsUser != null) {
			System.out.println("if Name : " + loginDto.getUsernameOrEmail());
			return new ResponseEntity<>("User Sign in Successfully ", HttpStatus.OK);
		} else {
			System.out.println("else  Name : " + loginDto.getUsernameOrEmail());

			return new ResponseEntity<>("Invalid email or password ", HttpStatus.BAD_REQUEST);
		}
	}
	
	// http://localhost:8585/api/auth/signup
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		
		// check for username exists in database
		if (usersRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("username is already taken", HttpStatus.BAD_REQUEST);
			
		}
		
		// add check for email exists in database 
		if (usersRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("email is already taken ", HttpStatus.BAD_REQUEST);
		}
		
		// create user object
		Users users= new Users();
		users.setName(signUpDto.getName());
		users.setUsername(signUpDto.getUsername());
		users.setEmail(signUpDto.getEmail());
		users.setPassword(EncryptionUtil.encode(signUpDto.getPassword()));
		
		Roles roles = rolesRepository.findByName("ROLE_ADMIN").get();
		users.setRoles(Collections.singleton(roles));
		usersRepository.save(users);
		
		return new ResponseEntity<>("User Register Successfully", HttpStatus.OK);
		
	}

}




//package com.springboot.blog.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.springboot.blog.CustomUserDetailsService;
//import com.springboot.blog.payload.LoginDto;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder bean
//
//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticationUser(@RequestBody LoginDto loginDto) {
//        // For testing purposes only; do not use this in production
//    	 System.out.println("Yes, plain : "+loginDto.getPassword());
//        String plainTextPassword = loginDto.getPassword();
//        
//        // Manually encode the plain text password for testing
//        String encodedPassword = passwordEncoder.encode(plainTextPassword);
//        System.out.println("Yes, encoded Password : "+encodedPassword);
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDto.getUsernameOrEmail(),
//                        encodedPassword  // Use the encoded password for testing
//                )
//        );
//        System.out.println("Yes, I am here");
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
//    }
//}




//Users email = usersRepository.findByEmailAddress(loginDto.getUsernameOrEmail());
//		System.out.println("email : "+email.getEmail());
//		
//		
//Users password = usersRepository.findByPassword(loginDto.getPassword());
//		
//	if (email!=null && password!=null) {
//		System.out.println("if Name : " + loginDto.getUsernameOrEmail());
//		return new ResponseEntity<>("User Sign in Successfully ", HttpStatus.OK);
//	}
//	else {
//		System.out.println("else  Name : " + loginDto.getUsernameOrEmail());
//
//		return new ResponseEntity<>("Invalid email or password ", HttpStatus.BAD_REQUEST);
//	}

