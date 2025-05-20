package com.insta.instagram_api.modal;

import com.insta.instagram_api.dto.UserDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

// 4번째 강의 27:32
@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;

    private String image;
    private String location;
    private LocalDateTime createdAt;

    private UserDto user;
    

}
