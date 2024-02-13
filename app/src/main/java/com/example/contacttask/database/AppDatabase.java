package com.example.contacttask.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Contact.class, User.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase AppDatabase;

    public static synchronized AppDatabase getInstance(Context context) {
        if (AppDatabase == null) {
            AppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return AppDatabase;
    }
}
