package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return "booklist";
    }

    @GetMapping("/addbook")
    public String getAddBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
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
        return "editbook";
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody List<Book> getBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping("/book/{id}")
    public @ResponseBody Book getBookById(@PathVariable("id") Long bookId){
        var book = bookRepository.findById(bookId);
        if(book.isEmpty()) return null;
        return book.get();
    }

/*
    @PostMapping(path = "/book")
    public @ResponseBody Book createBook(@ModelAttribute ("book") Book book) {
        bookRepository.save(book);
        return book;
    }
*/

    @PostMapping(path = "/book")
    public @ResponseBody Map<Object,Object> createBookWithCategory(@RequestParam(name = "title") String title,
                               @RequestParam(name = "author") String author,
                               @RequestParam(name = "isbn") String isbn,
                               @RequestParam(name = "year") int year,
                               @RequestParam(name = "price") double price,
                               @RequestParam(name = "category", required = false, defaultValue = "") String categoryName) {
        var book = new Book(title, author, isbn, year, price);
        var map = new HashMap<Object, Object>();
        if(!categoryName.isEmpty())
        {
            var category = categoryRepository.findByName(categoryName);
            if(category == null) {
                map.put("success", false);
                map.put("reason", "category " + categoryName + " does not exist");
                return map;
            }
            book.setCategory(category);
        }
        bookRepository.save(book);
        map.put("success", true);
        map.put("book", book);
        return map;
    }
}
