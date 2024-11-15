package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.adapter.WeatherListAdapter;
import com.example.myapplication.entity.WeatherInfo;
import com.example.myapplication.util.GpsLocationUtil;
import com.example.myapplication.util.NetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {
    private TextView tvWendu;
    private TextView tvStatus;
    private TextView tvFengxiang;
    private TextView tvShidu;
    private TextView tvAddress;
    private ImageView imageLocation;
    private ListView weatherListView;
    private WeatherListAdapter adapter;
    private TextView tvPm;
    MyHandler handler = new MyHandler();
    List<WeatherInfo.ShowapiResBodyBean.WeatherDay> list  = new ArrayList<>();
    WeatherInfo weatherInfo;
    GpsLocationUtil locationUtil = null;
    private String cityName = "沈阳";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        locationUtil = new GpsLocationUtil(this);
        //检查GPS或者网络是否开启
        String checkGpsOrNetWork = locationUtil.checkGpsOrNetwork();
        if(checkGpsOrNetWork != null){
            Toast.makeText(this, "请打开GPS 或者 数据", Toast.LENGTH_SHORT).show();
            //如果没开启GPS，直接按默认城市查询
            MyThread myThread = new MyThread();
            myThread.start();
        }else{
            MyThreadGetLocation myThreadGetLocation = new MyThreadGetLocation();
            myThreadGetLocation.start();
        }
    }

    //初始化视图
    public void initView() {
        tvWendu = (TextView) findViewById(R.id.tv_wendu);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvFengxiang = (TextView) findViewById(R.id.tv_fengxiang);
        tvShidu = (TextView) findViewById(R.id.tv_shidu);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        imageLocation = (ImageView) findViewById(R.id.image_location);
        weatherListView = (ListView) findViewById(R.id.weatherListView);
        tvPm = (TextView) findViewById(R.id.tv_pm);
        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherActivity.this,CityChooseActivity.class);
                //intent.putExtra("cityName", cityName); // 将当前城市名称作为额外数据传递
                startActivityForResult(intent,0);
            }
        });
    }

    //处理异步消息
    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                tvAddress.setText(cityName);
                adapter = new WeatherListAdapter(WeatherActivity.this, R.layout.item_weather, list);
                weatherListView.setAdapter(adapter);
                tvWendu.setText(weatherInfo.getShowapi_res_body().getNow().getTemperature());
                tvStatus.setText(weatherInfo.getShowapi_res_body().getNow().getWeather());
                tvFengxiang.setText("空气  " + weatherInfo.getShowapi_res_body().getNow().getAqiDetail().getQuality());
                tvShidu.setText("湿度  " + weatherInfo.getShowapi_res_body().getNow().getSd());
                tvPm.setText("PM2.5  " + weatherInfo.getShowapi_res_body().getNow().getAqiDetail().getPm2_5());
            } else if(msg.what == 3){
                MyThread myThread = new MyThread();
                myThread.start();
            }
        }
    }

    //进行GPS定位
    class MyThreadGetLocation extends Thread{
        @Override
        public void run() {
            super.run();
            requestLocationPermission();
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            String url = "https://route.showapi.com/9-2";
            try {
                Map<String,String> params = new HashMap<>();
                params.put("showapi_appid","1603525");
                params.put("showapi_sign","33d1075df52444af9e2f29262bc8f617");
                params.put("area",cityName);
                params.put("needMoreDay","1");
                params.put("needHourData","0");
                params.put("needIndex","0");
                params.put("need3HourForcast","0");
                params.put("needAlarm","0");
                String str = NetUtil.net(url, params, "GET");
                JSONObject jsonObject = JSONObject.parseObject(str);
                weatherInfo = jsonObject.toJavaObject(WeatherInfo.class);
                list = new ArrayList<>();
                list.add(weatherInfo.getShowapi_res_body().getF1());
                list.add(weatherInfo.getShowapi_res_body().getF2());
                list.add(weatherInfo.getShowapi_res_body().getF3());
                list.add(weatherInfo.getShowapi_res_body().getF4());
                list.add(weatherInfo.getShowapi_res_body().getF5());
                list.add(weatherInfo.getShowapi_res_body().getF6());
                list.add(weatherInfo.getShowapi_res_body().getF7());

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 在Activity或Fragment中
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    // 检查和请求位置权限
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            // 权限已经被授予
            String location = locationUtil.getLocation();
            //如果取不到地址，就用默认地址：沈阳
            this.cityName = location!=null?location:"沈阳";
        }
    }
    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了位置权限
                String location = locationUtil.getLocation();
                //如果取不到地址，就用默认地址：沈阳
                this.cityName = location!=null?location:"沈阳";
            } else {
                // 用户拒绝了位置权限
                Toast.makeText(this, "需要位置权限才能获取当前位置", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //选择完城市，跳转回来，更新城市数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 0){
                this.cityName = data.getStringExtra("cityName");
                MyThread myThread = new MyThread();
                myThread.start();
            }
        }
    }
}
