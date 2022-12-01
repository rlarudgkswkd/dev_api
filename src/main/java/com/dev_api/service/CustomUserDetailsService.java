package com.dev_api.service;

import com.dev_api.entity.User;
import com.dev_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //추가

    public CustomUserDetailsService(
            @Autowired UserRepository userRepository,
            @Autowired PasswordEncoder passwordEncoder //추가
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        final User testUserEntity = new User();
        testUserEntity.setUserName("entity_user");
        testUserEntity.setPassword(passwordEncoder.encode("test1pass")); //바꿈
        this.userRepository.save(testUserEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = userRepository.findByUserName(username);
        return new User(username, userEntity.getPassword(), new ArrayList<>());
    }
}