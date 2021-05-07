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

    @Query("SELECT COUNT(*) FROM Note WHERE userId = :uid AND status = :status")
    int countNotesOfUserByStatusID(int uid, String status);

    @Delete
    void deleteNote(Note Note);

    @Query("UPDATE Note SET category = :newCat WHERE category = :oldCat AND userId = :uID")
    void changeCategoryName(String newCat, String oldCat, int uID);

    @Query("UPDATE Note SET priority = :newPri WHERE priority = :oldPri AND userId = :uID")
    void changePriorityName(String newPri, String oldPri, int uID);

    @Query("UPDATE Note SET status = :newSta WHERE status = :oldSta AND userId = :uID")
    void changeStatusName(String newSta, String oldSta, int uID);
}
