package com.example.diary.frags;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diary.R;
import com.example.diary.database.MainAdapter;
import com.example.diary.database.MainData;
import com.example.diary.database.RoomDB;
import com.example.diary.makeJournal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class homeFrag extends Fragment {


    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<MainData>();
    LinearLayoutManager linearLayoutManager;
    RoomDB DB;
    MainAdapter mainAdapter;


    @Override
    public void onResume() {
        super.onResume();

        Log.d("Home frag : ", "resumed");

        if(makeJournal.updated)
            Log.d("changed", " confirmed ");

        if(makeJournal.updated || DB.mainDAO().getRowCount() != dataList.size()) {
            dataList = DB.mainDAO().getAll();

            sortDates();

            mainAdapter = new MainAdapter(getActivity(), dataList);
            recyclerView.setAdapter(mainAdapter);
        }

    }

    private void sortDates() {

        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Collections.sort(dataList, new Comparator<MainData>() {
            @Override
            public int compare(MainData a, MainData b) {

                String dateAstring = a.getDate();
                String dateBstring = b.getDate();

                Date dateA = null;
                try {
                    dateA = format.parse(dateAstring);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date dateB = null;
                try {
                    dateB = format.parse(dateBstring);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return dateA.before(dateB) ? 1 : -1;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_homepage);

        DB = RoomDB.getInstance(getActivity());
        dataList = DB.mainDAO().getAll(); // select * from table

//        for(MainData md : dataList) {
//            Log.d("data : ", md.getDate());
//        }

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

//
//        Collections.reverse(dataList);

        sortDates();

        mainAdapter = new MainAdapter(getActivity(), dataList);
        recyclerView.setAdapter(mainAdapter);


        return view;
    }
}