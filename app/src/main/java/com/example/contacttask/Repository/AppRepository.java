package com.example.contacttask.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.contacttask.database.AppDatabase;
import com.example.contacttask.database.Contact;
import com.example.contacttask.database.ContactDao;
import com.example.contacttask.database.User;
import com.example.contacttask.database.UserDao;

import java.util.List;

public class AppRepository {

    private ContactDao contactDao;
    private UserDao userDao;
    private LiveData<List<Contact>> allContacts;
    private LiveData<List<Contact>> allUserContacts;
    private LiveData<List<User>> allUsers;


    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        contactDao = database.contactDao();
        userDao = database.userDao();
        allContacts = contactDao.getContactOrderByName();
        allUsers = userDao.getUserOrderByName();
        /*Retrofit retrofit = RetrofitClient.getClient();
        genderizeApiService = retrofit.create(GenderizeApiService.class);*/
    }

    public void registerUser(User user) {
        new RegisterUserAsyncTask(userDao).execute(user);
    }

    public void insertContact(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public LiveData<List<Contact>> getContactsByUserId(int userId) {
        return contactDao.getContactsByUserId(userId);
    }

    public void updateContact(Contact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteContact(Contact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }


    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUserName(username);
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public LiveData<Contact> getContactById(int contactid) {
        return contactDao.getContactById(contactid);
    }

    private static class RegisterUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private RegisterUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.registerUser(users[0]);
            return null;
        }
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        private InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insertContact(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        private UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.updateContact(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        private DeleteContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.deleteContact(contacts[0]);
            return null;
        }
    }

    /*public void fetchGender(String name, final GenderizeCallback callback) {
        genderizeApiService.getGender(name).enqueue(new Callback<GenderizeResponse>() {
            @Override
            public void onResponse(Call<GenderizeResponse> call, Response<GenderizeResponse> response) {
                if (response.isSuccessful()) {
                    GenderizeResponse genderizeResponse = response.body();
                    callback.onSuccess(genderizeResponse);
                } else {
                    callback.onError(new Throwable("Failed to fetch info"));
                }
            }
            @Override
            public void onFailure(Call<GenderizeResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface GenderizeCallback {
        void onSuccess(GenderizeResponse response);
        void onError(Throwable throwable);
    }*/
}
