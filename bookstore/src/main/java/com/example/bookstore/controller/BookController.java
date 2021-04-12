package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookstoreUserDetails;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String getHome(){
        return "redirect:booklist";
    }

    @GetMapping("/index")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/booklist")
    public String getBooklist(Model model){
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("username", AuthGetter.getCurrentUsername());
        model.addAttribute("title", "Book List");
        return "booklist";
    }

    @GetMapping("/addbook")
    public String getAddBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("username", AuthGetter.getCurrentUsername());
        model.addAttribute("title", "Add Book");
        return "addbook";
    }

    @PostMapping("/save")
    public String postSave(Book book) {
        bookRepository.save(book);
        return "redirect:booklist";
    }


    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        bookRepository.deleteById(bookId);
        return "redirect:../booklist";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("book", bookRepository.findById(bookId));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("username", AuthGetter.getCurrentUsername());
        model.addAttribute("title", "Edit Book");
        return "editbook";
    }
}
