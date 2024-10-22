package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(nullable = false, length = 255)
    private String userId;

    @Column(nullable = false, length = 255)
    private String userName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    public User() {
    }

    public User(String userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }



    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId());
    }
}
