package com.example.diary.frags;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diary.R;
import com.example.diary.database.RoomDB;
import com.example.diary.settings;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class reportFrag extends Fragment {

    PieChartView pieChart;
    int colors[], pics[];
    RoomDB roomDB;
    TextView happyCount, coolCount, mehCount, sadCount, angryCount, journalCount;

    SharedPreferences sharedPreferences;
    ImageView avgEmojiImangeView, settingsIcon, profilePic;

    int picIndex = 0;

    @Override
    public void onResume() {
        super.onResume();


        int i = getActivity().getSharedPreferences("diaryPref", Context.MODE_PRIVATE).getInt("profilePicIndex", 0);
        profilePic.setImageResource(pics[i]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_report, container, false);

        roomDB = RoomDB.getInstance(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("diaryPref", Context.MODE_PRIVATE);

        List<Integer> emojis = roomDB.mainDAO().getEmojiColumn();

        for(int i : emojis) Log.d("emoji", String.valueOf(i));
        int cool = 0, happy = 0, meh = 0, sad = 0, angry = 0, total = emojis.size();
        for(int i : emojis) {

            if(i == 0) cool++;
            else if(i == 1) happy++;
            else if(i == 2) meh++;
            else if(i == 3) sad++;
            else angry++;
        }


        happyCount = view.findViewById(R.id.happyEmojiCount);
        coolCount = view.findViewById(R.id.coolEmojiCount);
        mehCount = view.findViewById(R.id.mehEmojiCount);
        sadCount = view.findViewById(R.id.sadEmojiCount);
        angryCount = view.findViewById(R.id.angryEmojiCount);
        journalCount = view.findViewById(R.id.journalsCountReportFrag);

        avgEmojiImangeView = view.findViewById(R.id.avgMoodContainer);
        pieChart = view.findViewById(R.id.pieChartReportFrag);
        settingsIcon = view.findViewById(R.id.settingsIconReportFrag);

        profilePic = view.findViewById(R.id.userIconProfileFrag);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("diaryPref", Context.MODE_PRIVATE);
        picIndex = sharedPreferences.getInt("profilePicIndex", 0);

        pics = new int[] {R.drawable.happy_emoji, R.drawable.cool_emoji, R.drawable.happy_emoji, R.drawable.sad_emoji, R.drawable.meh_emoji, R.drawable.angry_emoji};
        profilePic.setImageResource(pics[picIndex]);

        //journal count
        journalCount.setText(String.valueOf(total));

        // avg emoji
        int[] emojiCount = new int[] {cool, happy, meh, sad, angry};

        int Max = -1, maxIndex = -1;
        for(int i = 0; i < 5; i++)
            if(emojiCount[i] > Max) {
                Max = emojiCount[i];
                maxIndex = i;
            }

        if(maxIndex == -1) avgEmojiImangeView.setImageResource(R.drawable.none_icon);
        else {
            if(maxIndex == 0) avgEmojiImangeView.setImageResource(R.drawable.cool_emoji);
            else if(maxIndex == 1) avgEmojiImangeView.setImageResource(R.drawable.happy_emoji);
            else if(maxIndex == 2) avgEmojiImangeView.setImageResource(R.drawable.meh_emoji);
            else if(maxIndex == 3) avgEmojiImangeView.setImageResource(R.drawable.sad_emoji);
            else avgEmojiImangeView.setImageResource(R.drawable.angry_emoji);
        }

        colors = new int[] {Color.parseColor("#8BC34A"), Color.parseColor("#DDCA24"), Color.parseColor("#FF9800"), Color.parseColor("#FF5722"), Color.parseColor("#D53300")};

        float coolPercent = (float)((1.0 * cool / total) * 100);
        float happyPercent = (float)((1.0 * happy / total) * 100);
        float mehPercent = (float)((1.0 * meh / total) * 100);
        float sadPercent = (float)((1.0 * sad / total) * 100);
        float angryPercent = (float)((1.0 * angry / total) * 100);

        List pieDate = new ArrayList<>();
        pieDate.add(new SliceValue(cool, colors[0]).setLabel("Cool - " + cool));
        pieDate.add(new SliceValue(happy, colors[1]).setLabel("Happy - " + happy));
        pieDate.add(new SliceValue(meh, colors[2]).setLabel("Meh - " + meh));
        pieDate.add(new SliceValue(sad, colors[3]).setLabel("Sad - " + sad));
        pieDate.add(new SliceValue(angry, colors[4]).setLabel("Angry - " + angry));

        coolCount.setText(String.valueOf(coolPercent) + "%");
        coolCount.setTextColor(colors[0]);

        happyCount.setText(String.valueOf(happyPercent) + "%");
        happyCount.setTextColor(colors[1]);

        mehCount.setText(String.valueOf(mehPercent) + "%");
        mehCount.setTextColor(colors[2]);

        sadCount.setText(String.valueOf(sadPercent) + "%");
        sadCount.setTextColor(colors[3]);

        angryCount.setText(String.valueOf(angryPercent) + "%");
        angryCount.setTextColor(colors[4]);

        PieChartData pieChartData = new PieChartData(pieDate);
//        pieChartData.setHasLabels(true).setValueLabelTextSize(12);
        pieChartData.setHasLabelsOnlyForSelected(true).setValueLabelTextSize(12);
        pieChartData.setHasCenterCircle(true);

        pieChart.setPieChartData(pieChartData);

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), settings.class));
            }
        });


        return view;
    }
}