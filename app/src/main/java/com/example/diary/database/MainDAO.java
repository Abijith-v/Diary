package com.example.diary.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDAO {

    // insert queries
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    // delete query
    @Delete
    void delete(MainData mainData);

    // update content
    @Query("update diary_table set content = :newContent where ID = :id")
    void updateContent(int id, String newContent);

    // update title
    @Query("update diary_table set title = :newTitle where ID = :id")
    void updateTitle(int id, String newTitle);

    // update contentTextSize
    @Query("update diary_table set contentTextSize = :newContentTextSize where ID = :id")
    void updateContentTextSize(int id, int newContentTextSize);

    // update titleTextSize
    @Query("update diary_table set titleTextSize = :newTitleTextSize where ID = :id")
    void updateTitleSize(int id, int newTitleTextSize);

    // update titleTextColor
    @Query("update diary_table set titleTextColor = :newTitleTextColor where ID = :id")
    void updateTitleTextColor(int id, int newTitleTextColor);

    // update contentTextColor
    @Query("update diary_table set contentTextColor = :newContentTextColor where ID = :id")
    void updateContentTextColor(int id, int newContentTextColor);

    // update emoji
    @Query("update diary_table set moodEmoji = :newEmoji where ID = :id")
    void updateEmoji(int id, int newEmoji);


    //get all data query
    @Query("select * from diary_table")
    List<MainData> getAll();


    //get count of rows
    @Query("select count(*) from diary_table")
    int getRowCount();

    //get rows of particular date
    @Query("select * from diary_table where date = :reqDate")
    List<MainData> getDataFromDate(String reqDate);

}
