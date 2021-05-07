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

    @Query("SELECT * FROM Category WHERE userId = :uID")
    List<Category> getListCategory(int uID);

    @Delete
    void deleteCategory(Category category);

    @Query("Select name From Category WHERE userId = :uID")
    String[] getCategoryName(int uID);

    @Query("Select id From Category")
    int[] getCategoryId();
}
