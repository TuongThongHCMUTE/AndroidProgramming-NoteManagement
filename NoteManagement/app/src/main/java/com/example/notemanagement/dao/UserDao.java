package com.example.notemanagement.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notemanagement.entity.User;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void registerUser(User user);

    @Query("SELECT * FROM User WHERE email = (:email) AND password = (:password)")
    User login(String email, String password);

    @Query("SELECT * FROM user WHERE email = (:emailCheck)")
    User checkExistence(String emailCheck);

    @Update
    void  updateUser(User user);

    @Query("UPDATE user SET firstname = :firstname, lastname=:lastname, email=:email where userID = :uid")
    public void updateProfile(String firstname, String lastname, String email, int uid);

    @Query("SELECT * FROM user WHERE userID = :uid")
    public User getUserByID(int uid);

    @Query("UPDATE user SET password = :password WHERE userID = :uid")
    public void changePassword(String password, int uid);
}
