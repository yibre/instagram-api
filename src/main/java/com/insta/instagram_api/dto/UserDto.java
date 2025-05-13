package com.insta.instagram_api.dto;

public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String name;
    private String userImage;

    public UserDto(Integer id, String username, String email) {
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
    public int hashCode() {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
