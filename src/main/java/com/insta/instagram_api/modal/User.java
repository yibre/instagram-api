package com.insta.instagram_api.modal;

import com.insta.instagram_api.dto.UserDto;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;

    private String password;

    private Set<UserDto> follower = new HashSet<UserDto>();
    private Set<UserDto> following = new HashSet<UserDto>();
    private List<Story> stories = new ArrayList<>();
    private List<Post> savedPost = new ArrayList<>();
}