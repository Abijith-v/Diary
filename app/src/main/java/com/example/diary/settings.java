package com.example.diary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class settings extends AppCompatActivity {


    SwitchMaterial fingerprintSwitch;
    ImageView backBtn, userIcon;
    TextView saveBtn;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    public int profileIcons[];

    public settings() {

        profileIcons = new int[] {R.drawable.happy_emoji, R.drawable.happy_emoji, R.drawable.happy_emoji, R.drawable.happy_emoji, R.drawable.happy_emoji, R.drawable.happy_emoji};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = this.getSharedPreferences("diaryPref", Context.MODE_PRIVATE);
        fingerprintSwitch = findViewById(R.id.fingerPrintSwitch);
        backBtn = findViewById(R.id.backButtonSettings);
        saveBtn = findViewById(R.id.saveButtonSettings);
        userIcon = findViewById(R.id.userIconSettings);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        profilePicAdapter adapter = new profilePicAdapter(this, profileIcons);

        Boolean fingerPrintON = sharedPreferences.getBoolean("fingerPrintActivated", false);
        fingerprintSwitch.setChecked(fingerPrintON);

        fingerprintSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                sharedPreferences.edit().putBoolean("fingerPrintActivated", isChecked).apply();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(settings.this)
                        .setTitle("Close without saving?")
                        .setMessage("Are you sure you wanted to close without saving changes?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                finish();
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
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(settings.this)
                        .setTitle("Save changes?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                finish();
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
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(settings.this);
                View mview = getLayoutInflater().inflate(R.layout.profile_pic_picker_dialog,null);

                recyclerView = mview.findViewById(R.id.recyclerViewPicPickerDialog);
                MaterialButton cancel = mview.findViewById(R.id.cancelBtnPicPickerDialog);
                MaterialButton okBtn = mview.findViewById(R.id.okBtnPicPickerDialog);

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setLayoutManager(new GridLayoutManager(settings.this, 4));
                recyclerView.setClipToPadding(false);
                recyclerView.setAdapter(adapter);

                alert.setView(mview);
                final AlertDialog alertDialog = alert.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(true);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });


                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

    }
}