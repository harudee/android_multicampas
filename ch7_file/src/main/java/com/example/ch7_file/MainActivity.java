package com.example.ch7_file;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ch7_file.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //내장파일 이용
        binding.button1.setOnClickListener(view -> {
            try{
                //쓰기
                //내장메모리 위치지정 -> getFilesDir
                File file = new File(getFilesDir(), "test.txt");
                FileWriter writer = new FileWriter(file, true);
                writer.write("hello world - internal");
                writer.flush();

                //읽기
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer buffer = new StringBuffer();
                String line;

                while((line=reader.readLine()) != null){
                    buffer.append(line);
                }
                reader.close();

                binding.textView.setText(buffer.toString());

            }catch (Exception e){
                e.printStackTrace();

            }
        });

        //외장, 앱별 파일이용
        binding.button2.setOnClickListener(view -> {
            try{
                //쓰기
                //외장메모리 위치지정 -> getExternalFilesDir(null)
                File file = new File(getExternalFilesDir(null), "test.txt");
                FileWriter writer = new FileWriter(file, true);
                writer.write("hello world - internal");
                writer.flush();

                //읽기
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer buffer = new StringBuffer();
                String line;

                while((line=reader.readLine()) != null){
                    buffer.append(line);
                }
                reader.close();

                binding.textView.setText(buffer.toString());

            }catch (Exception e){
                e.printStackTrace();

            }
        });

        //permission 조정 다이얼로그, 사후 판단 콜백
        ActivityResultLauncher<String[]> launcher = registerForActivityResult(
                //퍼미션 여러개 조정 다이얼로그
                new ActivityResultContracts.RequestMultiplePermissions(),
                //사후 호출 call-back
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        if(result.containsValue(false)){
                            Toast.makeText(MainActivity.this, "permission denied",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //premission accessed
                            testFile();
                        }
                    }
                }
        );

        binding.button3.setOnClickListener(view -> {
            //permission check
//            if(ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED
//             && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)  {
//                testFile();
//            } else{
//                launcher.launch(
//                        new String[]{
//                                "android.permission.READ_EXTERNAL_STORAGE",
//                                "android.permission.WRITE_EXTERNAL_STORAGE"
//                        }
//                );
//            }

            //오류 이후 수정 버전별로 permission 확인진행
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                //퍼미션 체크..
                if(ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") ==
                        PackageManager.PERMISSION_GRANTED){
                    testFile();
                }else {
                    launcher.launch(
                            new String[]{
                                    "android.permission.READ_MEDIA_IMAGES"
                            }
                    );
                }
            }else {
                //퍼미션 체크..
                if(ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") ==
                                PackageManager.PERMISSION_GRANTED) {
                    testFile();
                }else {
                    launcher.launch(
                            new String[]{
                                    "android.permission.READ_EXTERNAL_STORAGE",
                                    "android.permission.WRITE_EXTERNAL_STORAGE"
                            }
                    );
                }
            }

            //testFile();

        });

    }

    void testFile(){
        //외장메모리의 갤러리앱이 가지고 있는 사진파일에 접근
        String[] projection = new String[]{
                MediaStore.Images.Media._ID, //사진 식별자
                MediaStore.Images.Media.DISPLAY_NAME //사진 이름
        };

        //갤러리 앱에서 정보획득
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null, null, null
        );

        while (cursor.moveToNext()){
            Uri uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getLong(0)
            );

            ContentResolver resolver = getApplicationContext().getContentResolver();
            try{
                //uri의 사진파일 읽기
                InputStream stream = resolver.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10; //데이터 1/10로 줄여서 로딩하기 위함

                //안드로이드 이미지 데이터 객체 -> bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);

                //화면출력
                binding.imageView.setImageBitmap(bitmap);


            }catch (Exception e ){
                e.printStackTrace();
            }

        }

    }
}