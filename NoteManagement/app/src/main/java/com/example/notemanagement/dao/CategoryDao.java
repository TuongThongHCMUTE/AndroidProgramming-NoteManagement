package com.example.notemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notemanagement.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Query("SELECT * FROM Category")
    List<Category> getListCategory();

    @Delete
    void deleteCategory(Category category);

    @Query("Select name From Category")
    String[] getCategoryName();

    @Query("Select id From Category")
    int[] getCategoryId();
}
