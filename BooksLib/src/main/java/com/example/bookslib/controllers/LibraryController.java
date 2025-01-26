package com.example.bookslib.controllers;

import com.example.bookslib.api.LibraryAPI;
import com.example.bookslib.models.Book;
import com.example.bookslib.models.Borrow;
import com.example.bookslib.models.LoginResponse;
import com.example.bookslib.models.User;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryController {
    private static final String BASE_URL = "https://android-seminar-flask-api.vercel.app/";
    private LibraryAPI api;

    public LibraryController() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(LibraryAPI.class);
    }

    public void getAvailableBooks(final LibraryCallback<List<Book>> callback) {
        Call<List<Book>> call = api.getAvailableBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getAllBooks(final LibraryCallback<List<Book>> callback) {
        Call<List<Book>> call = api.getAllBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void register(User user, final LibraryCallback<LoginResponse> callback) {
        Call<LoginResponse> call = api.register(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void login(User user, final LibraryCallback<String> callback) {
        Call<LoginResponse> call = api.login(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getUserId());
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }




    public void requestBorrow(Borrow borrowRequest, final LibraryCallback<Void> callback) {
        Call<Void> call = api.requestBorrow(borrowRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getUserBorrows(String userId, final LibraryCallback<List<Borrow>> callback) {
        Call<List<Borrow>> call = api.getUserBorrows(userId);
        call.enqueue(new Callback<List<Borrow>>() {
            @Override
            public void onResponse(Call<List<Borrow>> call, Response<List<Borrow>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Borrow>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface LibraryCallback<T> {
        void onSuccess(T result);
        void onFailure(Throwable t);
    }
}
