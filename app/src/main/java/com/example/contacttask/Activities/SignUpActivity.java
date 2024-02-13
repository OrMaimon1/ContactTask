package com.example.contacttask.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.contacttask.R;
import com.example.contacttask.ViewModels.AppViewModel;
import com.example.contacttask.database.User;

public class SignUpActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private EditText username, email, password;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.signUp_userName_ET);
        email = findViewById(R.id.signUp_email_ET);
        password = findViewById(R.id.signUp_password_ET);
        signup = findViewById(R.id.signUp_signUp_Btn);
        getSupportActionBar().setTitle("Sign Up");
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup.setEnabled(false);
                User user = new User(username.getText().toString(), email.getText().toString(), password.getText().toString());
                if (validateInput(user)) {
                    checkUsernameAndEmail(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                    signup.setEnabled(true);
                }
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

    //observer always got triggered more then once so need to separate logic!(twice)

    private void checkUsernameAndEmail(User user) {
        final LiveData<User> usernameLiveData = appViewModel.getUserByUsername(user.getUsername());
        final LiveData<User> emailLiveData = appViewModel.getUserByEmail(user.getEmail());

        final Observer<User> usernameObserver = new Observer<User>() {
            @Override
            public void onChanged(User existingUserByUsername) {
                usernameLiveData.removeObserver(this);
                if (existingUserByUsername != null) {
                    Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    signup.setEnabled(true);
                } else {
                    checkEmail(user, emailLiveData);
                }
            }
        };
        usernameLiveData.observe(SignUpActivity.this, usernameObserver);
    }

    private void checkEmail(User user, LiveData<User> emailLiveData) {
        final Observer<User> emailObserver = new Observer<User>() {
            @Override
            public void onChanged(User existingUserByEmail) {
                emailLiveData.removeObserver(this);
                if (existingUserByEmail != null) {
                    Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    signup.setEnabled(true);
                } else {
                    appViewModel.registerUser(user);
                    Toast.makeText(getApplicationContext(), "User signed Up", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        emailLiveData.observe(SignUpActivity.this, emailObserver);
    }



}
