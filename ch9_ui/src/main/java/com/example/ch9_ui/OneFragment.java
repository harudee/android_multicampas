package com.example.ch9_ui;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ch9_ui.databinding.FragmentOneBinding;
import com.example.ch9_ui.databinding.ItemRecyclerviewBinding;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment {

    // fragment 화면 구성하기위해서 자동호출

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentOneBinding binding = FragmentOneBinding.inflate(inflater, container, false);

        List<String> list = new ArrayList<>();
        for(int i=0; i<20; i++){
            list.add("Item : "+i);
        }

        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycler.setAdapter(new MyAdapter(list));
        binding.recycler.addItemDecoration(new MyDecoration());

        return binding.getRoot();
    }

    //항목을 구성하기위한 view 객체를 가지는 역할자
    class MyViewHolder extends RecyclerView.ViewHolder{

        ItemRecyclerviewBinding binding;
        public MyViewHolder(ItemRecyclerviewBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }

    }

    //viewholder에 항목을 추가해서 구성
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        List<String> list; //항목 구성을 위한 집합데이터
        public MyAdapter(List<String> list){
            this.list = list;
        }

        //항목구성  view
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemRecyclerviewBinding binding =
                    ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new MyViewHolder(binding);
        }


        //항목 count
        @Override
        public int getItemCount() {
            return list.size();
        }


        //각 항목을 구성하기위해서 호출
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String txt = list.get(position);//데이터가 추가되면 여기가 조정될것
            holder.binding.itemData.setText(txt);

        }
    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        //각 항목을 꾸미기 위해 호출
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            // 몇번째 항목인지 get 해야함
            // 인덱스값을 1부터 계산하기 위해서 +1 처리
            int index = parent.getChildAdapterPosition(view)+1;

            if(index%3 == 0){ //3개씩 묶어서 좀 떨어뜨려서 구성
                outRect.set(20,20,20,60);
            } else {
                outRect.set(20,20,20,20);
            }

            view.setBackgroundColor(0xFFECE9E9);
            ViewCompat.setElevation(view, 20.0f); //elevation이 커지면 그림자 효과가 짙어짐
        }

        //항목이 다 호출된 이후 최후 꾸미기, 항목 위에 그려짐
        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            //반투명 이미지를 받아서
            //그리기 위한 좌표값 계산 필요

            //view의 사이즈
            int width = parent.getWidth();
            int height = parent.getHeight();

            //이미지 사이즈
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.kbo, null);
            int drWidth = drawable.getIntrinsicWidth();
            int drHeight= drawable.getIntrinsicHeight();

            //좌표계산 -> 그림을 가운데 위치시키기 위해서
            int left = width/2 - drWidth/2;
            int top = height/2 - drHeight/2;

            //이미지 그리기
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kbo), left, top,null);


        }
    }
}