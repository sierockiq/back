package com.quentin.sierocki.legume.back.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.legume.back.websecurityconfig.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	public String login(String idUser,String username, String password) throws Exception {
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = ((User) auth.getPrincipal());

	        List<String> roles = user.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toList());

	        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

	        String token = Jwts.builder()
	            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
	            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
	            .setIssuer(SecurityConstants.TOKEN_ISSUER)
	            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
	            .setSubject(user.getUsername())
	            .setId(idUser)
	            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
	            .claim("rol", roles)
	            .compact();
	        return SecurityConstants.TOKEN_PREFIX + token;

		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
