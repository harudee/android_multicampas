package com.example.ch12_service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ch12_service.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            //정보의 숫자값 : 식별자, 여러 개의 jobService가 있을 수 있기 때문에 외부에서 종료시키고싶을때 사용함
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this, MyService.class));
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); //wifi에 연결됐을 때
            JobInfo info = builder.build();
            //info를 등록
            scheduler.schedule(info);
        });
    }
}