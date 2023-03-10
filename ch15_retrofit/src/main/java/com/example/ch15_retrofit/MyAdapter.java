package com.example.ch15_retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ch15_retrofit.databinding.ItemMainBinding;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MyViewHolder extends RecyclerView.ViewHolder{
    ItemMainBinding binding;
    MyViewHolder(ItemMainBinding binding){
        super(binding.getRoot());
        this.binding = binding;

    }
}


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    MyApplication application; //여기서 네트워킹 진행
    ArrayList<UserModel> datas;

    MyAdapter(MyApplication application, ArrayList<UserModel> datas){
        this.application = application;
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //서버에서 받은 항목 데이터 획득
        UserModel user = datas.get(position);

        holder.binding.id.setText(user.id);
        holder.binding.firstNameView.setText(user.firstName);
        holder.binding.lastNameView.setText(user.lastName);

        if(user.avatar != null){
            //서버에서 이미지 url이 넘어왔다면
            //네트워킹 -> 이미지 다운로드
            Call<ResponseBody> call = application.networkService.getAvatarImage(user.avatar);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                            holder.binding.avatarView.setImageBitmap(bitmap);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }
}

