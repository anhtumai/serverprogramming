package com.example.bookstore.controller;

import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String getProfile(Model model) {
        var user = AuthGetter.getCurrentUser();
        model.addAttribute("username", Optional.of(user.get().getUsername()));
        model.addAttribute("email", user.get().getEmail());
        return "profile";
    }

    @PostMapping("/profile/email")
    public String updateEmail(@RequestParam(name = "email") String email, Model model) {
        var user = AuthGetter.getCurrentUser().get();

        //logic to verify email
        var userWithSameEmail = userRepository.findUserByEmail(email);
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (userWithSameEmail.isPresent() || !Pattern.compile(regex).matcher(email).matches())
            return "redirect:/profile?uemail=false";

        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/profile?uemail=true";

    }

    @PostMapping("/profile/password")
    public String updatePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "matchPassword") String matchPassword) {
        var user = AuthGetter.getCurrentUser().get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/profile?upassword=false";
        }
        if(!newPassword.equals(matchPassword) || newPassword.equals(oldPassword)) {
            return "redirect:/profile?upassword=false";
        }
        user.setRawPassword(newPassword);
        user.encode(passwordEncoder);
        userRepository.save(user);
        return "redirect:/profile?upassword=true";
    }

}
