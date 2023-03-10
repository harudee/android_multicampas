package com.example.ch15_retrofit;


import com.google.gson.annotations.SerializedName;

//Service data DTO
//user가 1명
public class UserModel {
    // 회사 json데이터에 맞춰서 작성하면 됨
    public String id;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    public String avatar; // img url 정보

    //서버 데이터와 관련없는 변수도 선언가능

}
