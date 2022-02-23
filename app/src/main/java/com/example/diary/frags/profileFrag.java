package com.example.diary.frags;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.diary.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class profileFrag extends Fragment {

    SwitchMaterial fingerprintSwitch;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences("diaryPref", Context.MODE_PRIVATE);
        fingerprintSwitch = view.findViewById(R.id.fingerPrintSwitch);

        Boolean fingerPrintON = sharedPreferences.getBoolean("fingerPrintActivated", false);
        fingerprintSwitch.setChecked(fingerPrintON);

        fingerprintSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                sharedPreferences.edit().putBoolean("fingerPrintActivated", isChecked).apply();
            }
        });

        return view;
    }
}