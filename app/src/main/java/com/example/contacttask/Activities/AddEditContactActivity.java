package com.example.contacttask.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.contacttask.R;
import com.example.contacttask.ViewModels.AppViewModel;
import com.example.contacttask.database.Contact;

public class AddEditContactActivity extends AppCompatActivity {


    private EditText nameET, lastNameET, phoneNumberET;
    private Button saveBtn, backBtn;
    private AppViewModel appViewModel;
    private int userId;
    private Contact existingContact; // For editing existing contact


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameET = findViewById(R.id.contact_userNameET);
        lastNameET = findViewById(R.id.contact_lastNameET);
        phoneNumberET = findViewById(R.id.contact_phoneNumber);
        saveBtn = findViewById(R.id.contact_saveBtn);
        backBtn = findViewById(R.id.contact_backBtn);
        userId = getIntent().getIntExtra("userId", -1);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class); // Initialize the ViewModel
        getSupportActionBar().setTitle("Add Contact");
        int contactId = getIntent().getIntExtra("contactId", -1);
        if (contactId != -1) {
            // Load the existing contact from the database
            appViewModel.getContactById(contactId).observe(this, contact -> {
                existingContact = contact;
                if (existingContact != null) {
                    nameET.setText(existingContact.getFirstName());
                    lastNameET.setText(existingContact.getLastName());
                    phoneNumberET.setText(existingContact.getPhoneNumber());
                    getSupportActionBar().setTitle("Edit Contact");

                }
            });
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String phoneNumber = phoneNumberET.getText().toString();
                if (name.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(AddEditContactActivity.this, "please insert name, last name and phone number", Toast.LENGTH_SHORT).show();
                } else {
                    if (existingContact != null) {
                        // Update existing contact
                        existingContact.setFirstName(name);
                        existingContact.setLastName(lastName);
                        existingContact.setPhoneNumber(phoneNumber);
                        appViewModel.updateContact(existingContact);
                    } else {
                        Contact newContact = new Contact(name, lastName, phoneNumber, userId);
                        appViewModel.insertContact(newContact);
                    }
                    finish();
                }

            }
        });
    }
}