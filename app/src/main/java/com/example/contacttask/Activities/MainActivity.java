package com.example.contacttask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.contacttask.R;
import com.example.contacttask.ViewModels.AppViewModel;
import com.example.contacttask.database.User;

public class MainActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private EditText usernameET, passwordET;
    private Button signupBtn, loginBtn;
    private boolean shouldStartActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameET = findViewById(R.id.login_userName_ET);
        passwordET = findViewById(R.id.login_password_ET);
        signupBtn = findViewById(R.id.login_signUp_Btn);
        loginBtn = findViewById(R.id.login_login_Btn);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString().trim();
                String password = passwordET.getText().toString();
                appViewModel.getUserByUsername(username).observe(MainActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            if (validateInput(user)) {
                                // User found in database
                                if (user.getPassword().equals(password)) {
                                    // Passwords match, Proceed to Contact
                                    shouldStartActivity = true;
                                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Wrong passwords , display Toast
                                    Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // User not found, display Toast
                                Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // User not found, display Toast
                            Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                        if (shouldStartActivity) {
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            intent.putExtra("userId", user.getId());
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private Boolean validateInput(User user) {
        if (user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getEmail().isEmpty()) {
            return false;
        }
        return true;
    }
}