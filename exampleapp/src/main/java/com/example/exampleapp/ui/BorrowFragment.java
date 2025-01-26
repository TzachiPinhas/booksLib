package com.example.exampleapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookslib.controllers.LibraryController;
import com.example.bookslib.models.Borrow;
import com.example.exampleapp.R;
import com.example.exampleapp.adapters.BorrowAdapter;
import com.example.exampleapp.databinding.FragmentBorrowBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class BorrowFragment extends Fragment {

    private FragmentBorrowBinding binding;
    private ArrayList<Borrow> borrowList;
    private BorrowAdapter borrowAdapter;
    private RecyclerView recyclerView;
    private MaterialButtonToggleGroup toggleButtonGroup;
    private LibraryController libraryController;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBorrowBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        borrowList = new ArrayList<>();
        libraryController = new LibraryController();
        findView();

        fetchBorrowsFromServer();

        return root;
    }

    private void findView() {
        recyclerView = binding.recyclerViewBorrows;
        toggleButtonGroup = binding.toggleButton;
    }

    private void fetchBorrowsFromServer() {
        String userId = requireActivity()
                .getSharedPreferences("AppPrefs", getContext().MODE_PRIVATE)
                .getString("user_id", null);

        if (userId == null) {
            Toast.makeText(getContext(), "User not found, please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        libraryController.getUserBorrows(userId, new LibraryController.LibraryCallback<List<Borrow>>() {
            @Override
            public void onSuccess(List<Borrow> borrows) {
                borrowList.clear();
                borrowList.addAll(borrows);
                setupAdapter();
                setupToggleButtons();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Error fetching borrows: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupAdapter() {
        borrowAdapter = new BorrowAdapter(getContext(), borrowList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(borrowAdapter);
    }

    private void setupToggleButtons() {
        toggleButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                List<Borrow> filteredBorrows = new ArrayList<>();
                if (checkedId == R.id.button1) {
                    for (Borrow borrow : borrowList) {
                        if ("pending".equalsIgnoreCase(borrow.getStatus())) {
                            filteredBorrows.add(borrow);
                        }
                    }
                } else if (checkedId == R.id.button2) {
                    for (Borrow borrow : borrowList) {
                        if ("approved".equalsIgnoreCase(borrow.getStatus())) {
                            filteredBorrows.add(borrow);
                        }
                    }
                } else if (checkedId == R.id.button3) {
                    for (Borrow borrow : borrowList) {
                        if ("returned".equalsIgnoreCase(borrow.getStatus())) {
                            filteredBorrows.add(borrow);
                        }
                    }
                }
                updateBorrows(filteredBorrows);
            }
        });
        toggleButtonGroup.check(R.id.button1);
    }

    private void updateBorrows(List<Borrow> filteredBorrows) {
        borrowAdapter = new BorrowAdapter(getContext(), filteredBorrows);
        recyclerView.setAdapter(borrowAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
