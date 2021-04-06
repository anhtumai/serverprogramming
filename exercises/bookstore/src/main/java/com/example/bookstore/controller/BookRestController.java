package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    List<Book> getBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping("/api/book/{id}")
    public @ResponseBody Book getBookById(@PathVariable("id") Long bookId){
        var book = bookRepository.findById(bookId);
        if(book.isEmpty()) return null;
        return book.get();
    }

    @PostMapping("/api/book")
    public @ResponseBody
    Map<Object,Object> createBookWithCategory(@RequestParam(name = "title") String title,
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
