package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.entity.WeatherInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDetailActivity extends AppCompatActivity {

    private LinearLayout llFirst;
    private ImageView imgWeather;
    private TextView tvTempreture;
    private LinearLayout llBottom;
    private ImageView img1;
    private TextView tv1;
    private ImageView img2;
    private TextView tv2;
    private ImageView img3;
    private TextView tv3;
    private ImageView img4;
    private TextView tv4;
    private ImageView img5;
    private TextView tv5;
    private ImageView img6;
    private TextView tv6;
    private ImageView img7;
    private TextView tv7;
    private ImageView img8;
    private TextView tv8;
    private ImageView img9;
    private TextView tv9;
    private TextView tvDate;

    private TextView tvDesc;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        initView();
        try {
            initData();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void initView(){
        tvDate = (TextView) findViewById(R.id.tv_date);
        llFirst = (LinearLayout) findViewById(R.id.ll_first);
        imgWeather = (ImageView) findViewById(R.id.img_weather);
        tvTempreture = (TextView) findViewById(R.id.tv_tempreture);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        img1 = (ImageView) findViewById(R.id.img_1);
        tv1 = (TextView) findViewById(R.id.tv_1);
        img2 = (ImageView) findViewById(R.id.img_2);
        tv2 = (TextView) findViewById(R.id.tv_2);
        img3 = (ImageView) findViewById(R.id.img_3);
        tv3 = (TextView) findViewById(R.id.tv_3);
        img4 = (ImageView) findViewById(R.id.img_4);
        tv4 = (TextView) findViewById(R.id.tv_4);
        img5 = (ImageView) findViewById(R.id.img_5);
        tv5 = (TextView) findViewById(R.id.tv_5);
        img6 = (ImageView) findViewById(R.id.img_6);
        tv6 = (TextView) findViewById(R.id.tv_6);
        img7 = (ImageView) findViewById(R.id.img_7);
        tv7 = (TextView) findViewById(R.id.tv_7);
        img8 = (ImageView) findViewById(R.id.img_8);
        tv8 = (TextView) findViewById(R.id.tv_8);
        img9 = (ImageView) findViewById(R.id.img_9);
        tv9 = (TextView) findViewById(R.id.tv_9);
        tvDesc = (TextView) findViewById(R.id.tv_desc);

    }


    public void initData() throws ParseException {
        Intent intent = getIntent();
        WeatherInfo.ShowapiResBodyBean.WeatherDay forecastBean = (WeatherInfo.ShowapiResBodyBean.WeatherDay) intent.getSerializableExtra("weatherdetail");

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = inputFormat.parse(forecastBean.getDay());
        tvDate.setText(outputFormat.format(date));

        Glide.with(this).load(forecastBean.getDay_weather_pic()).into(imgWeather);

        tvTempreture.setText(forecastBean.getDay_air_temperature()+"/"+forecastBean.getNight_air_temperature()+"°C");
        tvDesc.setText(forecastBean.getDay_weather()+"   \n "+forecastBean.getDay_wind_direction()+"  "+forecastBean.getDay_wind_power());

        tv3.setText(forecastBean.getZiwaixian());
        tv1.setText(forecastBean.getAir_press());
        tv2.setText(forecastBean.getJiangshui());
        String[] sun = forecastBean.getSun_begin_end().split("|");
        tv4.setText(sun[0]);
        tv5.setText(sun[1]);
        tv7.setText(forecastBean.getNight_air_temperature());
        tv8.setText(forecastBean.getNight_wind_direction());

    }
}