package com.example.bookstore.service;

import com.example.bookstore.model.BookstoreUserDetails;
import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class JpaUserDetailsManager implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails userDetails) {
        var user = ((BookstoreUserDetails) userDetails).getUser();
        user.setAuthority("VISITOR");
        user.encode(passwordEncoder);
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

    public boolean emailExists(String email) {
        var optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.isPresent();
    }

    public List<String> getExistedInfos(String username, String email) {
        var existedInfos = new ArrayList<String>();
        if (userExists(username)) existedInfos.add("Username");
        if (emailExists(email)) existedInfos.add("Email");
        return existedInfos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication");
        var user = userRepository.findUserByUsername(username).orElseThrow(s);
        return new BookstoreUserDetails(user);
    }
}
