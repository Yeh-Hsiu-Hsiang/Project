package com.example.weather;

import android.content.Intent;
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

public class WeekWeather {

    protected void onCreate(Bundle savedInstanceState) {
        // 讀取各縣市一週天氣預報
        new WeekTask().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-005?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");
    }

    //  取得伺服端傳來回應
    class WeekTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while (line != null) {
                    Log.d("HTTP", line);
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            Log.d("JSON", data);
            parseJSON(data);
        }

        private void parseJSON(String data) {
            JSONObject Ob;
            try {
                Ob = new JSONObject(data);
                JSONArray location_array = Ob.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONArray("location");

                for (int i = 0; i < location_array.length(); i++) {
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
                        switch (elementName) {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}