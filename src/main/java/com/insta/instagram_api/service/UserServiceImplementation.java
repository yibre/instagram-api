package com.insta.instagram_api.service;

import com.insta.instagram_api.config.AppConfig;
import com.insta.instagram_api.exceptions.UserException;
import com.insta.instagram_api.modal.User;
import com.insta.instagram_api.repository.UserRepository;
import com.insta.instagram_api.dto.UserDto;
import com.insta.instagram_api.security.JwtTokenClaims;
import com.insta.instagram_api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

// 비즈니스 로직
@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    // 4번째 강의 1:25:40에서 구현 시작
    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isEmailExist = userRepository.findByEmail((user.getEmail()));
        if (isEmailExist.isPresent()) {
            throw new UserException("Email Is Already Exist");
        }

        Optional<User> isUsernameExist = userRepository.findByUsername((user.getUsername()));
        if (isUsernameExist.isPresent()) {
            throw new UserException("Username Is Already Taken...");
        }

        if (user.getEmail()==null || user.getPassword()==null || user.getUsername() == null || user.getName() == null) {
            throw new UserException("All fields are required.");
        }

        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        // 5th 54:22
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("user not exist with id: "+userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        // https://www.youtube.com/watch?v=UXx9J3pGJNI&list=PL26ar0pSef9hgFFtQOprYTM-zAw6-GmOE&index=8 강의 36:50
        // Bearer afafdadfasdfasdf
        token = token.substring(7);

        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);

        String email = jwtTokenClaims.getUsername();

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("invalid token...");
    }

    // 아래 함수는 4번째 강의 2:05:00 쯤에서부터 만들기 시작
    // TODO: 그런데 아래 findByUsername을 함수 안에서 한 번 더 쓴 건 어떻게 쓴거지?
    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt = userRepository.findByUsername(username);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("User does not exist with username: "+username);
    }

    // 1:41:47 부터 구현 시작
    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getUsername());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(follower.getEmail());
        following.setId(follower.getId());
        following.setUserImage(follower.getUserImage());
        following.setName(follower.getName());
        following.setUsername(following.getUsername());

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You are following "+ followUser.getId();
    }

    // 4번째 강의 1:44:48
    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getUsername());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(follower.getEmail());
        following.setId(follower.getId());
        following.setUserImage(follower.getUserImage());
        following.setName(follower.getName());
        following.setUsername(following.getUsername());

        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You have unfollowed "+followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);
        if (users.size()==0) {
            throw new UserException("user not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if (updatedUser.getEmail() != null ) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getWebsite() != null) {
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }
        if (updatedUser.getId().equals(existingUser.getId())) {
            return userRepository.save(existingUser);
        }
        throw new UserException("You can't update this user");
    }
}