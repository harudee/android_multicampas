package com.example.ch7_preference;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import org.jetbrains.annotations.NotNull;

public class MySettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        //설정화면+유저이벤트+설정내용 저장까지 자동화. -> 한 줄로 가능
        setPreferencesFromResource(R.xml.settings, rootKey);

        //추추가(필수는 아님, 원한다면-> 설정 객체를 코드에서 획득해서 제어 가능)
        EditTextPreference idPreference = findPreference("id"); // settings.xml에서 설정한 key값으로 찾기
        ListPreference colorPreference = findPreference("color");

        // xml에 summary를 등록할 경우 고정 문자열임 (정적)
        // 동적 summary 설정이 필요할 경우 key를 받아서 수정해야한다.

        // 유저 설정값을 그대로 summary에 출력할 때
        colorPreference.setSummaryProvider(ListPreference.SimpleSummaryProvider.getInstance());

        //원하는 형태로 만들어야한다면 provider를 만들어야한다
        idPreference.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>(){

            @NonNull
            @Override
            public CharSequence provideSummary(@NotNull EditTextPreference preference){

                String txt = preference.getText();
                if(TextUtils.isEmpty(txt)){
                    return "설정이 되지 않았습니다. ";
                }else{
                    return "설정한 ID값은"+txt+"입니다";

                }

            }
        });


        // 설정을 하는 순간 값 저장은 자동으로 됨
        // 그때 우리 로직을 실행해야하는 경우 (서버로 넘겨서 중복체크할 경우 등)
        // 변경 순간에 이벤트를 걸어야 한다.
        idPreference.setOnPreferenceChangeListener(
                ((preference, newValue) -> {
                    Log.d("kima", "preference key: "+preference.getKey()+", value:" + newValue);
                    return true;
                })
        );



    }
}
