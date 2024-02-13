package com.example.contacttask.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contacttask.Repository.AppRepository;
import com.example.contacttask.database.Contact;
import com.example.contacttask.database.User;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Contact>> allContact;
    private LiveData<List<Contact>> contacts;
    private LiveData<Contact> contactsId;
    private LiveData<User> userByUsername;
    private LiveData<User> userByEmail;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allContact = repository.getAllContacts();
    }

    public void registerUser(User user) {
        repository.registerUser(user);
    }

    public LiveData<User> getUserByUsername(String username) {
        userByUsername = repository.getUserByUsername(username);
        return userByUsername;
    }

    public LiveData<User> getUserByEmail(String email) {
        userByEmail = repository.getUserByEmail(email);
        return userByEmail;
    }

    public void init(int userId) {
        contacts = repository.getContactsByUserId(userId);
    }


    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void insertContact(Contact contact) {
        repository.insertContact(contact);
    }

    public void updateContact(Contact contact) {
        repository.updateContact(contact);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }

    public LiveData<Contact> getContactById(int contactId) {
        contactsId = repository.getContactById(contactId);
        return contactsId;
    }

}
