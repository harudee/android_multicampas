package com.example.ch7_database;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ch7_database.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAddBinding binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addButton.setOnClickListener(view -> {
            String inputData = binding.addEditView.getText().toString();
            //db insert
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            //?에 들어가야할 데이터는 String 배열으로 처리
            db.execSQL("insert into tb_memo(memo) values (?)", new String[]{inputData});
            db.close();

            //이전화면으로 되될리기 (insert 종료된 이후에 결과값 포함해서 보내기)
            Intent intent = getIntent(); //나를 실행한 intent 획득
            intent.putExtra("result", inputData); //결과 포함해서 전달
            setResult(RESULT_OK, intent); //상태지정

            //현재 액티비티 종료 -> 시스템에 의해서 이전 화면으로 넘어가게 처리됨
            finish();

        });
    }
}