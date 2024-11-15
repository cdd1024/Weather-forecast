package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.util.CityChoose;

import java.util.ArrayList;
import java.util.List;

public class CityChooseActivity extends AppCompatActivity {
    private ListView lvCities;
    private List<String> cityNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        initView();
        initData();
        bindData();
    }
    public void initView(){
        lvCities = (ListView) findViewById(R.id.lv_cities);
    }
    public void initData(){
        CityChoose.initCityName(this);
        cityNames = CityChoose.getCities(this);
    }
    public void bindData(){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,cityNames);
        lvCities.setAdapter(adapter);
        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cityName = (String) adapterView.getItemAtPosition(i);
                Intent intent = new Intent();
                intent.putExtra("cityName",cityName);
                setResult(0,intent);
                finish();
            }
        });
    }
}