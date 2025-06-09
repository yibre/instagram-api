package com.insta.instagram_api.service;

import com.insta.instagram_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.insta.instagram_api.modal.User> opt = userRepository.findByEmail(username);

        if(opt.isPresent()) {
            com.insta.instagram_api.modal.User user = opt.get();

            List<GrantedAuthority> authorities = new ArrayList<>();

            // 위에서 import한 모델 클래스의 유저와 아래 유저는 다름 45:10
            return new User(user.getEmail(), user.getPassword(), authorities);
        }

        throw new BadCredentialsException("user not found with username "+username);
    }
}
