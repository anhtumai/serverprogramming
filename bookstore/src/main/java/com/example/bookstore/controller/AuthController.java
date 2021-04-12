package com.example.bookstore.controller;

import com.example.bookstore.model.BookstoreUserDetails;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.JpaUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private JpaUserDetailsManager manager;

    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("username", AuthGetter.getCurrentUsername());
        model.addAttribute("title", "Registration");
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUp(Model model) {
        model.addAttribute("username", AuthGetter.getCurrentUsername());
        model.addAttribute("title", "Registration");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/perform_signup")
    public String postPerformSignup(User user, Model model){
        if(manager.userExists(user.getUsername())){
            return "redirect:signup?signup=false";
        }
        else {
            manager.createUser(new BookstoreUserDetails(user));
            return "redirect:login?signup=true";
        }
    }
}
