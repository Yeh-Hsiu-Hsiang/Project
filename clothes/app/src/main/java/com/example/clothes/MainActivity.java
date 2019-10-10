package com.example.clothes;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.AddSchedule;
import com.example.weather.weather;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String commadStr;
    private LocationManager locationManager;
    private TextView textView;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        //使用GPS定位
        commadStr = LocationManager.GPS_PROVIDER;
        // LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            return;
        }

        locationManager.requestLocationUpdates(commadStr, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(commadStr);
        if (location != null)
            textView.setText("經度：" + location.getLongitude() + "\n緯度：" + location.getLatitude() + "\n位於：" + getAddressByLocation(location));
        else
            textView.setText("定位中");

    }

     public LocationListener locationListener = new LocationListener() {
         //當地點改變時
         @Override
         public void onLocationChanged(Location location) {
//             textView.setText("經度：" + location.getLongitude() + "\n緯度：" + location.getLatitude());
         }

         // 定位狀態改變
         @Override
         public void onStatusChanged(String provider, int status, Bundle extras) { }

         // 當GPS或網路定位功能開啟
         @Override
         public void onProviderEnabled(String provider) { }

         // 當GPS或網路定位功能關閉時
         @Override
         public void onProviderDisabled(String provider) { }
     };


    // 轉成地址
    public String getAddressByLocation(Location location) {
        String returnAddress = "";
        try {
            if (location != null) {
                Double longitude = location.getLongitude();        //取得經度
                Double latitude = location.getLatitude();        //取得緯度

                Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);        //地區:台灣
                Log.d("gc = ", String.valueOf(gc));
                //自經緯度取得地址
                List<Address> lstAddress = gc.getFromLocation(latitude, longitude, 1);
                Log.d("lstAddress = ", String.valueOf(lstAddress));
                if (!Geocoder.isPresent()){ //Since: API Level 9
                    returnAddress = "Sorry! Geocoder service not Present.";
                }
                returnAddress = lstAddress.get(0).getAddressLine(0);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return returnAddress;
    }



    // 管理衣服
    public void Manageclothes(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , Manageclothes.class);
        startActivity(intent);
    }

    // 預覽穿衣
    public void View_wearing(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , Manageclothes.class);
        startActivity(intent);
    }

    // 去新增活動
    public void AddSchedule(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , AddSchedule.class);
        startActivity(intent);
    }

    // 去天氣詳細
    public void Weekweather(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , weather.class);
        startActivity(intent);
    }

    // 去個人設定
    public void Mysetting(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , setting.class);
        startActivity(intent);
    }
}

