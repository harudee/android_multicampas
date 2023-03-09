package com.example.ch11_receiver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ch11_receiver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //퍼미션 허락
        ActivityResultLauncher launcher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                isGranted -> {
                    if(isGranted.containsValue(false)){
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    } else{
                        noti();
                    }
                }
        );

        binding.button.setOnClickListener(view -> {
            //permission check
            //noti permission은 api level 33버전부터 추가됨
            //33 이하 버전에서는 항상 denied

            //따라서 user phone version으로 분기
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                if(ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED){
                    noti();

                }else {
                    launcher.launch(new String[]{"android.permission.POST_NOTIFICATIONS"});
                }

            } else{
                noti();
            }

        });
    }

    void noti(){

        Intent intent = new Intent(this, MyReceiver.class);
        sendBroadcast(intent);
    }
}