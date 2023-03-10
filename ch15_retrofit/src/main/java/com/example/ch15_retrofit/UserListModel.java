package com.example.ch15_retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//server DTO, 페이지 정보
public class UserListModel {

    public String page;
    @SerializedName("per_page")
    public String perPage;
    public String total;
    @SerializedName("total_pages")
    String totalPages;
    ArrayList<UserModel> data;

}
