package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class settings extends AppCompatActivity {


    SwitchMaterial fingerprintSwitch;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = this.getSharedPreferences("diaryPref", Context.MODE_PRIVATE);
        fingerprintSwitch = findViewById(R.id.fingerPrintSwitch);

        Boolean fingerPrintON = sharedPreferences.getBoolean("fingerPrintActivated", false);
        fingerprintSwitch.setChecked(fingerPrintON);

        fingerprintSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                sharedPreferences.edit().putBoolean("fingerPrintActivated", isChecked).apply();
            }
        });


    }
}