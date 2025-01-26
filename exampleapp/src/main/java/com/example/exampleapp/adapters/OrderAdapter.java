package com.example.exampleapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookslib.controllers.LibraryController;
import com.example.bookslib.models.Book;
import com.example.bookslib.models.Borrow;
import com.example.exampleapp.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Book> bookList;
    private LibraryController libraryController;

    public OrderAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        this.libraryController = new LibraryController();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horisontal_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.bookTitle.setText("Title: " + book.getTitle());
        holder.bookAuthor.setText("Author: " + book.getAuthor());
        holder.bookCategory.setText("Category: " + book.getCategory());

        holder.btnBorrow.setOnClickListener(v -> showConfirmationDialog(book, holder));
    }
    private void requestBorrow(Book book, OrderViewHolder holder) {
        String userId = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .getString("user_id", null);

        if (userId == null) {
            Toast.makeText(context, "User not found, please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        Borrow borrowRequest = new Borrow();
        borrowRequest.setUserId(userId);
        borrowRequest.setBookId(book.getId());

        Log.d("BorrowRequest", "Sending borrow request: " +
                "user_id=" + borrowRequest.getUserId() +
                ", book_id=" + borrowRequest.getBookId()
        );

        libraryController.requestBorrow(borrowRequest, new LibraryController.LibraryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(context, "Borrow request submitted for: " + book.getTitle(), Toast.LENGTH_SHORT).show();
                holder.btnBorrow.setEnabled(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BorrowRequestError", "Failed to request borrow: " + t.getMessage());
                Toast.makeText(context, "Failed to request borrow: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showConfirmationDialog(Book book, OrderViewHolder holder) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Borrow")
                .setMessage("Are you sure you want to borrow the book: " + book.getTitle() + "?")
                .setPositiveButton("Yes", (dialog, which) -> requestBorrow(book, holder))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }




    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateOrders(List<Book> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView bookTitle;
        private MaterialTextView bookAuthor;
        private MaterialTextView bookCategory;
        private ExtendedFloatingActionButton btnBorrow;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookCategory = itemView.findViewById(R.id.book_category);
            btnBorrow = itemView.findViewById(R.id.BTN_PCK_Finish);
        }
    }
}
