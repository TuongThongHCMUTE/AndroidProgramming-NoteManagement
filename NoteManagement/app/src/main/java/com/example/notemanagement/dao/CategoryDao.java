package com.example.notemanagement.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notemanagement.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM Category")
    List<Category> getListCategory();
}
