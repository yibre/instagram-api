package com.insta.instagram_api.dto;

import java.util.Objects;

public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String name;
    private String userImage;

    public UserDto(Integer id, String username, String email, String name, String userImage) {
        super();
        this.id = id;
        this.username = username;
        this.userImage = userImage;
        this.email = email;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(username, userDto.username)
                && Objects.equals(email, userDto.email) && Objects.equals(name, userDto.name)
                && Objects.equals(userImage, userDto.userImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, name, userImage);
    }
}
