package com.example.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.clothes.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class test extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_weekweather);
        new TransTask().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-003?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");
    }

    //    private static final String GET_CWB_OPENDATA_REST_URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-003?" + "locationName={locationName}&elementName={elementName}&sort={sort}&startTime={startTime}&timeFrom={timeFrom}&timeTo={timeTo";
//    //  Opendata 會員授權碼名稱
//    private static final String AUTHORIZATION_KEY = "Authorization";
//    // Opendata 會員授權碼
//    private static final String AUTHORIZATION_VALUE = "CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22";
//    public void cwbOpendataRestClient(Client client){}
//        final Response response = client.target(GET_CWB_OPENDATA_REST_URL).request().header(AUTHORIZATION_KEY, AUTHORIZATION_VALUE).get();
//        String output = response.readEntity(String.class);
//        client.close();

    class TransTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while(line!=null){
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }
        private void parseJSON(String s) {
        }


        public void main(String[] args) {
            try {
                //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
                String jsonText = null;
                JSONArray array = new JSONArray ( jsonText );
                for (int i = 0; i < array.length (); i++) {
                    JSONObject jsonObject = array.getJSONObject ( i );
                    String locationName = jsonObject.getString ( "locationName" );
                    String parameterName = jsonObject.getString ( "parameterName" );
                    String info = jsonObject.getString ( "info" );
                    Log.d ( "TAG", "locationName:" + locationName + ", parameterName:" + parameterName + ", info:" + info );
                }
            } catch (JSONException e) {
                e.printStackTrace ();
            }
        }
    }
}

