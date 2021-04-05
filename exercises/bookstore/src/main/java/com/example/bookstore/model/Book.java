package com.example.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String title, author, isbn;
   private int year;
   private double price;

   @ManyToOne
   @JoinColumn(name = "categoryid")
   private Category category;

    public Book(String title, String author, String isbn, int year, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.price = price;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
