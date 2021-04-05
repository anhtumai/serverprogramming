package com.example.bookstore;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return (args) -> {
            categoryRepository.save(new Category("Thrilling"));
            categoryRepository.save(new Category("Horror"));
            categoryRepository.save(new Category("Action"));
            categoryRepository.save(new Category("Politic"));
        };
    }

    @Bean
    public CommandLineRunner initBooks(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return (args) -> {
            var book1  = new Book(
                    "A Farewell to Arms",
                    "Ernest Hemingway",
                    "1232323-21",
                    1929,
                    12.21);
            book1.setCategory(categoryRepository.findByName("Action"));
            var book2 = new Book(
                    "Animal Farm",
                    "George Orwell",
                    "2212343-5",
                    1945,
                    12.58);
            book2.setCategory(categoryRepository.findByName("Politic"));

            bookRepository.save(book1);
            bookRepository.save(book2);
        };
    }

}
