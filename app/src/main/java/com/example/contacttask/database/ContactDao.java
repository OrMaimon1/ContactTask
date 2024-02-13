package com.example.contacttask.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("SELECT * FROM contacts_table WHERE id = :contactId")
    LiveData<Contact> getContactById(int contactId);

    @Query("SELECT * FROM contacts_table WHERE userId = :userId")
    LiveData<List<Contact>> getContactsByUserId(int userId);

    @Query("SELECT * FROM contacts_table ORDER BY name ASC")
    LiveData<List<Contact>> getContactOrderByName();


}

