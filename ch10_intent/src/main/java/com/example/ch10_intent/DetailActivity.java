package com.example.ch10_intent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ch10_intent.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //자신을 실행시킨 intent 획득
        Intent intent = getIntent();
        String data1 = intent.getStringExtra("data1");
        int data2 = intent.getIntExtra("data2",0);

        binding.detailResultView.setText("data1 : "+data1+" data2 : "+data2);

        binding.detailButton.setOnClickListener(view -> {
            //결과데이터 포함해서 되돌리기
            intent.putExtra("result", "world");

            //상태저장
            setResult(RESULT_OK, intent);

            //detail activity 종료
            finish();
        });

    }
}