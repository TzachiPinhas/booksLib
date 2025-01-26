package com.example.bookslib.api;

import com.example.bookslib.models.Book;
import com.example.bookslib.models.Borrow;
import com.example.bookslib.models.LoginResponse;
import com.example.bookslib.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LibraryAPI {

    @GET("/books/available")
    Call<List<Book>> getAvailableBooks();
    @GET("/books")
    Call<List<Book>> getAllBooks();
    @POST("/register")
    Call<LoginResponse> register(@Body User user);
    @POST("/login")
    Call<LoginResponse> login(@Body User user);
    @POST("/request-borrow")
    Call<Void> requestBorrow(@Body Borrow borrowRequest);
    @GET("/my-borrows/{user_id}")
    Call<List<Borrow>> getUserBorrows(@Path("user_id") String userId);

}
