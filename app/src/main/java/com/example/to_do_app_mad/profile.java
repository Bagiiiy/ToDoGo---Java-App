package com.example.to_do_app_mad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {

    private TextView userNameTextView, emailTextView;
    private Button editInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize TextViews and Button
        userNameTextView = findViewById(R.id.textView4);
        emailTextView = findViewById(R.id.textView5);
        editInfoButton = findViewById(R.id.button3);

        // Retrieve user details from SharedPreferences and display
        displayUserDetails();

        // Set click listener for the edit info button
        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    // Method to display user details
    private void displayUserDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString(username + "_email", "No email found");

        userNameTextView.setText("User name: " + username);
        emailTextView.setText("Email: " + email);
    }

    // Method to show edit dialog
    private void showEditDialog() {
        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.editbox, null);

        // Initialize EditTexts and buttons
        EditText etUsername = dialogView.findViewById(R.id.etUsername);
        EditText etEmail = dialogView.findViewById(R.id.email);
        Button btnDialogOk = dialogView.findViewById(R.id.btnDialogOk);
        Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);

        // Retrieve current user details
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString(username + "_email", "No email found");

        // Set current user details to EditTexts
        etUsername.setText(username);
        etEmail.setText(email);

        // Build and show the dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listener for Ok button
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get edited details from EditTexts
                String newUsername = etUsername.getText().toString().trim();
                String newEmail = etEmail.getText().toString().trim();

                // Save edited details to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", newUsername);
                editor.putString(newUsername + "_email", newEmail);
                editor.apply();

                // Update displayed details
                displayUserDetails();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Set click listener for Cancel button
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
    }
}
