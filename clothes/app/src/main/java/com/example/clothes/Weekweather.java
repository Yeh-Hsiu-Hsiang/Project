package com.example.clothes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Weekweather extends AppCompatActivity {

    public Button ReadCity;
    public TextView Json; // 用于展示读取xml的内容
    public TextView City; // 查詢城市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Log.d ( "after", "onCreate");
        setContentView( R.layout.activity_weekweather );
        Log.d ( "ok", "setContentView");

        // 城市按鈕
        ReadCity = (Button) findViewById(R.id.enter);
        City = (TextView) findViewById(R.id.CityName);
        Json = (TextView) findViewById(R.id.json);

        // 讀取一般天氣預報-今明36小時天氣預報
        new TransTask ().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-003?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");
    }

    // 點擊按鈕顯示文字
    public void Click(View view) {
        String input = City.getText().toString();
        Json.setText(input);
    }

    class TransTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder ();
            try {
                URL url = new URL ( params[0] );
                BufferedReader in = new BufferedReader (
                        new InputStreamReader ( url.openStream () ) );
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

        protected void onPostExecute(String s) {
            super.onPostExecute ( s );
            Log.d ( "JSON", s );
            parseJSON ( s );
        }

        private void parseJSON(String s) {
        }

        public void main(String[] args) {
            try {
                //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
                String jsonText = null;
                JSONArray DataArray  = new JSONArray ( jsonText );
                for (int i = 0; i < DataArray .length (); i++) {
                    // 獲取 DataArray 陣列的第 i 個 json 物件
                    JSONObject file = DataArray .getJSONObject ( i );
                    // 縣市
                    String locationName = file.getString ( "locationName" );
                    // 天氣因子:Wx, PoP, CI, MinT, MaxT
                    String elementName = file.getString ( "elementName" );
                    // 降雨機率
                    String parameter = file.getString ( "parameter" );
                    if (elementName == "PoP") {
                        String info = file.getString ( "info" );
                        Log.d ( "TAG", "降雨機率：" + parameter + "%" );
                        Log.d ( "info", "otherInfo" + info );
                        Log.d ( "TAG", "縣市名稱：" + locationName + "elementName:" + elementName + "降雨機率：" + parameter + "%");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace ();
            }
        }
    }

    // 回到主頁按鈕
    public void toHome(View view) {
                Intent intent = new Intent();
                intent.setClass(Weekweather.this  , MainActivity.class);
                startActivity(intent);
                Weekweather.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, Weekweather.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }
}
