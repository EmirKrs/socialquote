package com.emirhankaraarslan.socialquote.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.emirhankaraarslan.socialquote.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_SocialQuote);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}