package com.example.notemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anychart.chart.common.dataentry.DataEntry;
import com.example.notemanagement.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note Note);

    @Update
    void updateNote(Note Note);

    @Query("SELECT * FROM Note WHERE userId = (:idUser)")
    List<Note> getListNote(int idUser);

    @Query("SELECT COUNT(*) FROM Note WHERE status = :status")
    int countQuantityByStatus(String status);

    @Delete
    void deleteNote(Note Note);
}
