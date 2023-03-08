package com.example.ch8_menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //menu 구성을 위해서 자동 call 되는 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        //SearchView 획득
        MenuItem menuItem = menu.findItem(R.id.menu3);
        SearchView searchView = (SearchView) menuItem.getActionView();

        //검색 이벤트 등록
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //최종검색을 위해서 submit클릭을 누른 순간 처리
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query,Toast.LENGTH_SHORT).show();
                searchView.setQuery("",false);
                searchView.setIconified(true); //이전 상태로 돌아가시오 아이콘 꺼버리기

                return false;
            }

            //검색어 입력시마다 -> 추천단어 서비스 진행
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);


    }

    //menu event call-back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Toast.makeText(this, "menu1 click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Toast.makeText(this, "menu2 click", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}