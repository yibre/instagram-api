package com.insta.instagram_api.service;

import com.insta.instagram_api.dto.UserDto;
import com.insta.instagram_api.exceptions.PostException;
import com.insta.instagram_api.exceptions.UserException;
import com.insta.instagram_api.modal.Post;
import com.insta.instagram_api.modal.User;
import com.insta.instagram_api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    private UserService userService;

    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        // https://www.youtube.com/watch?v=UXx9J3pGJNI&list=PL26ar0pSef9hgFFtQOprYTM-zAw6-GmOE&index=8 강의 40:45
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.setUser(userDto);

        Post createdPost = postRepository.save(post);
        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        return "";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        return List.of();
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        return null;
    }

    @Override
    public List<Post> findAllPostByUserId(List<Integer> userIds) throws PostException, UserException {
        return List.of();
    }

    @Override
    public String savePost(Integer postId, Integer userId) throws PostException, UserException {
        return "";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
        return "";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {
        return null;
    }

    @Override
    public Post unlikePost(Integer postId, Integer userId) throws UserException, PostException {
        return null;
    }
}
