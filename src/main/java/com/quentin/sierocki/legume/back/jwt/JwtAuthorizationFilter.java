package com.quentin.sierocki.legume.back.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.quentin.sierocki.legume.back.globals.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication == null) {
			filterChain.doFilter(request, response);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(Constants.TOKEN_HEADER);
		String idUser = request.getHeader(Constants.ID_USER);
		if (token!=null && !token.isEmpty() && token.startsWith(Constants.TOKEN_PREFIX)) {
			try {
				byte[] signingKey = Constants.JWT_SECRET.getBytes();

				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey)
						.parseClaimsJws(token.replace("Bearer ", ""));

				String username = parsedToken.getBody().getSubject();
				String idUserJWT = parsedToken.getBody().getId();
				if(!idUserJWT.equals(idUser))
					throw new IllegalArgumentException("idUser request et idUserJWT différents.");
				List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get("rol")).stream()
						.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());

				if (!username.isEmpty()) {
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
				}
			} catch (ExpiredJwtException exception) {
				log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			} catch (UnsupportedJwtException exception) {
				log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			} catch (MalformedJwtException exception) {
				log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			} catch (SignatureException exception) {
				log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
			} catch (IllegalArgumentException exception) {
				log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			}
		}

		return null;
	}
}