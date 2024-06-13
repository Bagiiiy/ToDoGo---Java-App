package com.example.to_do_app_mad;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signinpage extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnForgotPassword = findViewById(R.id.btnForgotPassword);
        Button btnSignup = findViewById(R.id.btnSignup);
        Button btnLogin = findViewById(R.id.button);

        btnForgotPassword.setOnClickListener(v -> {
            // Navigate to Forgot Password Activity
            //Intent intent = new Intent(Signinpage.this, forgetpassword.class);
            //startActivity(intent);
        });

        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Signinpage.this, signup.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            // Check login credentials
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInputs(username, password)) {
                if (checkCredentials(username, password)) {
                    // Retrieve the email associated with the entered username
                    String email = sharedPreferences.getString(username + "_email", "");

                    // Save the username and email
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("email", email);
                    editor.apply();

                    // If credentials are correct, navigate to Todo List Activity
                    Intent intent = new Intent(Signinpage.this, todolist.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Signinpage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    // Method to validate user inputs
    private boolean validateInputs(String username, String password) {
        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Method to check user credentials
    private boolean checkCredentials(String username, String password) {
        // Retrieve password associated with the entered username
        String savedPassword = sharedPreferences.getString(username + "_password", "");

        // Check if the entered password matches with the saved one
        return password.equals(savedPassword);
    }
}
