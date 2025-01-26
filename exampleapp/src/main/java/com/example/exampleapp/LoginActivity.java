package com.example.exampleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookslib.controllers.LibraryController;
import com.example.bookslib.models.LoginResponse;
import com.example.bookslib.models.User;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnLogin;
    private MaterialButton btnRegister;
    private LibraryController libraryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        libraryController = new LibraryController();


        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(view -> showSignInDialog());
        btnRegister.setOnClickListener(view -> showSignUpDialog());

    }


    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sign_in, null);
        builder.setView(dialogView);

        final EditText inputUsername = dialogView.findViewById(R.id.input_username);
        final EditText inputPassword = dialogView.findViewById(R.id.input_password);
        Button btnSignIn = dialogView.findViewById(R.id.btn_sign_in);

        AlertDialog dialog = builder.create();

        btnSignIn.setOnClickListener(view -> {
            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                inputUsername.setError("Enter your username");
            } else if (TextUtils.isEmpty(password)) {
                inputPassword.setError("Enter your password");
            } else {
                dialog.dismiss();
                signIn(username, password);
            }
        });

        dialog.show();
    }


    private void showSignUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sign_in, null);
        builder.setView(dialogView);

        final EditText inputUsername = dialogView.findViewById(R.id.input_username);
        final EditText inputPassword = dialogView.findViewById(R.id.input_password);
        Button btnSignIn = dialogView.findViewById(R.id.btn_sign_in);

        btnSignIn.setText("Sign Up");

        AlertDialog dialog = builder.create();

        btnSignIn.setOnClickListener(view -> {
            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                inputUsername.setError("Enter your username");
            } else if (TextUtils.isEmpty(password)) {
                inputPassword.setError("Enter your password");
            } else {
                dialog.dismiss();
                signUp(username, password);
            }
        });

        dialog.show();
    }

    private void signIn(String userName, String password) {
        User user = new User(userName, password);
        libraryController.login(user, new LibraryController.LibraryCallback<String>() {
            @Override
            public void onSuccess(String userId) {
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                prefs.edit().putString("user_id", userId).apply();

                navigateToMainActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void signUp(String username, String password) {
        User user = new User(username, password);
        libraryController.register(user, new LibraryController.LibraryCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                Toast.makeText(LoginActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                showSignInDialog();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(LoginActivity.this, "Registration failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    }
