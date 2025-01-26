package com.example.exampleapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookslib.controllers.LibraryController;
import com.example.bookslib.models.Book;
import com.example.exampleapp.LoginActivity;
import com.example.exampleapp.R;
import com.example.exampleapp.adapters.OrderAdapter;
import com.example.exampleapp.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Book> bookList;
    private LibraryController libraryController;
    private View root;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        bookList = new ArrayList<>();
        libraryController = new LibraryController();
        setupLogoutButton();


        setupRecyclerView();
        fetchAvailableBooks();

        return root;
    }

    private void setupLogoutButton() {
        binding.buttonLogout.setOnClickListener(v -> {
            requireActivity()
                    .getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                    .edit()
                    .remove("user_id")
                    .apply();

            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

            // מעבר לעמוד ההתחברות
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        recyclerView = binding.recyclerViewBooks;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new OrderAdapter(getContext(), bookList);
        recyclerView.setAdapter(orderAdapter);
    }

    private void fetchAvailableBooks() {
        libraryController.getAvailableBooks(new LibraryController.LibraryCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> books) {
                bookList.clear();
                for (Book book : books) {
                    if (book.getStock() > 0) {
                        bookList.add(book);
                        Log.d("BookData", "Received book: " + book.getTitle() + ", ID: " + book.getId());
                    }
                }
                orderAdapter.updateOrders(bookList);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Error fetching books: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
