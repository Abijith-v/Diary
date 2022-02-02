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
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.jaredrummler.android.colorpicker.ColorPickerDialog.TYPE_CUSTOM;

public class makeJournal extends AppCompatActivity implements ColorPickerDialogListener {

    MaterialTextView dateSelector;
    ImageView colorSelector, close;
    Spinner titleSizeSelector, contentSizeSelector;
    EditText title, content;
    CircularProgressIndicator pb;

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
        pb = findViewById(R.id.journalPB);

        Integer textSizes[] = new Integer[30];
        for(int i = 0, size = 2; i < 30; i++, size += 2) {
            textSizes[i] = size;
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, textSizes);
        titleSizeSelector.setAdapter(adapter);
        contentSizeSelector.setAdapter(adapter);

        titleSizeSelector.setSelection(12);
        contentSizeSelector.setSelection(7);



        titleSizeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                title.setTextSize(textSizes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        contentSizeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                content.setTextSize(textSizes[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb.setVisibility(View.VISIBLE);

                final AlertDialog.Builder alert = new AlertDialog.Builder(makeJournal.this);
                View mview = getLayoutInflater().inflate(R.layout.add_diary_dialog,null);

                RelativeLayout rootLayout = mview.findViewById(R.id.dialogLayout);
                MaterialButton okButton = mview.findViewById(R.id.okButtonAddNewDialog);
                MaterialButton cancelButton = mview.findViewById(R.id.cancelButtonAddNewDialog);
                CalendarView calendar = mview.findViewById(R.id.calViewForDialog);


                Transition transition = new Slide(Gravity.TOP);
                transition.setDuration(700);
                transition.addTarget(calendar);

                alert.setView(mview);
                final AlertDialog alertDialog = alert.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(true);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        pb.setVisibility(View.GONE);
                        alertDialog.dismiss();
                    }
                });

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Calendar date = calendar.getFirstSelectedDate();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String curr = dateFormat.format(date.getTime());

                        Toast.makeText(makeJournal.this, curr, Toast.LENGTH_SHORT).show();

                        pb.setVisibility(View.GONE);
                        alertDialog.dismiss();
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