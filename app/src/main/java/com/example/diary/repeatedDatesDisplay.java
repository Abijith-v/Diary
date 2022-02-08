package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarDay;
import com.example.diary.database.MainAdapter;
import com.example.diary.database.MainData;
import com.example.diary.database.RoomDB;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class repeatedDatesDisplay extends AppCompatActivity {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainAdapter mainAdapter;
    RoomDB db;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_dates_display);

        recyclerView = findViewById(R.id.repeatedDatesRV);
        heading = findViewById(R.id.headingOfRepeatedDate);

        Intent intent = getIntent();
        String date = (String) intent.getSerializableExtra("repeatedDate");

        heading.setText("Journals of " + date);

        db = RoomDB.getInstance(this);
        List<MainData> rows = db.mainDAO().getDataFromDate(date);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Collections.reverse(rows);

        mainAdapter = new MainAdapter(this, rows);
        recyclerView.setAdapter(mainAdapter);
    }
}