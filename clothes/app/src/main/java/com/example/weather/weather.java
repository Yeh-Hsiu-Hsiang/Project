package com.example.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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


public class weather extends AppCompatActivity {

    public TextView Json; // 顯示天氣
    public TextView City; // 顯示城市
    public String Where;  // 查詢城市
    private Spinner sp;  // 城市清單

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_weekweather );

        View v = findViewById(R.id.today_relative);//設透明背景的layout 的id
        v.getBackground().setAlpha(200);//0~255透明度值

        // 顯示 Json
        Json = (TextView) findViewById(R.id.weather);
        // 顯示結果
        City = (TextView) findViewById(R.id.City);
        // 城市清單
        sp = (Spinner) findViewById(R.id.spinner);

        // 城市選擇清單
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] CityArray = {"請選擇城市", "臺北市", "新北市", "桃園市", "臺中市", "臺南市", "高雄市", "基隆市",
                "新竹縣", "新竹市", "苗栗縣", "彰化縣", "南投縣", "雲林縣", "嘉義縣", "屏東縣", "宜蘭縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(weather.this,
                android.R.layout.simple_spinner_dropdown_item,
                CityArray);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(weather.this, "你選的是" + lunch[position], Toast.LENGTH_SHORT).show();
                // 取得選擇地點
                Where = (String) sp.getSelectedItem();
                // 顯示選擇地點
                City.setText(Where);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 讀取各縣市一週天氣預報
        new WeekTask ().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-005?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");
    }

    //  取得伺服端傳來回應
    class WeekTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder ();
            try {
                URL url = new URL ( params[0] );
                BufferedReader in = new BufferedReader (
                        new InputStreamReader ( url.openStream () ) );
                String line = in.readLine ();
                while (line != null) {
                    Log.d ( "HTTP", line );
                    result.append ( line );
                    line = in.readLine ();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
            return result.toString ();
        }

        protected void onPostExecute(String data) {
            super.onPostExecute ( data );
            Log.d ( "JSON", data );
            parseJSON ( data );
        }

        private void parseJSON(String data)  {

            try{
                //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
                JSONObject Object = new JSONObject(data);
                Log.d("object","Obj = " + Object);
                JSONArray array = Object.getJSONArray ("location");
                Log.d("array", "Array = " + array);
                for (int i = 0; i < array .length (); i++) {
                    JSONObject JsonObject = array.getJSONObject(i);
                    Log.d("jsonObject", "json = " + JsonObject);
                    String locationName = JsonObject.getString("locationName");
                    Log.d("TAG", "城市：:" + locationName);
                }

//                JSONArray JsonArray  = new JSONArray (data);
//                Log.d("Array", "array = " + JsonArray);
//                JSONObject jsonObject = new JSONObject(data);
//                Log.d("object", "object = " + jsonObject);
//                // 縣市
//                String locationName = jsonObject.getString("location");
//                Log.d("City", "City ：" + locationName);
//                // 天氣因子:Wx, PoP, CI, MinT, MaxT
//                String elementName = jsonObject.getString("elementName");
//                Log.d("elementName", "天氣因子 ：" + elementName);
//                // 降雨機率
//                String parameter = jsonObject.getString("parameter");
//                Log.d("parameter", "降雨機率 ：" + parameter);
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
