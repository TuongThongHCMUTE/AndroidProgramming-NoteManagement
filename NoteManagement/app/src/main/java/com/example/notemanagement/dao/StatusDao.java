package com.example.notemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notemanagement.entity.Status;

import java.util.List;

@Dao
public interface StatusDao {
    @Insert
    void insertStatus(Status status);

    @Update
    void updateStatus(Status status);

    @Query("SELECT * FROM Status")
    List<Status> getListStatus();

    @Delete
    void deleteStatus(Status status);

    @Query("Select name From Status")
    String[] getStatusName();
}
