package com.example.ch15_retrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ch15_retrofit.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //네트워킹 하기전까지 비어있음
    ArrayList<UserModel> userList = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //최초에는 adapter에는 빈 데이터 전달, 아무것도 없을것임
        adapter = new MyAdapter((MyApplication) getApplicationContext(), userList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        INetworkService networkService = ((MyApplication) getApplicationContext()).networkService;
        Call<UserListModel> call = networkService.doGetUserList("1");

        call.enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
                //성공시
                userList.addAll(response.body().data);
                //adapter에 동적 항목 추가 제거는... adapter에서 찍는 데이터 집합에 데이터만 추가 제거한 후에
                //반영 명령을 ㄱㄱ
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<UserListModel> call, Throwable t) {

            }
        });
    }
}