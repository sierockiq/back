// https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world

package com.quentin.sierocki.jwt;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.exception.ResourceNotFoundException;
import com.quentin.sierocki.exception.UnauthorizedAccessRessourceException;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.globals.LoggedInUser;
import com.quentin.sierocki.service.UserService;
import com.quentin.sierocki.service.converter.ConvertionException;
import com.quentin.sierocki.websecurityconfig.UserDetails;

import io.jsonwebtoken.ExpiredJwtException;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Resource(name = "loggedInUserRequestScopeBean")
	LoggedInUser loggedInUser;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException, UnauthorizedAccessRessourceException {
		String idUser = null;
		final String requestTokenHeader = request.getHeader("Authorization");
		Pattern pattern = Pattern.compile(Pattern.quote("user/") + "(.*?)" + Pattern.quote("/"));
		Matcher matcher = pattern.matcher(request.getRequestURI());

		while (matcher.find()) {
			idUser = matcher.group(1); // Since (.*?) is capturing group 1
			// You can insert match into a List/Collection here
		}

		int id = 0;
		String jwtToken = null;

		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
		// Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				id = Integer.parseInt(jwtTokenUtil.getIdFromToken(jwtToken));
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		if (id != 0 && idUser != null && !String.valueOf(id).equals(idUser)) {
			throw new UnauthorizedAccessRessourceException(String.valueOf(id), idUser);
		}
		// Once we get the token validate it.
		if (id != 0 && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDTO user = null;
			try {
				user = userService.findUserById(id);
			} catch (ConvertionException | FunctionnalException e) {
				e.printStackTrace();
			}

			if (user == null) {
				throw new ResourceNotFoundException("User", "id", id);
			}

			UserDetails userDetails = jwtUserDetailsService.loadUser(user);

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				loggedInUser.setUser(user);
				loggedInUser.setJwtToken(jwtToken);

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// After setting the Authentication in the context, we specify that the current
				// user is authenticated.
				// So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		chain.doFilter(request, response);
	}
}
