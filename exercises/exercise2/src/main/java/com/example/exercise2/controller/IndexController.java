package com.example.exercise2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private List<String> friends = new ArrayList<String>();

    @GetMapping("/index")
    public String getIndex(@RequestParam(name="friend", required = false) String name,
                           Model model) {
        if (name != null && name.length() > 0)  friends.add(name);
        model.addAttribute("friends", friends);
        return "index";
    }

    @PostMapping("/index")
    public String postIndex(Model model, @RequestParam String name) {
        if (name.length() > 0)  friends.add(name);
        return "redirect:index";
    }
}
