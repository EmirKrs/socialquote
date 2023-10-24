package com.emirhankaraarslan.socialquote.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.emirhankaraarslan.socialquote.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class HomeActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_35));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_add_35));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_profile_35));
    }
}