package com.example.notemanagement.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notemanagement.dao.NoteDao;
import com.example.notemanagement.entity.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "note.db";
    private static NoteDatabase instance;

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() //Allow query on main thread
                    .build();
        }
        return instance;
    }

    public abstract NoteDao NoteDAO();
}
