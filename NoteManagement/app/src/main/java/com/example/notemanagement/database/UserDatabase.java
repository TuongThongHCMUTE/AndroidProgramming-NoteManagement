package com.example.notemanagement.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notemanagement.dao.UserDao;
import com.example.notemanagement.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private  static final String dbName = "user.db";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
}
