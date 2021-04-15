package com.example.bookstore.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Transient
    private String rawPassword;

    @Column(length = 60)
    private String password; // hashed password

    private String email;

    private String authority;

    public User(String username, String rawPassword, String email, String authority) {
        this.username = username;
        this.rawPassword = rawPassword;
        this.email = email;
        this.authority = authority;
    }

    public User() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    @Override
    public String toString(){
        return String.format("User %s with authority %s", username, authority);
    }

    public void encode(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(rawPassword);
    }
}
