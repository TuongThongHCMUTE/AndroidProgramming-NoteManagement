package com.example.notemanagement.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notemanagement.dao.StatusDao;
import com.example.notemanagement.entity.Status;

@Database(entities = {Status.class}, version = 2)
public abstract class StatusDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Status.db";
    private static StatusDatabase instance;

    public static synchronized StatusDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), StatusDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() //Allow query on main thread
                    .build();
        }
        return instance;
    }

    public abstract StatusDao StatusDAO();
}
