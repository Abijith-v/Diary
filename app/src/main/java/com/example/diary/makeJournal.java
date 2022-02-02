package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorPickerView;

import static com.jaredrummler.android.colorpicker.ColorPickerDialog.TYPE_CUSTOM;

public class makeJournal extends AppCompatActivity implements ColorPickerDialogListener {

    MaterialTextView dateSelector;
    ImageView colorSelector;

    public final static int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_journal);

        dateSelector = findViewById(R.id.todaysDateInTopBar);
        colorSelector = findViewById(R.id.selectColor);

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        colorSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setAllowPresets(true)
                        .setDialogId(DIALOG_ID)
                        .setColor(Color.BLUE)
                        .setShowAlphaSlider(true)
                        .show(makeJournal.this);
            }
        });
    }


    @Override public void onColorSelected(int dialogId, int color) {

        if(dialogId == DIALOG_ID)
            Toast.makeText(this, Integer.toHexString(color), Toast.LENGTH_SHORT).show();
    }
    @Override public void onDialogDismissed(int dialogId) {

//        Toast.makeText(this, "dismissed BC", Toast.LENGTH_SHORT).show();
    }
}