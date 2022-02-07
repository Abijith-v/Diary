package com.example.diary.frags;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.diary.R;
import com.example.diary.database.MainData;
import com.example.diary.database.RoomDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class calendarFrag extends Fragment {

    MaterialCalendarView materialCalendarView;
    List<MainData> dataList;
    RoomDB db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = view.findViewById(R.id.calendarVIew);

        db = RoomDB.getInstance(getActivity());
        dataList = db.mainDAO().getAll();

        HashSet<CalendarDay> datesToBeHighlighted = new HashSet<>();
        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        for(MainData md : dataList) {

            String currDate = md.getDate();
            Date date = null;
            try {
                date = format.parse(currDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            datesToBeHighlighted.add(CalendarDay.from(date));
        }

        materialCalendarView.addDecorator(new CalendarDecorator(getActivity(), ContextCompat.getColor(getActivity(), R.color.green), datesToBeHighlighted));

        Log.d("green", String.valueOf(ContextCompat.getColor(getActivity(), R.color.green)));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull  CalendarDay date, boolean selected) {

                CalendarDay calendarDay = materialCalendarView.getSelectedDate();
                Toast.makeText(getActivity(), simpleDateFormat.format(calendarDay.getDate()), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


}