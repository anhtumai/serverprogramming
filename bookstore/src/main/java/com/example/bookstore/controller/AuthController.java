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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @PostMapping("/signup")
    public String postPerformSignup(User user, Model model){
        var existedInfos = manager.getExistedInfos(user.getUsername(), user.getEmail());
        if (existedInfos.isEmpty()) {
            manager.createUser(new BookstoreUserDetails(user));
            return "redirect:login?signup=true";
        }
        model.addAttribute("signup", false);
        model.addAttribute("infos", existedInfos);
        model.addAttribute("username", AuthGetter.getCurrentUsername());
        return "signup";

    }
}
