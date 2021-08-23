package com.i2iproject.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2iproject.database.UserLoader;
import com.i2iproject.database.models.UserModel;

@Service
public class MyUserDetailsService implements UserDetailsService{
	private PasswordEncoder encoder;
	private UserLoader userLoader;
	
	public MyUserDetailsService(PasswordEncoder encoder,
			UserLoader userLoader) {
		this.encoder = encoder;
		this.userLoader = userLoader;
	}

	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		UserModel loadedUser = userLoader.loadUserByPhoneNumber(phoneNumber);
		return new User(loadedUser.getMsidn(), encoder.encode(loadedUser.getPassWord()), new ArrayList<>());
	}

}
