package com.example.lab04_aiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FlujoTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}