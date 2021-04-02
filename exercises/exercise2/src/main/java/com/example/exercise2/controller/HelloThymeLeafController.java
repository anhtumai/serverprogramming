package com.example.exercise2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloThymeLeafController {
    @GetMapping("/hello-thymeleaf")
    public String hello(@RequestParam(name="name") String name,
                        @RequestParam(name="age") Integer age,
                        Model model) {
        model.addAttribute("name",name);
        model.addAttribute("age",age);
        return "hello-thymeleaf";
    }
}
