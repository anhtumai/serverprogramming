package com.example.bookstore.controller;

import com.example.bookstore.model.BookstoreUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthGetter {
    public static Optional<String> getCurrentUsername() {

        var principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof BookstoreUserDetails) {
            return Optional.of(((BookstoreUserDetails) principal).getUsername());
        }
        return Optional.empty();
    }
}
