package com.example.exampleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookslib.models.Borrow;
import com.example.exampleapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.BorrowViewHolder> {

    private Context context;
    private List<Borrow> borrowList;

    public BorrowAdapter(Context context, List<Borrow> borrowList) {
        this.context = context;
        this.borrowList = borrowList;
    }

    @NonNull
    @Override
    public BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horisontal_borrow_item, parent, false);
        return new BorrowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
        Borrow borrow = borrowList.get(position);
        holder.bookTitle.setText("Book Title: " + borrow.getBookTitle());
        holder.borrowStatus.setText("Status: " + borrow.getStatus());
        holder.borrowDate.setText("Borrowed At: " + borrow.getBorrowedAt().toString());
    }


    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    public Borrow getItem(int position) {
        return borrowList.get(position);
    }

    public static class BorrowViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView bookTitle;
        private MaterialTextView borrowDate;
        private MaterialTextView borrowStatus;

        public BorrowViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.borrow_book_title);
            borrowDate = itemView.findViewById(R.id.borrow_date);
            borrowStatus = itemView.findViewById(R.id.borrow_status);
        }
    }
}
