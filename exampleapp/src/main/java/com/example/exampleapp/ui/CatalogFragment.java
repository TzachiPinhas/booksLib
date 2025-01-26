package com.example.exampleapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookslib.controllers.LibraryController;
import com.example.bookslib.models.Book;
import com.example.exampleapp.adapters.BookAdapter;
import com.example.exampleapp.databinding.FragmentCatalogBinding;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment {

    private FragmentCatalogBinding binding;
    private RecyclerView recycler_view_books;
    private LibraryController libraryController;
    private ArrayList <Book> books;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        books= new ArrayList<>();
        libraryController= new LibraryController();

        findView();
        fetchBooksFromServer();

        return root;
    }


    private void findView() {
        recycler_view_books = binding.booksRecyclerView;
    }


    private void updateAdapter() {
      BookAdapter bookAdapter = new BookAdapter(root.getContext(), books);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
      linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      recycler_view_books.setLayoutManager(linearLayoutManager);
      recycler_view_books.setAdapter(bookAdapter);
    }

    private void fetchBooksFromServer() {
        libraryController.getAllBooks(new LibraryController.LibraryCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> bookList) {
                books.clear();
                books.addAll(bookList);
                updateAdapter();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch books: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}