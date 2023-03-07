package com.example.ch3_view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ch3_view.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /*Button btn;
    EditText editView;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*//화면출력 -> layout xml 등록, view 객체 생성 완료.
        setContentView(R.layout.activity_main);

        //필요 view 객체 획득
        btn = findViewById(R.id.btn);
        editView = findViewById(R.id.edit);

        //view에 이벤트 등록
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editView.getText().toString();
                Log.d("kima", "data : " + data);
            }
        });*/

        //viewBinding 으로 변경 -------------------------------

        //항상 들어가야 하는 두줄
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //변수명은 res의 id
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = binding.edit.getText().toString();
                Log.d("kima", "data: "+data);
            }
        });


    }
}