package com.example.practice01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //화면출력 -> 화면은 xml으로 준비했음
        setContentView(R.layout.activity_main);
    }
}