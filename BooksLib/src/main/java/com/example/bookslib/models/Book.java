package com.example.bookslib.models;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("_id")
    private String id;
    private String title;
    private String author;
    private int stock;
    private String category;


    public Book() {
    }

    public Book(String title, String author, String category, int stock) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }



    public String getId() {
        return id;
    }

    public Book setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Book setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Book setCategory(String category) {
        this.category = category;
        return this;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                '}';
    }
}
