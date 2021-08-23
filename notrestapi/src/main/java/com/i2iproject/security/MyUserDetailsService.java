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
import com.i2iproject.security.factories.ProducerOfUserLoader;

@Service
public class MyUserDetailsService implements UserDetailsService{
	private PasswordEncoder encoder;
	private ProducerOfUserLoader producerOfUserLoader;
	
	public MyUserDetailsService(PasswordEncoder encoder,
			ProducerOfUserLoader producerOfUserLoader) {
		this.encoder = encoder;
		this.producerOfUserLoader = producerOfUserLoader;
	}

	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		UserLoader userLoader = producerOfUserLoader.produceUserLoader();
		UserModel loadedUser = userLoader.loadUserByPhoneNumber(phoneNumber);
		return new User(loadedUser.getMsidn(), encoder.encode(loadedUser.getPassWord()), new ArrayList<>());
	}

}
