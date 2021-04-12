package com.example.bookstore.service;

import com.example.bookstore.model.BookstoreUserDetails;
import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsManager implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserDetails userDetails) {
        var user = ((BookstoreUserDetails) userDetails).getUser();
        user.setAuthority("VISITOR");
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        var user = ((BookstoreUserDetails) userDetails).getUser();
    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String username) {
        var optionalUser = userRepository.findUserByUsername(username);
        return optionalUser.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication");
        var user = userRepository.findUserByUsername(username).orElseThrow(s);
        return new BookstoreUserDetails(user);
    }
}
