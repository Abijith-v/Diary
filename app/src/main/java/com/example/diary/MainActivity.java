package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.diary.frags.calendarFrag;
import com.example.diary.frags.homeFrag;
import com.example.diary.frags.reportFrag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar navbar;
    FloatingActionButton addFab;
    RelativeLayout layout;
    int bgColor, whiteBG;

    @Override
    protected void onResume() {
        super.onResume();

//        selectBottomMenuFrag();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = findViewById(R.id.bottom_navbar);
        addFab = findViewById(R.id.addFabInHome);
        layout = findViewById(R.id.mainActivityLayout);

        bgColor = ContextCompat.getColor(this, R.color.bgcolor);
        whiteBG  = ContextCompat.getColor(this, R.color.darker_white);


        navbar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_frag_container, new homeFrag()).commit();

        selectBottomMenuFrag();


        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//                View mview = getLayoutInflater().inflate(R.layout.add_diary_dialog,null);
//
//                RelativeLayout rootLayout = mview.findViewById(R.id.dialogLayout);
//                LinearLayout expandableCalLayout = mview.findViewById(R.id.dropDownSectionDialog);
//                MaterialButton okButton = mview.findViewById(R.id.okButtonAddNewDialog);
//                MaterialButton moreButton = mview.findViewById(R.id.moreButtonAddNewDialog);
//                CalendarView calendar = mview.findViewById(R.id.calViewForDialog);
//
//
//                Transition transition = new Slide(Gravity.TOP);
//                transition.setDuration(700);
//                transition.addTarget(calendar);
//
//                alert.setView(mview);
//                final AlertDialog alertDialog = alert.create();
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                alertDialog.setCanceledOnTouchOutside(true);
//
//                final boolean[] expanded = {false};
//
//                moreButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        if(expanded[0] == false) {
//
//                            TransitionManager.beginDelayedTransition(rootLayout, transition);
//
//                            calendar.setVisibility(View.VISIBLE);
//                            expanded[0] = true;
//                        }
//                        else {
//                            calendar.setVisibility(View.GONE);
//                            expanded[0] = false;
//                        }
//                    }
//                });
//
//                okButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        startActivity(new Intent(MainActivity.this, makeJournal.class));
//                        alertDialog.dismiss();
//                    }
//                });
//
//
//                alertDialog.show();


                BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(MainActivity.this)
                        .setTitle("Add a journal?")
                        .setMessage("Do you want to write a new journal?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", R.drawable.tick_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                startActivity(new Intent(MainActivity.this, makeJournal.class));
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", R.drawable.cancel_icon, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                dialogInterface.dismiss();
                            }
                        })
                        .setAnimation(R.raw.walking)
                        .build();

                // Show Dialog
                mDialog.show();


            }
        });

    }

    private void selectBottomMenuFrag() {

        navbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                Fragment frag = null;
                switch (i) {
                    case R.id.bottom_nav_home:
                        addFab.setVisibility(View.VISIBLE);
                        frag = new homeFrag();
                        break;
                    case R.id.bottom_nav_calander:
                        addFab.setVisibility(View.INVISIBLE);
                        frag = new calendarFrag();
                        break;
                    case R.id.bottom_nav_settings:
                        addFab.setVisibility(View.INVISIBLE);
                        frag = new reportFrag();
                        break;
                }
                //set frag to the relative layout with id frag_container
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.navbar_frag_container, frag).commit();


            }
        });
    }

    public void refreshFrag(Fragment frag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_frag_container, frag).commit();
    }
}