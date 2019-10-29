package com.example.weather;

import android.Manifest;
import android.app.assist.AssistStructure;
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
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public TextView HighTemperature; // 顯示最高溫
    public TextView LowTemperature; // 顯示最低溫

    private HorizontalScrollView scrollView;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekweather);

        scrollView = (HorizontalScrollView) this.findViewById(R.id.scroll_view);
        linear = (LinearLayout) this.findViewById(R.id.linear);
        createChildLinearLayout();

        // 顯示所在位置
        Location = (TextView) findViewById(R.id.City);
        // 顯示最高最低溫
        HighTemperature = (TextView) findViewById(R.id.HighTemperature);
        LowTemperature = (TextView) findViewById(R.id.LowTemperature);

        // 讀取各縣市一週天氣預報
        new WeekTask().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-D0047-003?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");

    }

    //  取得伺服端傳來回應
    private class WeekTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder ();
            try {
                URL url = new URL ( params[0] );
                BufferedReader in = new BufferedReader (new InputStreamReader ( url.openStream () ) );
                String line = in.readLine ();
                while (line != null) {
                    //                    Log.d ( "HTTP", line );
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

        protected void onPostExecute(String week_data) {
            super.onPostExecute ( week_data );
            //            Log.d ( "JSON", data );
            parseJSON ( week_data );
        }

        private void parseJSON(String week_data)  {
            JSONObject Ob;
            try{
                Ob = new JSONObject(week_data);
                JSONArray location_array = Ob.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONArray ("location");

                for (int i = 0; i < location_array .length (); i++) {
                    JSONObject JsonObject = location_array.getJSONObject(i);
                    String locationName = JsonObject.getString("locationName");
                    Log.d("locationName", "城市 = " + locationName);

                    JSONArray weatherElement = JsonObject.getJSONArray("weatherElement");
                    Log.d("weatherElement", "weatherElement = " + weatherElement);
                    for (int j = 0; j < weatherElement.length(); j++) {
                        JSONObject jsonObject2 = weatherElement.getJSONObject(j);
                        String elementName = jsonObject2.getString("elementName");
                        Log.d("elementName", "elementName = " + elementName);
                        JSONArray time = jsonObject2.getJSONArray("time");
                        Log.d("time", "time = " + time);
                        switch  (elementName) {
                            // 天氣現象
                            case "Wx":
                                for (int k = 0; k < time.length(); k++) {
                                    JSONObject jsonObject3 = time.getJSONObject(k);
                                    JSONObject parameter = jsonObject3.getJSONObject("parameter");
                                    Log.d("parameter", "parameter = " + parameter);
                                    String parameterName = parameter.getString("parameterName");
                                    Log.d("Wx", "Wx = " + parameterName);
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
                        }
                    }
                }
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void createChildLinearLayout() {
        for (int n = 0; n < 20; n++) {
            LinearLayout.LayoutParams linearLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout myLinear = new LinearLayout(this);
            linearLp.setMargins(30, 50, 30, 50);
            myLinear.setOrientation(LinearLayout.VERTICAL);
            myLinear.setTag(n);
            linear.addView(myLinear, linearLp);
            // 小時
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(150, 150);
            TextView textView = new TextView(this);
            textView.setText(n + "");
            textView.setGravity(Gravity.CENTER);
            myLinear.addView(textView, textViewLp);
            // 天氣圖示
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.weather);
            imageView.setScaleType(ImageView.ScaleType. CENTER_CROP);
            myLinear.addView(imageView, lp);

            myLinear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(weather.this, v.getTag().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });
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
