package com.example.ch13_provider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.ch13_provider.databinding.ActivityMainBinding;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //우리가 만드는 파일 경로..
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //gallery 앱과 연동하기위한 launcher 준비
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), //요청처리자
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //갤러리 목록에서 사진 선택후 되돌아올 때 처리 -> 화면에 올림
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 10; // 1/10사이즈로 줄여서 로딩...

                        try {
                            // 사진의 식별자가 url 형식으로 넘어온다 갤러리 프로바이더에서 연동되는 url
                            // 식별자로 뽑아서 파일경로도 획득가능함
                            // 갤러리에서 inputstream도 제공함
                            InputStream inputStream = getContentResolver().openInputStream(result.getData().getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                            inputStream.close();

                            if(bitmap != null){
                                binding.userImageView.setImageBitmap(bitmap); //화면에 이미지 교체
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        //launcher 실행
        binding.galleryButton.setOnClickListener(view -> {
            //유저가 버튼을 클릭했을때
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            launcher.launch(intent);

        });


        //camera launcher -> 갤러리거 복붙해서 사용
        ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), //요청처리자
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //갤러리 목록에서 사진 선택후 되돌아올 때 처리 -> 화면에 올림
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 10; // 1/10사이즈로 줄여서 로딩...

                        //사실 얘는 try-catch 필요없서
                        try {
                            //카메라 앱에서 사진 촬영을 마치고 되돌아왔을때
                            //경로의 사진을 읽어서 출력만 하면됨
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                            if(bitmap != null){
                                binding.userImageView.setImageBitmap(bitmap); //화면에 이미지 출력
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        binding.cameraButton.setOnClickListener(view -> {
            try {
                //파일준비 - 시스템 현재시간으로
                String tempStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                //외장 앱(사진 저장 directory )
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = File.createTempFile(
                        "JPEG+"+tempStamp+"_",
                        ".jpg",
                        storageDir
                );
                //현재 파일의 경로를 저장
                filePath = file.getAbsolutePath();
                //intent 정보로 camera에 넘길 파일 정보를 준비 (uri 형식으로)
                Uri uri = FileProvider.getUriForFile(
                        this,
                        "com.example.ch13_provider.fileprovider",
                        file
                        );

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //이것만하고 보내면 썸네일 방식
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //공유형식으로 ㄱㄱ
                cameraLauncher.launch(intent);

            }catch (Exception e){
                e.printStackTrace();
            }


        });


    }
}