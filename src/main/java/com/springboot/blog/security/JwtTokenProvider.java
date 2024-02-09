//package com.springboot.blog.security;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import com.springboot.blog.exception.BlogAPIException;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//
//@Component
//public class JwtTokenProvider {
//	
//	
//	@Value("${app.jwt.secret}")
//	private String jwtSecret;
//	
//	@Value("${app.jwt.expiration-milliseconds}")
//	private int jwtExpirationInMs;
//	
//	// Genrate Token 
//	
//	public String genrateToken(Authentication authentication) {
//		String username = authentication.getName();
//		Date currentDate= new Date();
//		Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMs);
//		String token=  Jwts.builder()
//				.setSubject(username)
//				.setIssuedAt(new Date())
//				.setExpiration(expiryDate) 
//				.signWith(SignatureAlgorithm.HS512,jwtSecret)
//				.compact();
//		
//		return token;
//	}
//	
//	// get Username from the token 
//	public String getUsernameFromJWT(String token) {
//	    Claims claims = Jwts.parser()
//	            .setSigningKey(jwtSecret)
//	            .parseClaimsJws(token)
//	            .getBody();
//
//	    // Assuming that the 'sub' claim in the JWT represents the username
//	    return claims.getSubject();
//	}
//	
//	// validate JWT token 
//	public boolean validationToken(String token) {
//		try {
//			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//			return true;
//			
//		} 
//		catch (SignatureException ex) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt Signature");
//		}
//		catch (MalformedJwtException me) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt Token");
//		}
//		catch (ExpiredJwtException jt) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expiry Jwt Token");
//		}
//		catch (UnsupportedJwtException ex) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsuported Jwt Token");
//		}
//		catch (IllegalArgumentException ix) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims String is empty");
//		}
//	}
//	
//	
//
//}
