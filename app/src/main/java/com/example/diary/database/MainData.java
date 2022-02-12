package com.example.diary.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// table name
@Entity (tableName = "diary_table")

public class MainData implements Serializable {

    // auto generate ID
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "titleTextSize")
    private int titleTextSize;

    @ColumnInfo(name = "contentTextSize")
    private int contentTextSize;

    @ColumnInfo(name = "titleTextColor")
    private int titleTextColor;

    @ColumnInfo(name = "contentTextColor")
    private int contentTextColor;

    @ColumnInfo(name = "moodEmoji")
    private int moodEmoji;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getContentTextSize() {
        return contentTextSize;
    }

    public void setContentTextSize(int contentTextSize) {
        this.contentTextSize = contentTextSize;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getContentTextColor() {
        return contentTextColor;
    }

    public void setContentTextColor(int contentTextColor) {
        this.contentTextColor = contentTextColor;
    }

    public int getMoodEmoji() {
        return moodEmoji;
    }

    public void setMoodEmoji(int moodEmoji) {
        this.moodEmoji = moodEmoji;
    }
}
