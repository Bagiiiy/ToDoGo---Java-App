package com.example.to_do_app_mad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class signOut extends AppCompatActivity {

    private TextView userNameTextView, emailTextView;
    private Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        // Initialize TextViews and Button
        userNameTextView = findViewById(R.id.textView4);
        emailTextView = findViewById(R.id.textView5);
        signoutBtn = findViewById(R.id.signOutBtn);

        // Retrieve user details from SharedPreferences and display
        displayUserDetails();

        // Set click listener for the edit info button
        signoutBtn.setOnClickListener(new View.OnClickListener() {
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
        View dialogView = getLayoutInflater().inflate(R.layout.signoutbox, null);

        // Initialize EditTexts and buttons

        Button btnDialogOk = dialogView.findViewById(R.id.btnDialogYes);
        Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogNo);


        // Build and show the dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listener for Ok button
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signOut.this, Signinpage.class);
                startActivity(intent);

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
