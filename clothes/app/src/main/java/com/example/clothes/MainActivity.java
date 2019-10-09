package com.example.clothes;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calendar.AddSchedule;
import com.example.weather.weather;

public class MainActivity extends AppCompatActivity {

    private String commadStr;
    private LocationManager locationManager;
    private TextView textView;
    private Button button;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        //使用GPS定位
        commadStr = LocationManager.GPS_PROVIDER;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
                    return;
                }

                locationManager.requestLocationUpdates(commadStr, 1000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(commadStr);
                if (location != null)
                    textView.setText("經度：" + location.getLongitude() + "\n緯度：" + location.getLatitude());
                else
                    textView.setText("定位中");
            }
        });
    }

     public LocationListener locationListener = new LocationListener() {
         @Override
         public void onLocationChanged(Location location) {
             textView.setText("經度：" + location.getLongitude() + "\n緯度：" + location.getLatitude());
         }

         @Override
         public void onStatusChanged(String provider, int status, Bundle extras) { }

         @Override
         public void onProviderEnabled(String provider) { }

         @Override
         public void onProviderDisabled(String provider) { }
     };



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

