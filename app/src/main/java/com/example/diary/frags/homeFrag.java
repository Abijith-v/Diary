package com.example.diary.frags;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    LinearLayout lottieContainer;
    RoomDB DB;
    MainAdapter mainAdapter;
    EditText searchBar;
    ImageView searchIcon;
    RelativeLayout parentLayout;

    @Override
    public void onResume() {
        super.onResume();

        if (makeJournal.updated || DB.mainDAO().getRowCount() != dataList.size()) {
            dataList = DB.mainDAO().getAll();

            sortDates();

            mainAdapter = new MainAdapter(getActivity(), dataList);
            recyclerView.setAdapter(mainAdapter);
        }

        lottieContainer.setVisibility(dataList.isEmpty() ? View.VISIBLE : View.GONE);

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
        searchBar = view.findViewById(R.id.searchBarHomeFrag);
        searchIcon = view.findViewById(R.id.searchIcon);
        parentLayout = view.findViewById(R.id.HomeParentLaout);
        lottieContainer = view.findViewById(R.id.emptyLottieHomeFrag);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        DB = RoomDB.getInstance(getActivity());
        dataList = DB.mainDAO().getAll(); // select * from table


        lottieContainer.setVisibility(dataList.isEmpty() ? View.VISIBLE : View.GONE);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setClipToPadding(false);

        sortDates();

        mainAdapter = new MainAdapter(getActivity(), dataList);
        recyclerView.setAdapter(mainAdapter);


        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searchBar.getVisibility() == View.VISIBLE) {
                    searchBar.setVisibility(View.GONE);
                } else {
                    searchBar.setVisibility(View.VISIBLE);
                }
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filter(searchBar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private void filter(String input) {

        if (!input.isEmpty()) {

            List<MainData> newList = new ArrayList<MainData>();
            for (MainData md : dataList) {

                if (md.getTitle().toUpperCase().trim().contains(input.toUpperCase().trim()) ||
                        md.getDate().toUpperCase().trim().contains(input.toUpperCase().trim())) {
                    newList.add(md);
                }
            }

            MainAdapter newAdapter = new MainAdapter(getActivity(), newList);
            recyclerView.setAdapter(newAdapter);
        } else
            recyclerView.setAdapter(mainAdapter);

        mainAdapter.notifyDataSetChanged();
    }
}