package com.example.ch15_retrofit;


import android.app.Application;

import retrofit2.Retrofit;

//Application 클래스
//앱이 실행되면서 최초에 가장먼저 생성되는 객체 하나만...
//앱이 동작하면서 여러곳에서 이용할 상수 변수... 객체등을 준비하는 역할
//manifest에 등록해야 함
public class MyApplication extends Application {
    //retrofit2 초기화
    INetworkService networkService;
    Retrofit retrofit;

    public MyApplication(){

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("retrofit2://");

        retrofit = builder.build();
        networkService = retrofit.create(INetworkService.class);

    }

}
