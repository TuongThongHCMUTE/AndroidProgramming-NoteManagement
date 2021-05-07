package com.example.notemanagement.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Status")
public class Status {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String createDate;
    private int userId;

    public Status(String name, String createDate, int userId) {
        this.name = name;
        this.createDate = createDate;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
