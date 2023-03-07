package com.example.ch7_database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ch7_database.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //db select data
    ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //db select
        SQLiteDatabase db = new DBHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_memo", null);

        //cursor의 moveXXX 함수는 row 선택 함수 boolean 값으로 반환
        //건수에 상관없이 있는건 다 read 하게 됨
        while (cursor.moveToNext()){
            datas.add(cursor.getString(1));
        }

        //결과 출력
        String results = "";
        for(String memo: datas){
            results += memo +"\n";
        }

        binding.mainTextView.setText(results);


        //다른 Activity 실행준비
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                //요청처리자
                new ActivityResultContracts.StartActivityForResult(),

                //call back -> 되돌아왔을때 처리
                result -> {
                    //add에서 넘어온 결과값
                    datas.add(result.getData().getStringExtra("result"));

                    //코드 복붙함
                    String data = "";
                    for(String memo: datas){
                        data += memo +"\n";
                    }

                    binding.mainTextView.setText(data);

                }
        );

        binding.mainButton.setOnClickListener(view -> {
            //화면 전환
            Intent intent = new Intent(this, AddActivity.class);
            launcher.launch(intent);

        });

    }
}