package com.example.ch11_receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //notification 띄우기
        //안드로이드의 모든 시스템 서비스는 getSystemService() 로 획득 (상수변수로 식별됨)
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //26버전 이상부터는 채널개념 도입해야 정상적인 알람이 뜬다
            NotificationChannel channel = new NotificationChannel(
                    "oneChannel",
                    "One Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setDescription("One Channel");
            manager.createNotificationChannel(channel); //일단 한번 등록해줘야함

            //이미 등록된 채널을 명시하고 builder를 생성 (동일한 id를 넣어야함)
            builder = new NotificationCompat.Builder(context, "oneChannel");


        }else{
            builder = new NotificationCompat.Builder(context);

        }

        builder.setSmallIcon(android.R.drawable.ic_notification_overlay);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("메시지 도착");
        builder.setContentText("오늘 저녁은 햄버거 입니다");

        manager.notify(11, builder.build());

    }
}