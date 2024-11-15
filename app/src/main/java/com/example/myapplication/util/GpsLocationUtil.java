package com.example.myapplication.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsLocationUtil {
    private LocationManager locationManager;//  android.location public class LocationManager
    private Location curLocation = null;
    private Context context;
    private String provider;
    public GpsLocationUtil(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
    //检查GPS或者网络是否开启
    public String checkGpsOrNetwork(){
        List<String> allProvider = locationManager.getProviders(true);
        if (allProvider.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            return null;
        } else if (allProvider.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            return null;
        } else {
            return "请打开GPS 或者 数据";
        }
    }
    private LocationListener locationListener =new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            curLocation = location;
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 可以在这里处理位置提供程序的状态变化
        }
        @Override
        public void onProviderEnabled(@NonNull String provider) {
            // 可以在这里处理位置提供程序被启用的情况
        }
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            // 可以在这里处理位置提供程序被禁用的情况
        }
    };
    // 获取当前位置
    public String getLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        //获取GPS位置信息
        curLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (curLocation != null) {
            double latitude = curLocation.getLatitude();
            double longitude = curLocation.getLongitude();
            // 根据经纬度获取城市名称
            return getCityName(latitude, longitude);
        } else {
            // 重新定位
          //  locationManager.requestLocationUpdates(provider, 2000, 1, locationListener);
            System.out.println("重新进行定位");
            return null;
        }
    }
    // 根据经纬度获取城市名称
    private String getCityName(double latitude, double longitude) {
        //地理编码器（Geocoder）来获取与这些坐标相关的地理位置信息，其中包括城市名称
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getLocality();
                return cityName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
