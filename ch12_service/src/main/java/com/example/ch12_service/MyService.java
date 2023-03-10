package com.example.ch12_service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class MyService extends JobService {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("kima", "onCreate: called ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("kima", "onStartCommand: called ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("kima", "onDestroy: called ");
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //개발자 로직
        //시스템에 요청할 조건 적으면 됨

        Log.d("kima", "onStartJob: called ");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("kima", "onStopJob: called ");
        return false;
    }
}