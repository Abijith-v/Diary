package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.diary.frags.calendarFrag;
import com.example.diary.frags.homeFrag;
import com.example.diary.frags.settingsFrag;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import io.ak1.BubbleTabBar;
import io.ak1.OnBubbleClickListener;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar navbar;
    FloatingActionButton addFab;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = findViewById(R.id.bottom_navbar);
        addFab = findViewById(R.id.addFabInHome);

        navbar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_frag_container, new homeFrag()).commit();

        selectBottomMenuFrag();


        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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

                        startActivity(new Intent(MainActivity.this, makeJournal.class));
                        alertDialog.dismiss();
                    }
                });


                alertDialog.show();
            }
        });

    }

    private void selectBottomMenuFrag() {

        navbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                Fragment frag = null;
                switch(i) {
                    case R.id.bottom_nav_home:

                        frag = new homeFrag();
                        break;
                    case R.id.bottom_nav_calander:
                        frag = new calendarFrag();
                        break;
                    case R.id.bottom_nav_settings:
                        frag = new settingsFrag();
                        break;
                }
                //set frag to the relative layout with id frag_container
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.navbar_frag_container, frag).commit();
            }
        });
    }
}