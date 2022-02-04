package com.example.diary.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class}, version = 1, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

    // create database instance and DB name
    private static RoomDB db;
    private static String DATABASE_NAME = "diary_DB";

    public synchronized static RoomDB getInstance(Context context) {

        if(db == null) {

            // If null, make a new DB

            db = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return db;
    }


    //Dao - DATA ACCESS OBJECTS to access data stored in DB

    public abstract MainDAO mainDAO();

}
