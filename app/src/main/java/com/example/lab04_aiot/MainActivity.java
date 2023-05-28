package com.example.lab04_aiot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void tutor(View view){
        Intent i = new Intent(this,FlujoTutor.class);
        startActivity(i);

    }
    public void trabajador(View view){
        Intent i = new Intent(this,FlujoTrabajador.class);
        startActivity(i);

    }

}