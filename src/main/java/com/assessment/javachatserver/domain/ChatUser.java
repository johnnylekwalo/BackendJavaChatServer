package com.assessment.javachatserver.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatUser chatUser = (ChatUser) o;
        return Objects.equals(id, chatUser.id) && Objects.equals(username, chatUser.username) && Objects.equals(password, chatUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
