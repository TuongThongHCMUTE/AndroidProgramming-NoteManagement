package com.example.notemanagement.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notemanagement.entity.User;
import com.example.notemanagement.entity.UserEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void registerUser(User user);

    @Query("SELECT * FROM user WHERE email = (:email) AND password = (:password)")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM user WHERE email = (:emailCheck)")
    UserEntity checkExistence(String emailCheck);

    @Query("UPDATE user SET firstname = :firstname, lastname=:lastname, email=:email where userID = :uID")
    public void updateProfile(String firstname, String lastname, String email, int uID);

    @Query("SELECT * FROM user WHERE email = :email")
    public User getUserByEmail(String email);

    @Query("UPDATE user SET password = :password WHERE userID = :uID")
    public void changePassword(String password, int uID);
}
