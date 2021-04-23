package com.example.notemanagement.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Priority")
public class Priority {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String createDate;

    public Priority(String name, String createDate) {
        this.name = name;
        this.createDate = createDate;
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
}
