package com.example.ch10_intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ch10_intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //detail 실행시키는 intent
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("data1", "hello");
        intent.putExtra("data2", 10);

        //결과를 되돌려받으면서 intent 실행
        binding.goDetailButton1.setOnClickListener(view -> {
            startActivityForResult(intent, 10);

        });

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                //요청처리자
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        String resultData = result.getData().getStringExtra("result");
                        binding.mainResultView.setText("result data: "+resultData);


                    }
                }
        );

        binding.goDetailButton2.setOnClickListener(view -> {
            launcher.launch(intent);

        });

        binding.webButton.setOnClickListener(view -> {
            //인터넷 연결 (외부 앱_브라우저 실행 )
            Intent webIntent = new Intent();
            webIntent.setAction(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse("http://www.google.com"));
            startActivity(webIntent);

        });

        binding.mapButton.setOnClickListener(view -> {
            //외부 앱위치데이터와 연결
            // 매개변수로도 전달이 가능
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,127.4194"));

            //폰내부에 여러 개의 지도 앱이 있다면 패키지명을 명시해서 특정 앱을 지정할 수 있다.
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

        });

    }


    /**
     * startActivityForResult에 의한 요청이 되돌아오면 자동으로 호출됨
     * requestCode : intent를 발생시킨 곳에서 자신의 intent를 식별하기 위해서 지정한 개발자 숫자값 (개발자가 지정)
     * resultCode : intent에 의해서 실행된 곳에서 결과를 되돌리기전에 지정한 상태값
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && resultCode == RESULT_OK){
            //ok가 아니면 함수 처리할 필요가 없징
            String result = data.getStringExtra("result");
            binding.mainResultView.setText("result data: "+result);

        }

    }
}