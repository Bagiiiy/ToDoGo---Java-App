package com.example.to_do_app_mad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signup extends AppCompatActivity {
    private EditText etUsername, etPassword, etCpassword, etEmail;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EdgeToEdge.enable(this);

        // Initializing SharedPreferences
        sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);

        // Initializing the views
        etUsername = findViewById(R.id.editTextText);
        etPassword = findViewById(R.id.editTextTextPassword);
        etCpassword = findViewById(R.id.editTextTextPassword2);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        Button button = findViewById(R.id.button);

        // Setting the padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setting the onClick listener for the register button
        button.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etCpassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (validateInputs(username, password, confirmPassword, email)) {
                // Check if the username already exists
                if (isUserExists(username)) {
                    Toast.makeText(this, "Username already exists, please choose another one", Toast.LENGTH_SHORT).show();
                } else {
                    // Store user details in SharedPreferences
                    saveUserDetails(username, password, email);

                    // Proceed to the signin activity or any other activity
                    Intent intent = new Intent(signup.this, Signinpage.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                }
            }
        });
    }

    // Method to validate user inputs
    private boolean validateInputs(String username, String password, String confirmPassword, String email) {
        if (username.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Additional validation can be added here (e.g., email format, password strength)
        return true;
    }

    // Method to check if a user already exists
    private boolean isUserExists(String username) {
        return sharedPreferences.contains(username);
    }

    // Method to save user details in SharedPreferences
    private void saveUserDetails(String username, String password, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username + "_password", password);
        editor.putString(username + "_email", email);


        editor.apply();
    }
}
