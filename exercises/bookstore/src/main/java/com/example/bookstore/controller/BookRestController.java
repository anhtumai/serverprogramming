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
    public @ResponseBody Map<Object,Object> createBookWithCategory(
                                            @RequestParam(name = "title") String title,
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
                map.put("error", "category " + categoryName + " does not exist");
                return map;
            }
            book.setCategory(category);
        }
        bookRepository.save(book);
        map.put("success", true);
        map.put("book", book);
        return map;
    }

    @DeleteMapping("/api/book/{id}")
    public @ResponseBody Map<Object, Object> deleteBook(@PathVariable(name = "id") Long bookId) {
        var optionalBook = bookRepository.findById(bookId);
        var map = new HashMap<>();
        if (optionalBook.isEmpty()) {
            map.put("success", false);
            map.put("error", "book ID " + bookId + " does not exist");
            return map;
        }
        bookRepository.deleteById(bookId);
        map.put("success", true);
        map.put("message", "book with ID " + bookId + " has been removed");
        return map;
    }

    @PutMapping("/api/book/{id}")
    public @ResponseBody Map<Object,Object> putBook(@PathVariable(name = "id") Long bookId,
                                                    @RequestParam Map<String, String> params){
        var optionalBook = bookRepository.findById(bookId);
        var map = new HashMap<>();
        if (optionalBook.isEmpty()) {
            map.put("success", false);
            map.put("error", "book ID " + bookId + " does not exist");
            return map;
        }
        var book = optionalBook.get();
        var validMap = validateRequestParams(params);
        if((boolean) validMap.get("valid")) {
            if (params.containsKey("title")) book.setTitle(params.get("title"));
            if (params.containsKey("author")) book.setAuthor(params.get("author"));
            if (params.containsKey("isbn")) book.setIsbn(params.get("isbn"));
            if (params.containsKey("year")) book.setYear(Integer.parseInt(params.get("year")));
            if (params.containsKey("price")) book.setPrice(Double.parseDouble(params.get("price")));
            if (params.containsKey("category"))
                book.setCategory(categoryRepository.findByName(params.get("category")));
            bookRepository.save(book);
            map.put("success", true);
            map.put("book", book);
        }
        else {
            map.put("success", false);
            map.put("error", validMap.get("error"));
        }
        return map;
    }

    private Map<String, Object> validateRequestParams(Map<String,String> params){
        boolean valid = true;
        String failedReason="";
        if (params.containsKey("year")) {
            try {
                var year = Integer.parseInt(params.get("year"));
            } catch (NumberFormatException nfe) {
                failedReason += "Year should be an integer.\n";
                valid = false;
            }
        }
        if (params.containsKey("price")) {
            try {
                var price = Double.parseDouble(params.get("price"));
            } catch (NumberFormatException nfe) {
                failedReason += "Price should be a double.\n";
                valid = false;
            }
        }

        if (params.containsKey("category")) {
            var categoryName = params.get("category");
            var category = categoryRepository.findByName(categoryName);
            if (category == null) {
                failedReason += "Category " + categoryName + " does not exist.\n";
                valid = false;
            }
        }
        var map = new HashMap<String, Object>();
        map.put("valid", valid);
        if (!valid) map.put("error", failedReason);
        return map;
    }

}
