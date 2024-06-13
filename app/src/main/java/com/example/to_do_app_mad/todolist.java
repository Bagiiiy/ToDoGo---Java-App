package com.example.to_do_app_mad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class todolist extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton signOutButton, editButton, aboutUsButton;

    private NavigationView navigationView;
    private ImageButton sideNaviButton;


    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<String> tasks;
    private ImageButton addTaskButton;
    private String username;

    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        // Initialize the DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Initialize the buttons
        sideNaviButton = findViewById(R.id.sideNavi);


        recyclerView = findViewById(R.id.recycler_view);
        userName = findViewById(R.id.userName);
        addTaskButton = findViewById(R.id.addTask);


        // Get the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        userName.setText(username);

        tasks = new ArrayList<>();

        // Load tasks for the current user
        loadTasks();

        taskAdapter = new TaskAdapter(this, tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptTaskInput();
            }
        });
        sideNaviButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        setupNavigationDrawerButtons();
    }


    private void setupNavigationDrawerButtons() {
        editButton = findViewById(R.id.imageButton);
        signOutButton = findViewById(R.id.imageButton2);
        aboutUsButton = findViewById(R.id.imageButton7);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign out action
                Toast.makeText(todolist.this, " Loading", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(todolist.this, profile.class);
                startActivity(intent);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit action
                Toast.makeText(todolist.this, "Sign Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(todolist.this, signOut.class);
                startActivity(intent);
            }
        });

        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle about us action
                Intent intent = new Intent(todolist.this, devInfo.class);
                startActivity(intent);
            }
        });
    }
    // Method to load tasks for the current user from SharedPreferences
    private void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasks_" + username, Context.MODE_PRIVATE);
        int taskCount = sharedPreferences.getInt("taskCount", 0);
        tasks.clear();
        for (int i = 0; i < taskCount; i++) {
            String task = sharedPreferences.getString("task_" + i, "");
            tasks.add(task);
        }
    }

    // Method to save tasks for the current user to SharedPreferences
    void saveTasks(List<String> tasks) {
        SharedPreferences sharedPreferences = getSharedPreferences("tasks_" + username, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("taskCount", tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            editor.putString("task_" + i, tasks.get(i));
        }
        editor.apply();
    }


    // Method to prompt the user to enter a new task
    private void promptTaskInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");

        final EditText input = new EditText(this);
        input.setHint("Enter Task Name");
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskName = input.getText().toString().trim();
                if (!taskName.isEmpty()) {
                    tasks.add(taskName);
                    taskAdapter.notifyDataSetChanged();
                    // Save tasks for the current user to SharedPreferences
                    saveTasks(tasks);
                } else {
                    Toast.makeText(todolist.this, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}