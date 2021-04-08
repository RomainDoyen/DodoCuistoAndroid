package com.example.dodocuisto.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dodocuisto.R;

public class MainActivity extends AppCompatActivity {

    View addActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addActivity = findViewById(R.id.addActivity);
    }

    public void addActi(View v) {
        Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
        startActivity(addActivity);
    }
}