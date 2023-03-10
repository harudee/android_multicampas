package com.example.ch15_retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface INetworkService {
    //실제 안드로이드의 Service는 아닌데 레트로핏에서 이럿게 함

    @GET("api/users")
    Call<UserListModel> doGetUserList(@Query("page") String page);

    //결과를 그냥 RespnseBody...
    @GET()
    Call<ResponseBody> getAvatarImage(@Url String url);

}
