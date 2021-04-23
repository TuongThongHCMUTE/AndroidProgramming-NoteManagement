package com.example.notemanagement.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notemanagement.dao.PriorityDao;
import com.example.notemanagement.entity.Priority;

@Database(entities = {Priority.class}, version = 2)
public abstract class PriorityDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "priority.db";
    private static PriorityDatabase instance;

    public static synchronized PriorityDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PriorityDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() //Allow query on main thread
                    .build();
        }
        return instance;
    }

    public abstract PriorityDao PriorityDAO();
}
