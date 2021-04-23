package com.example.notemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notemanagement.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note Note);

    @Update
    void updateNote(Note Note);

    @Query("SELECT * FROM Note")
    List<Note> getListNote();

    @Delete
    void deleteNote(Note Note);
}
