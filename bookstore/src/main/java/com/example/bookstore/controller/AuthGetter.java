package com.example.bookstore.controller;

import com.example.bookstore.model.BookstoreUserDetails;
import com.example.bookstore.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthGetter {
    public static Optional<User> getCurrentUser() {

        var principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof BookstoreUserDetails) {
            return Optional.of(((BookstoreUserDetails) principal).getUser());
        }
        return Optional.empty();
    }

    public static Optional<String> getCurrentUsername() {
        Optional<User> optionalUser = getCurrentUser();
        return optionalUser.map(User::getUsername);
    }
}
