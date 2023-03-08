package com.example.ch9_ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ch9_ui.databinding.ActivityMainBinding;

import java.util.ArrayList;

//ViewPager -> adapter 클래스 생성 -> 원래는 빼서 다른데 만들어라
// Viewpager의 화면이 fragment로 구성되는 경우
class MyFragmentPagerAdapter extends FragmentStateAdapter{
    ArrayList<Fragment> fragments;
    MyFragmentPagerAdapter(FragmentActivity activity){
        super(activity);
        fragments = new ArrayList<>();
        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());

    }

    //항목 갯수를 판단해서 자동호출
    @Override
    public int getItemCount() {
        return fragments.size();
    }

    //자동으로 호출됨, 매개변수를 보고 적절한 fragment 객체를 리턴
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
}


public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //viewPager....
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);


        //drawer toggle....
        toggle = new ActionBarDrawerToggle(this, binding.drawer,R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState(); //제스쳐와 토글버튼 sync

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //toggle menu취급 차단
        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}