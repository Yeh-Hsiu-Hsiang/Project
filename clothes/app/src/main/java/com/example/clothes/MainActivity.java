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

import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getWeather;
import com.example.clothes.database.weatherDAO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // 宣告資料庫功能類別欄位變數
    private weatherDAO dao;
    private clothesDAO clothesDAO;
    getWeather getWeather = new getWeather();
    long dbcount = 1;
    // GPS 定位
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private String commadStr;
    private LocationManager locationManager;
    private int GPS_REQUEST_CODE = 10;

    public TextView CityName; // 顯示城市
    public TextView Today_Temperature; // 顯示氣溫
    public TextView TodayWeek; // 顯示星期
    public TextView date, Today_Time, Today_date, T_day, T_hour, WD_Day, WD_Hour, PoP_Day; // 顯示日期時間
    public TextView PoP; // 顯示降雨量
    public TextView Description, threehour_Description; // 顯示天氣敘述
    public ArrayList<String> CityName_list, T_day_list, T_hour_list, Today_Temperature_list, WD_Day_list, WD_Hour_list, Description_list, threehour_Description_list, PoP_Day_list, PoP_list;
    String Hour_three,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new weatherDAO(getApplicationContext());
        clothesDAO = new clothesDAO(getApplicationContext());
        CityName_list = new ArrayList<String>();
        T_day_list = new ArrayList<String>();
        T_hour_list = new ArrayList<String>();
        Today_Temperature_list = new ArrayList<String>();
        WD_Day_list = new ArrayList<String>();
        WD_Hour_list = new ArrayList<String>();
        Description_list = new ArrayList<String>();
        threehour_Description_list = new ArrayList<String>();
        PoP_Day_list = new ArrayList<String>();
        PoP_list = new ArrayList<String>();
        TodayWeek = (TextView) findViewById(R.id.week);
        date = (TextView) findViewById(R.id.date);
        CityName = (TextView) findViewById(R.id.CityName);
        Today_Temperature = (TextView) findViewById(R.id.C);
        PoP = (TextView) findViewById(R.id.PoP);
        Description = (TextView) findViewById(R.id.WeatherDescription);
        Today_Time = (TextView) findViewById(R.id.Today_Time);
        Today_date = (TextView) findViewById(R.id.Today_date);
        threehour_Description = (TextView) findViewById(R.id.threehour_Description);
        T_day = (TextView) findViewById(R.id.T_day);
        T_hour = (TextView) findViewById(R.id.T_hour);
        WD_Day = (TextView) findViewById(R.id.WD_Day);
        WD_Hour = (TextView) findViewById(R.id.WD_Hour);
        PoP_Day = (TextView) findViewById(R.id.PoP_Day);

        Today_Time.setText(new SimpleDateFormat("HH").format(new Date()));

        switch (Today_Time.getText().toString()){
            case "00":
            case "01":
            case "02":
                Hour_three ="00";
                break;
            case "03":
            case "04":
            case "05":
                Hour_three ="03";
                break;
            case "06":
            case "07":
            case "08":
                Hour_three ="06";
                break;
            case "09":
            case "10":
            case "11":
                Hour_three ="09";
                break;
            case "12":
            case "13":
            case "14":
                Hour_three ="12";
                break;
            case "15":
            case "16":
            case "17":
                Hour_three ="15";
                break;
            case "18":
            case "19":
            case "20":
                Hour_three ="18";
                break;
            case "21":
            case "22":
            case "23":
                Hour_three ="21";
                break;
        }
        Today_Time.setText(Hour_three);
        Today_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
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
        else{
            if(dao.getCount() == 528 && dao.getWDweather(city).size() != 0) {
                CityName.setText(dao.getWDweather(city).get(0).getNowCity());
            }else{
                CityName.setText("定位中");
            }
        }
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
            parseJSON ( today_data );
            if(Today_Temperature.getText().equals("°C") && dao.getWDweather(city).size() != 0)
                Today_Temperature.setText(dao.getWDweather(city).get(0).getTemperature() + " °C ");
        }

        private void parseJSON(String week_data)  {
            getWeather getWeather = new getWeather();
            JSONObject Ob;
            try{
                Ob = new JSONObject(week_data);
                JSONObject locations = Ob.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONObject ("locations");
                JSONArray location_array = locations.getJSONArray("location");

                for (int i = 0; i < location_array .length (); i++) {
                    JSONObject JsonObject = location_array.getJSONObject(i);
                    String locationName = JsonObject.getString("locationName");
                    CityName_list.add(locationName);
                    city = CityName.getText().toString();
//                    city = "台北市";
                    switch (city){
                        case "台北市":
                            city = "臺北市";
                            break;
                        case "台中市":
                            city = "臺中市";
                            break;
                        case "台東市":
                            city = "臺東市";
                            break;
                        case "台南市":
                            city = "臺南市";
                            break;
                    }
                    CityName.setText(city);
                }

                for (int b = 0; b < location_array .length (); b++) {
                    JSONObject JsonObject = location_array.getJSONObject(b);
                    String locationName = JsonObject.getString("locationName");
                    for (int citycount = 0; citycount < CityName_list.size(); citycount++) {
                        if (locationName.equals(CityName_list.get(citycount))) {
                            JSONArray weatherElement = JsonObject.getJSONArray("weatherElement");
                            for (int j = 0; j < weatherElement.length(); j++) {
                                JSONObject jsonObject2 = weatherElement.getJSONObject(j);
                                String elementName = jsonObject2.getString("elementName");
                                JSONArray time = jsonObject2.getJSONArray("time");
                                switch (elementName) {
                                    case "T":
                                        for (int k = 0; k < time.length(); k++) {
                                            JSONObject jsonObject3 = time.getJSONObject(k);
                                            String T_date = jsonObject3.getString("dataTime");
                                            String current_date = T_date.substring(0, 10);
                                            String T_time = T_date.substring(11, 13);
                                            T_day.setText(current_date);
                                            T_hour.setText(T_time);
                                            T_day_list.add(T_day.getText().toString());
                                            T_hour_list.add(T_hour.getText().toString());
                                            JSONObject TelementValue = jsonObject3.getJSONObject("elementValue");
                                            String value = TelementValue.getString("value");
                                            Today_Temperature_list.add(value);
                                        }
                                        break;
                                    // 天氣現象
                                    case "WeatherDescription":
                                        for (int k = 0; k < time.length(); k++) {
                                            JSONObject jsonObject3 = time.getJSONObject(k);
                                            String startTime = jsonObject3.getString("startTime");
                                            String WD_startDate = startTime.substring(0, 10);
                                            WD_Day.setText(WD_startDate);
                                            WD_Hour.setText(startTime.substring(11, 13));
                                            WD_Day_list.add(WD_Day.getText().toString());
                                            WD_Hour_list.add(WD_Hour.getText().toString());
                                            JSONObject WDelementValue = jsonObject3.getJSONObject("elementValue");
                                            String value = WDelementValue.getString("value");
                                            threehour_Description.setText(value);
                                            threehour_Description_list.add(threehour_Description.getText().toString());
                                            Integer WD_time = Integer.valueOf(startTime.substring(11, 13)).intValue();
                                            Integer current_Time = Integer.valueOf(Today_Time.getText().toString()).intValue();
                                            int quotients = Math.round(current_Time / 3);
                                            switch (quotients) {
                                                case 0:
                                                    current_Time = 0;
                                                    break;
                                                case 1:
                                                    current_Time = 3;
                                                    break;
                                                case 2:
                                                    current_Time = 6;
                                                    break;
                                                case 3:
                                                    current_Time = 9;
                                                    break;
                                                case 4:
                                                    current_Time = 12;
                                                    break;
                                                case 5:
                                                    current_Time = 15;
                                                    break;
                                                case 6:
                                                    current_Time = 18;
                                                    break;
                                                case 7:
                                                    current_Time = 21;
                                                    break;
                                                case 8:
                                                    current_Time = 0;
                                                    break;
                                            }
                                            Description_list.add(value);
                                            if (WD_startDate.equals(Today_date.getText().toString()) && WD_time == current_Time) {
                                                Description.setText(value);
                                            }
                                        }
                                        break;
                                    // 降雨機率
                                    case "PoP12h":
                                        for (int k = 0; k < time.length(); k++) {
                                            JSONObject jsonObject3 = time.getJSONObject(k);
                                            String startTime = jsonObject3.getString("startTime");
                                            String PoP_startTime = startTime.substring(0, 10);
                                            PoP_Day.setText(PoP_startTime);
                                            PoP_Day_list.add(PoP_Day.getText().toString());
                                            JSONObject elementValue = jsonObject3.getJSONObject("elementValue");
                                            String value = elementValue.getString("value");
                                            PoP_list.add(value);
                                            if (PoP_startTime.equals(Today_date.getText().toString())) {
                                                PoP.setText(value + " % ");
                                            }
                                        }
                                        break;
                                }
                            }
                        }
                        CompleteAdd(citycount);
                    }
                }
                dbcount = 1;
                for(int y = 0 ; y < dao.getWDweather(CityName.getText().toString()).size() ; y++){
                    if(dao.getWDweather(CityName.getText().toString()).get(0).getDay().equals(dao.getWDweather(CityName.getText().toString()).get(y).getT_Day()) &&
                            dao.getWDweather(CityName.getText().toString()).get(0).getHour().equals(dao.getWDweather(CityName.getText().toString()).get(y).getT_Hour())){
                             Today_Temperature.setText(dao.getWDweather(CityName.getText().toString()).get(y).getTemperature() + " °C ");
                    }
                }
            } catch(JSONException e) {
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
        if(!Today_Temperature.getText().equals("°C")){
            if(clothesDAO.getUPCount() == 0 || clothesDAO.getDOWNCount() == 0){
                Toast.makeText( MainActivity.this, "請先新增上衣及下衣至少各一件，才能進行預覽喔！" , Toast.LENGTH_LONG).show();
            }else {
                clothesDAO.close();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, viewclothes.class);
                startActivity(intent);
                finish();
            }
        }else {
            Toast.makeText( MainActivity.this, "需等待資料讀取，感謝您的耐心等候" , Toast.LENGTH_LONG).show();
        }
    }

    // 去天氣
    public void Weekweather(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , weather.class);
        startActivity(intent);
    }

    //整理進入DB
    public void CompleteAdd(int citycount){
        //如果定位位址改變，只更新NowCity
        getWeather.setNowCity(city);
        if(dao.getCount() == 528 && dao.getWDweather(city).size() != 0) {
            if (!city.equals(dao.getWDweather(city).get(0).getNowCity())) {
                for (long i = 1; i <= 528; i++) {
                    dao.getoneID(i);
                    getWeather.setId(i);
                    getWeather.setDay(dao.getoneID(i).getDay());
                    getWeather.setHour(dao.getoneID(i).getHour());
                    getWeather.setCityName(dao.getoneID(i).getCityName());
                    getWeather.setT_Day(dao.getoneID(i).getT_Day());
                    getWeather.setT_Hour(dao.getoneID(i).getT_Hour());
                    getWeather.setTemperature(dao.getoneID(i).getTemperature());
                    getWeather.setWD_Day(dao.getoneID(i).getWD_Day());
                    getWeather.setWD_Hour(dao.getoneID(i).getWD_Hour());
                    getWeather.setWeatherDescription(dao.getoneID(i).getWeatherDescription());
                    getWeather.setThreehour_Description(dao.getoneID(i).getThreehour_Description());
                    getWeather.setPoP_Day(dao.getoneID(i).getPoP_Day());
                    getWeather.setPoPh(dao.getoneID(i).getPoPh());
                    dao.update(getWeather);
                }
            }
        }

        getWeather.setDay(Today_date.getText().toString());
        getWeather.setHour(Today_Time.getText().toString());
        getWeather.setCityName(CityName_list.get(citycount));

        for (int a = 0; a < T_day_list.size(); a++) {
            getWeather.setT_Day(T_day_list.get(a));
            getWeather.setT_Hour(T_hour_list.get(a));
            getWeather.setTemperature(Today_Temperature_list.get(a));
            getWeather.setWD_Day(WD_Day_list.get(a));
            getWeather.setWD_Hour(WD_Hour_list.get(a));
            getWeather.setWeatherDescription(Description_list.get(a));
            getWeather.setThreehour_Description(threehour_Description_list.get(a));
            switch (a){
                case 0:
                case 1:
                case 2:
                case 3:
                    getWeather.setPoP_Day(PoP_Day_list.get(0));
                    getWeather.setPoPh(PoP_list.get(0));
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    getWeather.setPoP_Day(PoP_Day_list.get(1));
                    getWeather.setPoPh(PoP_list.get(1));
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    getWeather.setPoP_Day(PoP_Day_list.get(2));
                    getWeather.setPoPh(PoP_list.get(2));
                    break;
                case 12:
                case 13:
                case 14:
                case 15:
                    getWeather.setPoP_Day(PoP_Day_list.get(3));
                    getWeather.setPoPh(PoP_list.get(3));
                    break;
                case 16:
                case 17:
                case 18:
                case 19:
                    getWeather.setPoP_Day(PoP_Day_list.get(4));
                    getWeather.setPoPh(PoP_list.get(4));
                    break;
                case 20:
                case 21:
                case 22:
                case 23:
                    getWeather.setPoP_Day(PoP_Day_list.get(5));
                    getWeather.setPoPh(PoP_list.get(5));
                    break;
            }

            String[] Tdaylist_array,Today_date_array;
            Integer Tdaylist_Y ,Tdaylist_MM ,Tdaylist_DD ,Today_date_Y ,Today_date_MM ,Today_date_DD;
            Tdaylist_array = T_day_list.get(0).split("-");
            Today_date_array =Today_date.getText().toString().split("-");
            Tdaylist_Y = Integer.valueOf(Tdaylist_array[0]);
            Tdaylist_MM = Integer.valueOf(Tdaylist_array[1]);
            Tdaylist_DD = Integer.valueOf(Tdaylist_array[2]);
            Today_date_Y = Integer.valueOf(Today_date_array[0]);
            Today_date_MM = Integer.valueOf(Today_date_array[1]);
            Today_date_DD = Integer.valueOf(Today_date_array[2]);
            if(dao.getCount() != 528){
                dao.insert(getWeather);
            }else {
                if(Today_date_Y >= Tdaylist_Y && Today_date_MM >= Tdaylist_MM && Today_date_DD >= Tdaylist_DD){
                    if(Integer.valueOf(Hour_three) >= Integer.valueOf(T_hour_list.get(0))){
                        getWeather.setId(dbcount);
                        dao.update(getWeather);
                        dbcount++;
                    }
                }
            }
        }
        T_day_list = new ArrayList<String>();
        T_hour_list = new ArrayList<String>();
        Today_Temperature_list = new ArrayList<String>();
        WD_Day_list = new ArrayList<String>();
        WD_Hour_list = new ArrayList<String>();
        Description_list = new ArrayList<String>();
        threehour_Description_list = new ArrayList<String>();
        PoP_Day_list = new ArrayList<String>();
        PoP_list = new ArrayList<String>();
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

