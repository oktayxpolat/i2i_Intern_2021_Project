package com.i2iproject.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.controllers.factories.ProducerOfLoginResponder;
import com.i2iproject.controllers.models.Login;
import com.i2iproject.exceptions.UnAuthorizedException;
import com.i2iproject.responders.LoginResponder;
import com.i2iproject.responders.models.LoginResponderModel;
import com.i2iproject.responders.models.LoginResponse;
import com.i2iproject.responders.utils.SHA256Hasher;
import com.i2iproject.security.MyUserDetailsService;
import com.i2iproject.security.utils.JwtUtil;

@RestController
public class LoginController {
	private AuthenticationManager authenticationManager;
	private MyUserDetailsService userDetailsService;
	private JwtUtil jwtTokenUtil;
	private SHA256Hasher passwordHasher;
	
	private ProducerOfLoginResponder producerOfLoginResponder;
	
	public LoginController(
			AuthenticationManager authenticationManager,
			MyUserDetailsService userDetailsService,
			JwtUtil jwtTokenUtil,
			SHA256Hasher passwordHasher,
			ProducerOfLoginResponder producerOfLoginResponder) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.passwordHasher = passwordHasher;
		this.producerOfLoginResponder = producerOfLoginResponder;
	}

	@PostMapping(path = "/api/login", consumes = "application/json")
	public LoginResponse postLogin(@RequestBody Login requestBody) {
		
		LoginResponse response = new LoginResponse();
	
		if(isRequiredParametersSetInTheRequestBody(requestBody)) {
			try {
				String hashedPassword = passwordHasher.getSHA256(requestBody.getPassword());
				String jwt = authenticate(requestBody.getMsisdn(),hashedPassword);
				
				LoginResponder loginResponder = producerOfLoginResponder.produceLoginResponder();
				LoginResponderModel responderInputModel = new LoginResponderModel(requestBody.getMsisdn(), hashedPassword, jwt);
				response = loginResponder.respond(responderInputModel);
			}catch(UnAuthorizedException ea) {
				response.setLoginSuccess(false);
			}
		}
		else {
			response.setLoginSuccess(false);
		}
		return response;		
	}
	
	private boolean isRequiredParametersSetInTheRequestBody(Login requestBody) {
		return requestBody.getMsisdn() != null && requestBody.getPassword() !=null;
	}
	
	private String authenticate(String msisdn, String password) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(msisdn, password));
		}catch (BadCredentialsException e) {
			throw new UnAuthorizedException("BAD CREDENTIALS");
		}catch (AuthenticationException e) {
			throw new UnAuthorizedException("BAD CREDENTIALS");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(msisdn);
		return jwtTokenUtil.generateToken(userDetails);
	}
	
}
