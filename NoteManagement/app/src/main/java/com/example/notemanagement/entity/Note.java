package com.example.notemanagement.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String category;
    private String priority;
    private String status;
    private String planDate;
    private String createDate;
    private int userId;

    public Note(String name, String category, String priority, String status, String planDate, String createDate, int userId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.planDate = planDate;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }
}
