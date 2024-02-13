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
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void registerUser(User user) ;

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users_table WHERE id = :userId")
    LiveData<User> getUserById(int userId);

    @Query("SELECT * FROM users_table WHERE username = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM users_table WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM users_table ORDER BY username ASC")
    LiveData<List<User>> getUserOrderByName();



}