package com.example.weather;

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
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothes.MainActivity;
import com.example.clothes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class weather extends AppCompatActivity {

    public TextView Location; // 顯示城市
    public TextView HighLowTemperature; // 顯示最高最低溫

    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private String commadStr;
    private LocationManager locationManager;
    private int GPS_REQUEST_CODE = 10;

    public String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_weekweather);

        // 顯示所在位置
        Location = (TextView) findViewById(R.id.City);
        // 顯示最高最低溫
        HighLowTemperature = (TextView) findViewById(R.id.HighLowTemperature);

        // 讀取各縣市一週天氣預報
        new WeekTask ().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-005?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");

        if(!GPSIsOpen()) {
            return;
        }

        //使用GPS定位
        commadStr = LocationManager.GPS_PROVIDER;
        // LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(weather.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(weather.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(weather.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            return;
        }

        locationManager.requestLocationUpdates(commadStr, 1000, 0, locationListener);
        android.location.Location location = locationManager.getLastKnownLocation(commadStr);
        if (location != null)
            Location.setText(getAddressByLocation(location));
            // Location.setText("經度：" + location.getLongitude() + "\n緯度：" + location.getLatitude());
        else
            Location.setText("定位中");
    }

    // 判斷當前是否開啟GPS
    private boolean GPSIsOpen()
    {
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
                Log.d("lstAddress = ", String.valueOf(lstAddress));
                if (!Geocoder.isPresent()){ //Since: API Level 9
                    returnAddress = "Sorry! Geocoder service not Present.";
                }
                returnAddress = lstAddress.get(0).getAddressLine(0);
                Log.d("returnAddress = ", returnAddress);
                returnAddress = returnAddress.substring(5,8);
                Log.d("city = ", returnAddress);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return returnAddress;
    }


    //  取得伺服端傳來回應
    class WeekTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder ();
            try {
                URL url = new URL ( params[0] );
                BufferedReader in = new BufferedReader (new InputStreamReader ( url.openStream () ) );
                String line = in.readLine ();
                while (line != null) {
                    Log.d ( "HTTP", line );
                    sb.append ( line );
                    line = in.readLine ();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
            return sb.toString ();
        }

        protected void onPostExecute(String data) {
            super.onPostExecute ( data );
            Log.d ( "JSON", data );
            parseJSON ( data );
        }

        private void parseJSON(String data)  {
            JSONObject Ob;
            try{
                Ob = new JSONObject(data);
                JSONArray location_array = Ob.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONArray ("location");
                Log.d("location_array", "location_array = " + location_array);

                for (int i = 0; i < location_array .length (); i++) {
                    JSONObject JsonObject = location_array.getJSONObject(i);
                    Log.d("jsonObject", "json = " + JsonObject);
                    String locationName = JsonObject.getString("locationName");
                    Log.d("locationName", "城市 = " + locationName);

                    JSONArray weatherElement = JsonObject.getJSONArray("weatherElement");
                    Log.d("weatherElement", "weatherElement = " + weatherElement);
                    for (int j = 0; j < weatherElement.length(); j++) {
                        JSONObject jsonObject2 = weatherElement.getJSONObject(j);
                        String elementName = jsonObject2.getString("elementName");
                        Log.d("elementName", "elementName = " + elementName);
                        if (elementName == "MaxT") {
                            Log.d("MaxT 判斷", "OK ");
                            JSONArray time = jsonObject2.getJSONArray("time");
                            Log.d("time", "time = " + time);
                            for (int k = 0; k < time.length(); k++) {
                                JSONObject jsonObject3 = weatherElement.getJSONObject(j);
                                String parameter = jsonObject3.getString("parameter");
                                Log.d("parameter", "parameter = " + parameter);
                                String MaxT = JsonObject.getString("parameterName");
                                Log.d("MaxT", "最高溫 = " + MaxT);
                            }
                        } else if (elementName == "MinT") {
                            String MinT = JsonObject.getString("time");
                            Log.d("MinT", "最低溫 = " + MinT);
                        }
                    }
                }
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass( weather.this  , MainActivity.class);
        startActivity(intent);
        weather.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, weather.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }

}
