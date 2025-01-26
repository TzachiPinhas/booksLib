package com.example.bookslib.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Borrow {
    @SerializedName("_id")
    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("book_id")
    private String bookId;

    @SerializedName("book_title")
    private String bookTitle;

    @SerializedName("borrowed_at")
    private String borrowedAt;  // שינוי מ-String ל-Date

    @SerializedName("status")
    private String status;  // "pending", "approved", "returned"

    public Borrow() {
    }

    public Borrow(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public Borrow(String id, String userId, String userName, String bookId, String bookTitle, String borrowedAt, String status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowedAt = borrowedAt;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public Borrow setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Borrow setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getBookId() {
        return bookId;
    }

    public Borrow setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getBorrowedAt() {
        return borrowedAt;
    }

    public Borrow setBorrowedAt(String borrowedAt) {
        this.borrowedAt = borrowedAt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Borrow setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public Borrow setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Borrow setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    @Override
    public String toString() {
        return "Borrow{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", borrowedAt=" + borrowedAt +
                ", status='" + status + '\'' +
                '}';
    }
}