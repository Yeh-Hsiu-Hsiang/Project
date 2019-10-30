package com.example.clothes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.CalendarView;
import com.example.viewclothes.viewclothes;
import com.example.weather.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // GPS 定位
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private String commadStr;
    private LocationManager locationManager;
    private int GPS_REQUEST_CODE = 10;

    public TextView CityName; // 顯示城市
    public TextView Today_Temperature; // 顯示氣溫
    public TextView TodayWeek; // 顯示星期
    public TextView date; // 顯示日期
    public TextView PoP; // 顯示降雨量
    public TextView Description; // 顯示天氣敘述

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TodayWeek = (TextView) findViewById(R.id.week);
        date = (TextView) findViewById(R.id.date);
        CityName = (TextView) findViewById(R.id.CityName);
        Today_Temperature = (TextView) findViewById(R.id.C);
        PoP = (TextView) findViewById(R.id.PoP);
        Description = (TextView) findViewById(R.id.WeatherDescription);

        date.setText(new SimpleDateFormat("yyyy / MM / dd").format(new Date()));
        //  獲取當前系統星期
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        TodayWeek.setText(format.format(date));

        // 鄉鎮天氣預報-臺灣未來 2 天天氣預報
        new TodayTask().execute ( "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-D0047-089?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON" );

        if (!GPSIsOpen()) {
            return;
        }

        //使用GPS定位
        commadStr = LocationManager.GPS_PROVIDER;
        // LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            return;
        }

        locationManager.requestLocationUpdates(commadStr, 1000, 0, locationListener);
        android.location.Location location = locationManager.getLastKnownLocation(commadStr);
        if (location != null)
            CityName.setText(getAddressByLocation(location));
            // Location.setText("經度：" + location.getLongitude() + "\n緯度：" + location.getLatitude());
        else
            CityName.setText("定位中");
    }

    // 判斷當前是否開啟GPS
    private boolean GPSIsOpen() {
        boolean GPS = true;
        LocationManager alm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if(!alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "未開啟GPS", Toast.LENGTH_SHORT).show();
            GPS = false;
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, GPS_REQUEST_CODE);
        } else {
            Toast.makeText(this, "GPS已開啟", Toast.LENGTH_SHORT).show();
        }
        return GPS;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            // 做需要做的事情，比如再次检测是否打开GPS了 或者定位
            GPSIsOpen();
        }
    }

    public LocationListener locationListener = new LocationListener() {
        //當地點改變時
        @Override
        public void onLocationChanged(Location location) {
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
                //自經緯度取得地址
                List<Address> lstAddress = gc.getFromLocation(latitude, longitude, 1);
                if (!Geocoder.isPresent()){ //Since: API Level 9
                    returnAddress = "Sorry! Geocoder service not Present.";
                }
                returnAddress = lstAddress.get(0).getAddressLine(0);
                returnAddress = returnAddress.substring(5,8);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return returnAddress;
    }

    //  取得伺服端傳來回應
    private class TodayTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder today_sb = new StringBuilder ();
            try {
                URL url = new URL ( params[0] );
                BufferedReader in = new BufferedReader (new InputStreamReader( url.openStream () ) );
                String line = in.readLine ();
                while (line != null) {
//                    Log.d ( "HTTP", line );
                    today_sb.append ( line );
                    line = in.readLine ();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
            return today_sb.toString ();
        }

        protected void onPostExecute(String today_data) {
            super.onPostExecute ( today_data );
//            Log.d ( "JSON", today_data );
            parseJSON ( today_data );
        }

        private void parseJSON(String week_data)  {
            JSONObject Ob;
            try{
                Ob = new JSONObject(week_data);
                JSONObject locations = Ob.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONObject ("locations");
                JSONArray location_array = locations.getJSONArray("location");

                for (int i = 0; i < location_array .length (); i++) {
                    JSONObject JsonObject = location_array.getJSONObject(i);
                    String locationName = JsonObject.getString("locationName");
                    Log.d("locationName", "城市 = " + locationName);
                    String city = CityName.getText().toString();
                    switch (city){
                        case "台北市":
                            city = "臺北市";
                            CityName.setText("臺北市");
                            break;
                        case "台中市":
                            city = "臺中市";
                            CityName.setText("臺中市");
                            break;
                        case "台東市":
                            city = "臺東市";
                            CityName.setText("臺東市");
                            break;
                        case "台南市":
                            city = "臺南市";
                            CityName.setText("臺南市");
                            break;
                    }
                    if (locationName.equals(city)) {
                        Log.d("loaction = city","yes");
                        JSONArray weatherElement = JsonObject.getJSONArray("weatherElement");
                        Log.d("weatherElement", "weatherElement = " + weatherElement);
                        for (int j = 0; j < weatherElement.length(); j++) {
                            JSONObject jsonObject2 = weatherElement.getJSONObject(j);
                            String elementName = jsonObject2.getString("elementName");
                            Log.d("elementName", "elementName = " + elementName);
                            JSONArray time = jsonObject2.getJSONArray("time");
                            Log.d("time", "time = " + time);
                            switch  (elementName) {
                                case "T":
                                    for (int k = 0; k < time.length(); k++) {
                                        JSONObject jsonObject3 = time.getJSONObject(k);
                                        JSONObject elementValue = jsonObject3.getJSONObject("elementValue");
                                        Log.d("elementValue", "elementValue = " + elementValue);
                                        String value = elementValue.getString("value");
                                        Log.d("T", "T = " + value);
                                        Today_Temperature.setText(value + " °C ");
                                    }
                                    break;
                                // 天氣現象
                                case "WeatherDescription":
                                    for (int k = 0; k < time.length(); k++) {
                                        JSONObject jsonObject3 = time.getJSONObject(k);
                                        JSONObject elementValue = jsonObject3.getJSONObject("elementValue");
                                        Log.d("elementValue", "elementValue = " + elementValue);
                                        String value = elementValue.getString("value");
                                        Description.setText(value);
                                    }
                                    break;
                                case "MaxT":
                                    for (int k = 0; k < time.length(); k++) {
                                        JSONObject jsonObject3 = time.getJSONObject(k);
                                        JSONObject parameter = jsonObject3.getJSONObject("parameter");
                                        Log.d("parameter", "parameter = " + parameter);
                                        String parameterName = parameter.getString("parameterName");
                                        Log.d("MaxT", "MaxT = " + parameterName);
                                    }
                                    break;
                                case "MinT":
                                    for (int k = 0; k < time.length(); k++) {
                                        JSONObject jsonObject3 = time.getJSONObject(k);
                                        JSONObject parameter = jsonObject3.getJSONObject("parameter");
                                        Log.d("parameter", "parameter = " + parameter);
                                        String parameterName = parameter.getString("parameterName");
                                        Log.d("MinT", "MinT = " + parameterName);
                                    }
                                    break;
                                // 降雨機率
                                case "PoP12h":
                                    for (int k = 0; k < time.length(); k++) {
                                        JSONObject jsonObject3 = time.getJSONObject(k);
                                        JSONObject elementValue = jsonObject3.getJSONObject("elementValue");
                                        Log.d("elementValue", "elementValue = " + elementValue);
                                        String value = elementValue.getString("value");
                                        Log.d("PoP", "PoP = " + value);
                                        PoP.setText(value + " % ");
                                    }
                                    break;
                            }
                        }
                    }else if(locationName != city)  {
                        Toast.makeText(MainActivity.this, "查詢不到所在位置天氣", Toast.LENGTH_LONG).show();
                    }
                }
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
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
        intent.setClass( MainActivity.this  , viewclothes.class);
        startActivity(intent);
    }

    // 去行事曆
    public void Calendar(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , CalendarView.class);
        startActivity(intent);
    }

    // 去天氣
    public void Weekweather(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , weather.class);
        intent.putExtra("locationName", CityName.getText().toString());
        intent.putExtra("Today_Temperature", Today_Temperature.getText().toString());
        intent.putExtra("PoP", PoP.getText().toString());
        intent.putExtra("WeatherDescription", Description.getText().toString());
        Log.d("put","ok");
        startActivity(intent);
    }

    // 去個人設定
    public void Mysetting(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , setting.class);
        startActivity(intent);
    }

    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , MainActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }

}

