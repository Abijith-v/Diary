package com.example.diary.frags;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.applandeo.materialcalendarview.CalendarView;
import com.example.diary.MainActivity;
import com.example.diary.R;
import com.example.diary.database.MainData;
import com.example.diary.database.RoomDB;
import com.example.diary.makeJournal;
import com.example.diary.repeatedDatesDisplay;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class calendarFrag extends Fragment {

    MaterialCalendarView materialCalendarView;
    List<MainData> dataList;
    RoomDB db;
    TextView currStreakTV, highestStreakTV;
    LottieAnimationView fireAnimStreak;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = view.findViewById(R.id.calendarVIew);
        highestStreakTV = view.findViewById(R.id.highestStreakCalFrag);
        currStreakTV = view.findViewById(R.id.streakCountCalFrag);
        fireAnimStreak = view.findViewById(R.id.fireLottieNearStreak);

        Log.d("color : ", String.format("#%06X", (0xFFFFFF & highestStreakTV.getCurrentTextColor())));

        db = RoomDB.getInstance(getActivity());
        dataList = db.mainDAO().getAll();

        HashSet<CalendarDay> datesToBeHighlighted = new HashSet<>();
        HashSet<CalendarDay> repeatedDates = new HashSet<>();
        List<Date> sortedDates = new ArrayList<>();

        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        // If dates are present in set, mark visited
        for (MainData md : dataList) {

            String currDate = md.getDate();
            Date date = null;
            try {
                date = format.parse(currDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CalendarDay cal = CalendarDay.from(date);
            if (datesToBeHighlighted.contains(cal))
                repeatedDates.add(cal);
            else {
                datesToBeHighlighted.add(cal);
                sortedDates.add(date);
            }
        }

        Collections.sort(sortedDates, new Comparator<Date>() {
            @Override
            public int compare(Date a, Date b) {
                if(a.before(b))
                    return -1;
                else
                    return 1;
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
//
//        for(Date d : sortedDates)
//            Log.d("sorted date : ", simpleDateFormat.format(d));

        // find max streak and currstreak
        int sortedDatesSize = sortedDates.size();

        Log.d("dates size", String.valueOf(sortedDatesSize));

        int maxStreak = 0, currStreak = 1;
        for(int i = 0; i < sortedDatesSize - 1; i++) {

            Date tmrw = new Date(sortedDates.get(i).getTime() + 86400000);

//            Log.d("tmrw", simpleDateFormat.format(sortedDates.get(i + 1)) + " " + simpleDateFormat.format(tmrw) + " " + String.valueOf(simpleDateFormat.format(sortedDates.get(i + 1)) == simpleDateFormat.format(tmrw)));

            if(!sortedDates.get(i + 1).equals(tmrw)) {

                maxStreak = Math.max(maxStreak, currStreak);
                currStreak = 1;
            }
            else
                currStreak++;
        }

        Log.d("currStreak",  String.valueOf(currStreak));

        if(!sortedDates.isEmpty())
            maxStreak = Math.max(maxStreak, currStreak);

        Date today = Calendar.getInstance().getTime();
        String newToday = simpleDateFormat.format(today);
        Date yest = new Date(today.getTime() - 86400000);
        String newYest = simpleDateFormat.format(yest);
//
//        Log.d("today and yest",  today + " " + yest + " " + yest.equals(sortedDates.get(sortedDatesSize - 1)));

        if(!sortedDates.isEmpty() && !newYest.equals(simpleDateFormat.format(sortedDates.get(sortedDatesSize - 1))))
            currStreak = 0;

        if(currStreak >= 7) fireAnimStreak.setVisibility(View.VISIBLE);

        highestStreakTV.setText(String.valueOf(maxStreak));
        currStreakTV.setText(String.valueOf(currStreak));

        materialCalendarView.addDecorator(new CalendarDecorator(getActivity(), ContextCompat.getColor(getActivity(), R.color.green), datesToBeHighlighted));

        Log.d("green", String.valueOf(ContextCompat.getColor(getActivity(), R.color.green)));

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

//                Toast.makeText(getActivity(), simpleDateFormat.format(calendarDay.getDate()), Toast.LENGTH_SHORT).show();

                if (getActivity() == null) return;

                CalendarDay calendarDay = materialCalendarView.getSelectedDate();
                String currDateInStr = simpleDateFormat.format(calendarDay.getDate());

                if(!datesToBeHighlighted.contains(calendarDay)) {

                    Toast.makeText(getActivity(), "Press and hold to create a new journal", Toast.LENGTH_SHORT).show();

                    materialCalendarView.setOnDateLongClickListener(new OnDateLongClickListener() {
                        @Override
                        public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {


                            BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                                    .setTitle("Add a journal?")
                                    .setMessage("Do you want to write a new journal?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                            Intent makeJIntent = new Intent(getActivity(), makeJournal.class);
                                            makeJIntent.putExtra("dateFromCalendarFrag", currDateInStr);
                                            startActivity(makeJIntent);

                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancel", R.drawable.close_icon, new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setAnimation(R.raw.night_sky)
                                    .build();

                            // Show Dialog
                            mDialog.show();

                        }
                    });

                    return;
                }

                Boolean repeated = repeatedDates.contains(calendarDay);

                BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                        .setTitle(repeated ? "View journals?" : "View journal?")
                        .setMessage(repeated ? "There are multiple journals for this date" : "View/edit the journal you wrote")
                        .setCancelable(false)
                        .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                if(repeated) {

                                    Intent intent = new Intent(getActivity(), repeatedDatesDisplay.class);
                                    intent.putExtra("repeatedDate", currDateInStr);
                                    startActivity(intent);
                                }
                                else {

                                    List<MainData> data = db.mainDAO().getDataFromDate(currDateInStr);

                                    Intent intent = new Intent(getActivity(), makeJournal.class);
                                    intent.putExtra("mainData_Object", data.get(0));

                                    startActivity(intent);
                                }

                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", R.drawable.cancel_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();

//                    Toast.makeText(getActivity(), "repeated", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


}