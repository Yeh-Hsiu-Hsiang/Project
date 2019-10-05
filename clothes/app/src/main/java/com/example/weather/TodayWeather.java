package com.example.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TodayWeather {

    protected void onCreate(Bundle savedInstanceState) {
        // 讀取今明36小時天氣預報
        new TodayTask ().execute ( "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-003?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON" );
    }

    //  取得伺服端傳來回應
    class TodayTask extends AsyncTask<String, Void, String> {
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
    }

    public void main(String[] args) {
        try {
            //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
            String jsonText = null;
            JSONArray JsonArray  = new JSONArray ( jsonText );
            // Get all jsonObject from jsonArray
            for (int i = 0; i < JsonArray .length (); i++) {
                JSONObject JsonObject = JsonArray .getJSONObject ( i );
                // 縣市
                String locationName = JsonObject.getString ( "locationName" );
                // 天氣因子:Wx, PoP, CI, MinT, MaxT
                String elementName = JsonObject.getString ( "elementName" );
                // 降雨機率
                String parameter = JsonObject.getString ( "parameter" );
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }
    }
}