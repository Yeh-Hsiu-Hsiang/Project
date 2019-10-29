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
            StringBuilder today_sb = new StringBuilder ();
            try {
                URL url = new URL ( params[0] );
                BufferedReader in = new BufferedReader (new InputStreamReader ( url.openStream () ) );
                String line = in.readLine ();
                while (line != null) {
                    Log.d ( "HTTP", line );
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
            Log.d ( "JSON", today_data );
            parseJSON ( today_data );
        }

        private void parseJSON(String today_data)  {
            Log.d("today_data","today_data = " + today_data);
            JSONObject Ob;
            try{
                Ob = new JSONObject(today_data);
                Object jsonOb = Ob.getJSONObject("cwbopendata").get("dataset");
                Log.d("dataset","Object = " + jsonOb);

                JSONArray array = Ob.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONArray ("location");
                Log.d("array", "array = " + array);

                for (int i = 0; i < array .length (); i++) {
                    JSONObject JsonObject = array.getJSONObject(i);
                    Log.d("jsonObject", "json = " + JsonObject);
                    String locationName = JsonObject.getString("locationName");
                    Log.d("TAG", "城市：:" + locationName);
                }
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }
}