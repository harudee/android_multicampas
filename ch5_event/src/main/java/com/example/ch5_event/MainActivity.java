package com.example.ch5_event;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ch5_event.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    long initTime;//back button 누른 시간 확인
    long pauseTime;//chronometer 시간- 멈췄다가 다시 시작하기 위해서

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStart.setOnClickListener(view -> {
            binding.chronometer.setBase(SystemClock.elapsedRealtime()+pauseTime);
            binding.chronometer.start();

            //button 조정
            binding.btnStart.setEnabled(false);
            binding.btnStop.setEnabled(true);
            binding.btnReset.setEnabled(true);
        });

        binding.btnStop.setOnClickListener(view -> {
            pauseTime = binding.chronometer.getBase() - SystemClock.elapsedRealtime();
            binding.chronometer.stop();

            //button 조정
            binding.btnStart.setEnabled(true);
            binding.btnStop.setEnabled(false);
            binding.btnReset.setEnabled(true);
        });

        binding.btnReset.setOnClickListener(view -> {
            pauseTime = 0;
            binding.chronometer.setBase(SystemClock.elapsedRealtime());
            binding.chronometer.stop();

            //button 조정
            binding.btnStart.setEnabled(true);
            binding.btnStop.setEnabled(false);
            binding.btnReset.setEnabled(false);
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime >3000){

                Toast.makeText(this, "종료하려면 한번 더 누르세요", Toast.LENGTH_SHORT).show();
                initTime = System.currentTimeMillis();
                return true;

            }
        }

        //상위클래스 함수 남겨줘야함
        return super.onKeyDown(keyCode, event);


    }
}