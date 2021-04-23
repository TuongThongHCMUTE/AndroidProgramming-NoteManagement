package com.example.notemanagement.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notemanagement.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE email = (:email) AND password = (:password)")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM users WHERE email = (:emailCheck)")
    UserEntity checkExistence(String emailCheck);

    @Query("Update users set firstname = :firstname, lastname=:lastname, email=:email where id = :uID")
    public void updateProfile(String firstname, String lastname, String email, int uID);

}
