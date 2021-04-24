package com.example.notemanagement.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notemanagement.dao.CategoryDao;
import com.example.notemanagement.entity.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class CategoryDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "category.db";
    private static CategoryDatabase instance;

    public static synchronized CategoryDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CategoryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() //Allow query on main thread
                    .build();
        }
        return instance;
    }

    public abstract CategoryDao categoryDAO();
}
