package com.example.diary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorPickerView;

import java.util.Calendar;

import static com.jaredrummler.android.colorpicker.ColorPickerDialog.TYPE_CUSTOM;

public class makeJournal extends AppCompatActivity implements ColorPickerDialogListener {

    MaterialTextView dateSelector;
    ImageView colorSelector, close;
    Spinner titleSizeSelector, contentSizeSelector;
    EditText title, content;

    public final static int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_journal);

        dateSelector = findViewById(R.id.todaysDateInTopBar);
        colorSelector = findViewById(R.id.selectColor);
        titleSizeSelector = findViewById(R.id.titleSize);
        contentSizeSelector = findViewById(R.id.contentSize);
        title = findViewById(R.id.titleJournal);
        content = findViewById(R.id.contentJournal);
        close = findViewById(R.id.closeMakeJournal);

        Integer textSizes[] = new Integer[30];
        for(int i = 0, size = 2; i < 30; i++, size += 2) {
            textSizes[i] = size;
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, textSizes);
        titleSizeSelector.setAdapter(adapter);
        contentSizeSelector.setAdapter(adapter);

        titleSizeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                title.setTextSize(textSizes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        contentSizeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                content.setTextSize(textSizes[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(makeJournal.this);
                View mview = getLayoutInflater().inflate(R.layout.add_diary_dialog,null);

                RelativeLayout rootLayout = mview.findViewById(R.id.dialogLayout);
                LinearLayout expandableCalLayout = mview.findViewById(R.id.dropDownSectionDialog);
                MaterialButton okButton = mview.findViewById(R.id.okButtonAddNewDialog);
                MaterialButton moreButton = mview.findViewById(R.id.moreButtonAddNewDialog);
                CalendarView calendar = mview.findViewById(R.id.calViewForDialog);


                Transition transition = new Slide(Gravity.TOP);
                transition.setDuration(700);
                transition.addTarget(calendar);

                alert.setView(mview);
                final AlertDialog alertDialog = alert.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(true);

                final boolean[] expanded = {false};

                moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(expanded[0] == false) {

                            TransitionManager.beginDelayedTransition(rootLayout, transition);

                            calendar.setVisibility(View.VISIBLE);
                            expanded[0] = true;
                        }
                        else {
                            calendar.setVisibility(View.GONE);
                            expanded[0] = false;
                        }
                    }
                });

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String date = calendar.getFirstSelectedDate().toString();
                        Toast.makeText(makeJournal.this, date, Toast.LENGTH_SHORT).show();
                    }
                });


                alertDialog.show();
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

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
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