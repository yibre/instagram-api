package com.insta.instagram_api.controller;

import com.insta.instagram_api.exceptions.UserException;
import com.insta.instagram_api.modal.User;
import com.insta.instagram_api.repository.UserRepository;
import com.insta.instagram_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// 4번째 강의 2:23:27 시작
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {

        User createduser = userService.registerUser(user);

        return new ResponseEntity<User>(createduser, HttpStatus.OK);
    }

    // 5번째 강의 47:50
    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException {

        Optional<User> opt = userRepo.findByEmail(auth.getName());

        if (opt.isPresent()) {
            return new ResponseEntity<User>(opt.get(), HttpStatus.ACCEPTED);
        }

        throw new BadCredentialsException("invalid username or password");
    }

}
