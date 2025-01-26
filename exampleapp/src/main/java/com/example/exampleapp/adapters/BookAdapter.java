package com.example.exampleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookslib.models.Book;
import com.example.exampleapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horisontal_book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);
        holder.bookTitle.setText("Title: " + book.getTitle());
        holder.bookAuthor.setText("Author: " + book.getAuthor());
        holder.bookCategory.setText("Category: " + book.getCategory());
        holder.bookStock.setText("Stock: " + book.getStock());

        if (book.getStock() == 0) {
            holder.bookStock.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_dark));
        } else {
            holder.bookStock.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark));
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public Book getItem(int position) {
        return bookList.get(position);
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView bookTitle;
        private MaterialTextView bookAuthor;
        private MaterialTextView bookCategory;
        private MaterialTextView bookStock;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookCategory = itemView.findViewById(R.id.book_category);
            bookStock = itemView.findViewById(R.id.book_stock);
        }
    }
}