package com.example.contacttask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacttask.Adapters.MyRecyclerAdapter;
import com.example.contacttask.R;
import com.example.contacttask.ViewModels.AppViewModel;
import com.example.contacttask.database.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_Contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        getSupportActionBar().setTitle("Contact List");
        FloatingActionButton addContactBtn = findViewById(R.id.add_contactBtn);
        userId = getIntent().getIntExtra("userId", -1);
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, AddEditContactActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        //Log.d("user","user: "+ userId);
        if (userId != -1) {
            //Log.d("user", "Logged-in user: " + userId);
            appViewModel.init(userId);
            appViewModel.getContacts().observe(ContactActivity.this, contacts -> {
                // Update UI with the list of contacts
                adapter.setContact(contacts);
            });
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                appViewModel.deleteContact(adapter.getContactPos(viewHolder.getAbsoluteAdapterPosition()));
                Toast.makeText(ContactActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(ContactActivity.this, AddEditContactActivity.class);
                intent.putExtra("contactId", contact.getId());
                startActivity(intent);
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton defaultRadioButton = findViewById(R.id.radioShowAll);
        defaultRadioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioShowAll:
                        adapter.setShowNameOnly(false); // Set the showNameOnly flag to false
                        break;
                    case R.id.radioShowNameOnly:
                        adapter.setShowNameOnly(true); // Set the showNameOnly flag to true
                        break;
                }
            }
        });



    }
}
