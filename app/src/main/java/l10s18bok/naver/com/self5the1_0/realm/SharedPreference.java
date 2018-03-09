package l10s18bok.naver.com.self5the1_0.realm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by pp on 2018-01-18.
 */

public class SharedPreference {
    public final String PACKGE_NAME = "l10s18bok.naver.com.self5the1_0";

    private SharedPreferences pref;

    //SharedPreference 생성자
    public SharedPreference(Context context){
        pref = context.getSharedPreferences(PACKGE_NAME, Activity.MODE_PRIVATE);
    }

    public void setIntegerPreferences(String key, int value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //String 값 저장 함수
    public void setStringPreferences(String key, String value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    //Boolean 값 저장 함수
    public void setBooleanPreferences(String key, boolean value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int getIntegerPreferences(String key, int value){
        int get_value = pref.getInt(key, value);
        return get_value;
    }

    //String 값 불러오는 함수
    // value 는 값이 없었을 때 출력 되는 값
    public String getStringPreferences(String key, String value){
        String get_value = pref.getString(key, value);
        return get_value;
    }


    //Boolean 값 불러오는 함수
    // value 는 값이 없었을 때 출력 되는 값
    public boolean getBooleanPreferences(String key, boolean value){
        boolean get_value = pref.getBoolean(key, value);
        return get_value;
    }
    //특정 키 값을 검색하여 삭제
    public void removePreferences(String key){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
    //모든 SharedPreference 값을 삭제
    public void removeAll(){
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }


}
