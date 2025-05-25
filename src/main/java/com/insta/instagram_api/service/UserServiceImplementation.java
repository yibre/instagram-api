package com.insta.instagram_api.service;

import com.insta.instagram_api.exceptions.UserException;
import com.insta.instagram_api.modal.User;
import com.insta.instagram_api.repository.UserRepository;
import com.insta.instagram_api.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

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
            throw new UserException("all fields are required.");
        }

        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

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
        return null;
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        return null;
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
        return List.of();
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        return List.of();
    }

    @Override
    public User updateUserDetails(User updateUser, User existingUser) throws UserException {
        return null;
    }
}