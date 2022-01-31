package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.diary.frags.calendarFrag;
import com.example.diary.frags.homeFrag;
import com.example.diary.frags.settingsFrag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

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