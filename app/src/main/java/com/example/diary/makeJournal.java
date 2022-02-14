package com.example.diary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
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
import com.example.diary.database.MainData;
import com.example.diary.database.RoomDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

import static com.jaredrummler.android.colorpicker.ColorPickerDialog.TYPE_CUSTOM;

public class makeJournal extends AppCompatActivity implements ColorPickerDialogListener {

    public static boolean updated = false;

    MaterialTextView dateSelector;
    ImageView colorSelector, close, save, deleteBtn;
    Spinner titleSizeSelector, contentSizeSelector;
    EditText title, content;
    CircularProgressIndicator pb;

    RoomDB db;

    boolean contentSelected = true;

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
        save = findViewById(R.id.saveButton);
        pb = findViewById(R.id.journalPB);
        deleteBtn = findViewById(R.id.deleteBtnMakeJournal);


        db = RoomDB.getInstance(this);

        Intent intent = getIntent();

        String dateFromCalendarFrag = (String) intent.getSerializableExtra("dateFromCalendarFrag");
        MainData currData = (MainData) intent.getSerializableExtra("mainData_Object");

        Integer textSizes[] = new Integer[30];
        for (int i = 0, size = 2; i < 30; i++, size += 2) {
            textSizes[i] = size;
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, textSizes);
        titleSizeSelector.setAdapter(adapter);
        contentSizeSelector.setAdapter(adapter);

        titleSizeSelector.setSelection(12);
        contentSizeSelector.setSelection(7);


        if (currData != null) {

            dateSelector.setCompoundDrawables(null, null, null, null);
            deleteBtn.setVisibility(View.VISIBLE);
            dateSelector.setText(currData.getDate());

            content.setText(currData.getContent());
            title.setText(currData.getTitle());

            content.setTextColor(currData.getContentTextColor());
            title.setTextColor(currData.getTitleTextColor());

            titleSizeSelector.setSelection(currData.getTitleTextSize());
            contentSizeSelector.setSelection(currData.getContentTextSize());
        } else {

            deleteBtn.setVisibility(View.GONE);
            Date d = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

            dateSelector.setText(dateFromCalendarFrag == null ? simpleDateFormat.format(d) : dateFromCalendarFrag);
        }

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

                if (currData != null) return;

                pb.setVisibility(View.VISIBLE);

                final AlertDialog.Builder alert = new AlertDialog.Builder(makeJournal.this);
                View mview = getLayoutInflater().inflate(R.layout.add_diary_dialog, null);

                RelativeLayout rootLayout = mview.findViewById(R.id.dialogLayout);
                MaterialButton okButton = mview.findViewById(R.id.okButtonAddNewDialog);
                MaterialButton cancelButton = mview.findViewById(R.id.cancelButtonAddNewDialog);
                MaterialCalendarView calendarView = mview.findViewById(R.id.calViewForDialog);


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

                        CalendarDay calendarDay = calendarView.getSelectedDate();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

                        String curr = simpleDateFormat.format(calendarDay.getDate());

                        dateSelector.setText(curr);

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

                contentSelected = !title.isFocused();

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // get all data

                String date = dateSelector.getText().toString();

                String titleStr = title.getText().toString().trim();
                String contentStr = content.getText().toString();

                int titleColor = title.getCurrentTextColor();
                int contenColor = content.getCurrentTextColor();

                int titleSizePos = titleSizeSelector.getSelectedItemPosition();
                int contentSizePos = contentSizeSelector.getSelectedItemPosition();

                if (!valid(titleStr, contentStr)) {

                    Toast.makeText(makeJournal.this, "Please type something!", Toast.LENGTH_SHORT).show();
                    return;
                }

                showDialog(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date);

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(makeJournal.this)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you wanna delete this journal?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {


                                db.mainDAO().delete(currData);
                                dialogInterface.dismiss();

                                finish();
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
            }
        });

    }

    private void showBottomDialog(MainData currData, String contentStr, int contenColor, int contentSizePos, String titleStr, int titleSizePos, int titleColor, String date, int emoji) {

        BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(makeJournal.this)
                .setTitle(currData == null ? "Save?" : "Make changes?")
                .setMessage(currData == null ? "Do you want to save this journal?" : "Save the new changes made?")
                .setCancelable(false)
                .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        // ask for mood

                        saveIntoRoom(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date, emoji);
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
    }

    private void showDialog(MainData currData, String contentStr, int contenColor, int contentSizePos, String titleStr, int titleSizePos, int titleColor, String date) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(makeJournal.this);
        View mview = getLayoutInflater().inflate(R.layout.mood_emoji_dialog, null);

        ImageView coolEmoji = mview.findViewById(R.id.coolEmojiIcon);
        ImageView happyEmoji = mview.findViewById(R.id.happyEmojiIcon);
        ImageView mehEmoji = mview.findViewById(R.id.neutralEmojiIcon);
        ImageView sadEmoji = mview.findViewById(R.id.sadEmojiIcon);
        ImageView angryEmoji = mview.findViewById(R.id.angryEmojiIcon);


        alert.setView(mview);
        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);

        coolEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                showBottomDialog(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date, 0);
            }
        });

        happyEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                showBottomDialog(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date, 1);
            }
        });

        mehEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                showBottomDialog(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date, 2);
            }
        });

        sadEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                showBottomDialog(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date, 3);
            }
        });

        angryEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                showBottomDialog(currData, contentStr, contenColor, contentSizePos, titleStr, titleSizePos, titleColor, date, 4);

            }
        });


        alertDialog.show();
    }

    private void saveIntoRoom(MainData currData, String contentStr, int contenColor, int contentSizePos, String titleStr, int titleSizePos, int titleColor, String date, int emoji) {

        // If we are editing already existing diary, just update
        if (currData != null) {

            int ID = currData.getID();

            db.mainDAO().updateContent(ID, contentStr);
            db.mainDAO().updateContentTextColor(ID, contenColor);
            db.mainDAO().updateContentTextSize(ID, contentSizePos);

            db.mainDAO().updateTitle(ID, titleStr);
            db.mainDAO().updateTitleSize(ID, titleSizePos);
            db.mainDAO().updateTitleTextColor(ID, titleColor);
            db.mainDAO().updateEmoji(ID, emoji);


            Toast.makeText(makeJournal.this, "updated!", Toast.LENGTH_SHORT).show();

            updated = true;

            finish();
            return;
        }

        updated = false;

        MainData mainData = new MainData();

        mainData.setDate(date);

        mainData.setContent(contentStr);
        mainData.setContentTextColor(contenColor);
        mainData.setContentTextSize(contentSizePos);

        mainData.setTitle(titleStr);
        mainData.setTitleTextColor(titleColor);
        mainData.setTitleTextSize(titleSizePos);

        mainData.setMoodEmoji(emoji);

        db.mainDAO().insert(mainData);

        Toast.makeText(makeJournal.this, "saved!", Toast.LENGTH_SHORT).show();

        finish();

    }

    private boolean valid(String titleStr, String contentStr) {

        return !titleStr.isEmpty() && !contentStr.isEmpty();
    }


    @Override
    public void onColorSelected(int dialogId, int color) {

        if (dialogId == DIALOG_ID) {

            Toast.makeText(this, Integer.toHexString(color), Toast.LENGTH_SHORT).show();
            (contentSelected ? content : title).setTextColor(color);
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

//        Toast.makeText(this, "dismissed BC", Toast.LENGTH_SHORT).show();
    }
}