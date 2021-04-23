package com.example.notemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notemanagement.entity.Priority;

import java.util.List;

@Dao
public interface PriorityDao {
    @Insert
    void insertPriority(Priority priority);

    @Update
    void updatePriority(Priority priority);

    @Query("SELECT * FROM Priority")
    List<Priority> getListPriority();

    @Delete
    void deletePriority(Priority priority);

    @Query("Select name From Priority")
    String[] getPriorityName();
}
