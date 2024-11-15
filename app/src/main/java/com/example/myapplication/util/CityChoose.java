package com.example.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CityChoose {
    public static  void initCityName(Context context){
        saveCityName(context,"北京","1");
        saveCityName(context,"沈阳","2");
        saveCityName(context,"成都","3");
        saveCityName(context,"上海","4");
        saveCityName(context,"深圳","5");
        saveCityName(context,"杭州","6");
    }
    public static List<String> getCities(Context context){
        SharedPreferences sp = context.getSharedPreferences("cities", Context.MODE_PRIVATE);
        HashMap<String,String> cities = (HashMap<String, String>) sp.getAll();
        List<String> cityNames = new ArrayList<>(cities.keySet() );
        return  cityNames;
    }
    public static void saveCityName(Context context,String  cityName,String order){
        SharedPreferences sp = context.getSharedPreferences("cities", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(cityName,order);
        editor.apply();
    }
}
