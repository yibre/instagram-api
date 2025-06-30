package com.insta.instagram_api.service;

import java.util.List;

import com.insta.instagram_api.exceptions.PostException;
import com.insta.instagram_api.exceptions.UserException;
import com.insta.instagram_api.modal.Post;

// 다섯번째(지만 다섯번째 강의가 너무 많음) 아래 링크 2:49
// https://www.youtube.com/watch?v=UXx9J3pGJNI&list=PL26ar0pSef9hgFFtQOprYTM-zAw6-GmOE&index=6
public interface PostService {

    public Post createPost(Post post, Integer userId) throws UserException;

    public String deletePost(Integer postId, Integer userId) throws UserException, PostException;

    public List<Post> findPostByUserId(Integer userId) throws UserException;

    public Post findPostById(Integer postId) throws PostException;

    public List<Post> findAllPostByUserId(List<Integer> userIds) throws PostException, UserException;

    public String savePost(Integer postId, Integer userId) throws PostException, UserException;

    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException;

    public Post likePost(Integer postId, Integer userId) throws UserException, PostException;

    public Post unlikePost(Integer postId, Integer userId) throws UserException, PostException;
}
