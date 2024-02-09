package com.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.blog.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/**").permitAll()
		.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/v3/api-docs/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()  
		.httpBasic();
	}
	
	
	// Memory based Authentication
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails rashid= User.builder().username("rashid").
//				password(passwordEncoder().encode("rashid")).roles("USER").build();
//		
//		UserDetails admin= User.builder().username("admin").
//				password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(rashid, admin);
//	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//	}
//	   @Override
//	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	    } 
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean(); 
	}

}
