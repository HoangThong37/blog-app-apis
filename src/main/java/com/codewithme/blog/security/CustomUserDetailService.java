package com.codewithme.blog.security;

import com.codewithme.blog.entities.UserEntity;
import com.codewithme.blog.exceptions.ResourceNotFoundException;
import com.codewithme.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by userName
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(" user ", " email "+ username, 0));
        return user;
    }
}
