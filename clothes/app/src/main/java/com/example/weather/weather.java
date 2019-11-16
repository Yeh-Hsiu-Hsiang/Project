package com.example.weather;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clothes.MainActivity;
import com.example.clothes.R;
import com.example.clothes.database.WeekWeatherDAO;
import com.example.clothes.database.getWeather;
import com.example.clothes.database.getWeekWeather;
import com.example.clothes.database.weatherDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class weather extends AppCompatActivity {
    // 宣告資料庫功能類別欄位變數
    private WeekWeatherDAO dao_Week;
    private weatherDAO dao_Today;
    long dbcount = 1;
    getWeekWeather getWeekWeather = new getWeekWeather();

    public TextView Location, week_location; // 顯示城市
    public TextView Temperature, LowTemperature, HighTemperature; // 顯示溫度
    public TextView WeatherDescription, WeatherDescription_array; // 顯示天氣敘述
    public TextView Today_date, Today_Time, WD_Day, PoP_Day, MaxT_Day, MaxT_Hour, MinT_Day; // 顯示日期時間
    public ImageView weather_image, MON_image, TUE_image, WED_image, THU_image, FRI_image, SAT_image, SUN_image;
    public TextView MON_Min, TUE_Min, WED_Min, THU_Min, FRI_Min, SAT_Min, SUN_Min;
    public TextView MON_Max, TUE_Max, WED_Max, THU_Max, FRI_Max, SAT_Max, SUN_Max;
    public TextView week_PoP, PoPh, MON_PoP, TUE_PoP, WED_PoP, THU_PoP, FRI_PoP, SAT_PoP, SUN_PoP; // 降雨機率
    public ArrayList<String> locationList, WD_Day_list, PoP_Day_list, MaxT_Day_list, WeatherDescription_array_list, week_PoP_list, HighTemperature_list, MinT_Day_list, LowTemperature_list, MaxT_Hour_list;
    public ArrayList<getWeather> dao_Today_list;
    private HorizontalScrollView scrollView;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekweather);
        dao_Week = new WeekWeatherDAO(getApplicationContext());
        dao_Today = new weatherDAO(getApplicationContext());
        locationList = new ArrayList<String>();
        WD_Day_list = new ArrayList<String>();
        PoP_Day_list = new ArrayList<String>();
        MaxT_Day_list = new ArrayList<String>();
        WeatherDescription_array_list = new ArrayList<String>();
        week_PoP_list = new ArrayList<String>();
        HighTemperature_list = new ArrayList<String>();
        MinT_Day_list = new ArrayList<String>();
        LowTemperature_list = new ArrayList<String>();
        MaxT_Hour_list = new ArrayList<String>();
        scrollView = (HorizontalScrollView) this.findViewById(R.id.scroll_view);
        linear = (LinearLayout) this.findViewById(R.id.linear);
        // 顯示所在位置
        Location = (TextView) findViewById(R.id.City);
        week_location = (TextView) findViewById(R.id.week_location);
        // 顯示最高最低溫
        Temperature = (TextView) findViewById(R.id.Temperature);
        HighTemperature = (TextView) findViewById(R.id.HighTemperature);
        LowTemperature = (TextView) findViewById(R.id.LowTemperature);
        PoPh = (TextView) findViewById(R.id.Rainfall_probability);
        week_PoP = (TextView) findViewById(R.id.week_PoP);
        WeatherDescription = (TextView) findViewById(R.id.weather);
        WeatherDescription_array = (TextView) findViewById(R.id.WeatherDescription_array);
        Today_date = (TextView) findViewById(R.id.Today_date);
        Today_Time = (TextView) findViewById(R.id.Today_Time);
        WD_Day = (TextView) findViewById(R.id.WD_Day);
        PoP_Day = (TextView) findViewById(R.id.PoP_Day);
        MaxT_Day = (TextView) findViewById(R.id.MaxT_Day);
        MinT_Day = (TextView) findViewById(R.id.MinT_Day);
        MaxT_Hour = (TextView) findViewById(R.id.MaxT_Hour);
        weather_image = (ImageView) findViewById(R.id.weather_image);
        MON_image = (ImageView) findViewById(R.id.MON_image);
        MON_Min = (TextView) findViewById(R.id.MON_Min);
        MON_Max = (TextView) findViewById(R.id.MON_Max);
        MON_PoP = (TextView) findViewById(R.id.MON_PoP);
        TUE_image = (ImageView) findViewById(R.id.TUE_image);
        TUE_Min = (TextView) findViewById(R.id.TUE_Min);
        TUE_Max = (TextView) findViewById(R.id.TUE_Max);
        TUE_PoP = (TextView) findViewById(R.id.TUE_PoP);
        WED_image = (ImageView) findViewById(R.id.WED_image);
        WED_Min = (TextView) findViewById(R.id.WED_Min);
        WED_Max = (TextView) findViewById(R.id.WED_Max);
        WED_PoP = (TextView) findViewById(R.id.WED_PoP);
        THU_image = (ImageView) findViewById(R.id.THU_image);
        THU_Min = (TextView) findViewById(R.id.THU_Min);
        THU_Max = (TextView) findViewById(R.id.THU_Max);
        THU_PoP = (TextView) findViewById(R.id.THU_PoP);
        FRI_image = (ImageView) findViewById(R.id.FRI_image);
        FRI_Min = (TextView) findViewById(R.id.FRI_Min);
        FRI_Max = (TextView) findViewById(R.id.FRI_Max);
        FRI_PoP = (TextView) findViewById(R.id.FRI_PoP);
        SAT_image = (ImageView) findViewById(R.id.SAT_image);
        SAT_Min = (TextView) findViewById(R.id.SAT_Min);
        SAT_Max = (TextView) findViewById(R.id.SAT_Max);
        SAT_PoP = (TextView) findViewById(R.id.SAT_PoP);
        SUN_image = (ImageView) findViewById(R.id.SUN_image);
        SUN_Min = (TextView) findViewById(R.id.SUN_Min);
        SUN_Max = (TextView) findViewById(R.id.SUN_Max);
        SUN_PoP = (TextView) findViewById(R.id.SUN_PoP);
        Today_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Today_Time.setText(new SimpleDateFormat("HH").format(new Date()));

        // 讀取各縣市一週天氣預報
        new WeekTask().execute("https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-D0047-091?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON");

        String city = dao_Today.getoneID(1).getNowCity();
        Location.setText(city);
        dao_Today_list = dao_Today.getWDweather(Location.getText().toString());
        String Description;
        String[] Description_array;

        for(int y = 0 ; y < dao_Today.getWDweather(city).size() ; y++){
            if(dao_Today.getWDweather(city).get(0).getDay().equals(dao_Today.getWDweather(city).get(y).getT_Day()) &&
                    dao_Today.getWDweather(city).get(0).getHour().equals(dao_Today.getWDweather(city).get(y).getT_Hour())){
                Temperature.setText(dao_Today.getWDweather(city).get(y).getTemperature() + " °C ");
                Description = dao_Today_list.get(y).getWeatherDescription();
                Description_array = Description.split("。");
                WeatherDescription.setText(Description_array[0] + "\n" + "\n" + Description_array[1] + "\n" + "\n" + Description_array[2] + "\n" + "\n" + Description_array[3] + "\n" + "\n" + Description_array[4] + "\n" + "\n" + Description_array[5]);
                PoPh.setText(dao_Today_list.get(y).getPoPh() + " % ");
                if(Description_array[0].equals("晴") || Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")){
                    weather_image.setImageDrawable(getResources().getDrawable( R.drawable.sunny));
                }else if(Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲") || Description_array[0].contains("陰")){
                    weather_image.setImageDrawable(getResources().getDrawable( R.drawable.cloudybackground));
                }else if(Description_array[0].contains("雨")){
                    weather_image.setImageDrawable(getResources().getDrawable( R.drawable.rainy));
                }else if(Description_array[0].contains("雷") || Description_array[0].contains("雷") && Description_array[0].contains("雨")){
                    weather_image.setImageDrawable(getResources().getDrawable( R.drawable.lightning));
                }else if(Description_array[0].contains("雪")){
                    weather_image.setImageDrawable(getResources().getDrawable( R.drawable.snowy));
                }else if(Description_array[0].contains("霧")){
                    weather_image.setImageDrawable(getResources().getDrawable( R.drawable.windy));
                }
                break;
            }
        }

        for (int n = 0; n < 8; n++) {
            String[] threehour_array = dao_Today_list.get(n).getThreehour_Description().split("。");
            LinearLayout.LayoutParams linearLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout myLinear = new LinearLayout(this);
            linearLp.setMargins(30, 30, 30, 30);
            myLinear.setOrientation(LinearLayout.VERTICAL);
            myLinear.setTag(n);
            linear.addView(myLinear, linearLp);
            // 小時
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(150, 150);
            TextView textView = new TextView(this);
            textView.setText(dao_Today_list.get(n).getWD_Hour() + ": 00");
            textView.setGravity(Gravity.CENTER);
            myLinear.addView(textView, textViewLp);
            // 天氣圖示
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            ImageView imageView = new ImageView(this);
            if(threehour_array[0].equals("晴")){
                imageView.setBackgroundResource(R.drawable.sun);
            }else if(threehour_array[0].equals("晴時多雲") || threehour_array[0].equals("多雲時晴")){
                imageView.setBackgroundResource(R.drawable.sunpartlyclear);
            }else if(threehour_array[0].contains("多雲") || threehour_array[0].contains("多雲時陰") || threehour_array[0].contains("陰時多雲")){
                imageView.setBackgroundResource(R.drawable.mostlycloudy);
            }else if(threehour_array[0].contains("陰") ){
                imageView.setBackgroundResource(R.drawable.cloudy);
            }else if(threehour_array[0].contains("雨") ){
                imageView.setBackgroundResource(R.drawable.rain);
            }else if(threehour_array[0].equals("多雲陣雨") || threehour_array[0].equals("多雲短暫雨") || threehour_array[0].equals("多雲短暫陣雨") || threehour_array[0].equals("多雲時陰短暫雨") || threehour_array[0].equals("多雲時陰短暫陣雨") || threehour_array[0].equals("陰時多雲短暫雨") || threehour_array[0].equals("陰時多雲短暫陣雨") || threehour_array[0].equals("陰時多雲有雨") || threehour_array[0].equals("陰時多雲有陣雨") || threehour_array[0].equals("陰時多雲陣雨")){
                imageView.setBackgroundResource(R.drawable.cloudywithrain);
            }else if(threehour_array[0].equals("多雲時晴短暫陣雨") || threehour_array[0].equals("多雲時晴短暫雨") || threehour_array[0].equals("晴時多雲短暫陣雨") || threehour_array[0].equals("晴短暫陣雨") || threehour_array[0].equals("晴午後陰短暫雨") || threehour_array[0].equals("晴午後陰短暫陣雨") || threehour_array[0].equals("晴時多雲陣雨") || threehour_array[0].equals("多雲時晴陣雨")){
                imageView.setBackgroundResource(R.drawable.afternooncloudywithshowers);
            } else if (threehour_array[0].contains("雷") && threehour_array[0].contains("雨") || threehour_array[0].contains("雷")) {
                imageView.setBackgroundResource(R.drawable.thunderstorms);
            }else if(threehour_array[0].contains("霧")){
                imageView.setBackgroundResource(R.drawable.fog);
            }else if(threehour_array[0].contains("雪")){
                imageView.setBackgroundResource(R.drawable.snow);
            }
            imageView.setScaleType(ImageView.ScaleType. CENTER_CROP);
            myLinear.addView(imageView, lp);

            // 溫度
            LinearLayout.LayoutParams textTem = new LinearLayout.LayoutParams(150, 150);
            TextView threehour_Tem = new TextView(this);
            threehour_Tem.setText(threehour_array[2].substring(4,6) + "°C");
            threehour_Tem.setGravity(Gravity.CENTER);
            myLinear.addView(threehour_Tem, textTem);
        }
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
            parseJSON ( week_data );
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
                    week_location.setText(locationName);
                    locationList.add(week_location.getText().toString());
                }
                for (int z = 0; z < location_array .length (); z++) {
                    JSONObject JsonObject = location_array.getJSONObject(z);
                    String locationName = JsonObject.getString("locationName");
                    for (int citycount = 0; citycount < locationList.size(); citycount++) {
                        if (locationName.equals(locationList.get(citycount))) {
                            JSONArray weatherElement = JsonObject.getJSONArray("weatherElement");
                            for (int j = 0; j < weatherElement.length(); j++) {
                                JSONObject jsonObject2 = weatherElement.getJSONObject(j);
                                String elementName = jsonObject2.getString("elementName");
                                JSONArray time = jsonObject2.getJSONArray("time");
                                switch (elementName) {
                                    // 天氣現象
                                    case "WeatherDescription":
                                        for (int k = 0; k < time.length(); k++) {
                                            JSONObject jsonObject3 = time.getJSONObject(k);
                                            String startTime = jsonObject3.getString("startTime");
                                            String WD_startTime = startTime.substring(0, 10);
                                            WD_Day.setText(WD_startTime);
                                            WD_Day_list.add(WD_Day.getText().toString());
                                            JSONObject WDelementValue = jsonObject3.getJSONObject("elementValue");
                                            String value = WDelementValue.getString("value");
                                            WeatherDescription_array.setText(value);
                                            WeatherDescription_array_list.add(WeatherDescription_array.getText().toString());
                                            String[] Description_array = value.split("。");
                                            switch (getWeek(WD_startTime)) {
                                                case "天":
                                                    if (Description_array[0].equals("晴")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        SUN_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
                                                case "一":
                                                    if (Description_array[0].equals("晴")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        MON_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
                                                case "二":
                                                    if (Description_array[0].equals("晴")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        TUE_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
                                                case "三":
                                                    if (Description_array[0].equals("晴")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        WED_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
                                                case "四":
                                                    if (Description_array[0].equals("晴")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        THU_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
                                                case "五":
                                                    if (Description_array[0].equals("晴")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].contains("短暫陣雨") || Description_array[0].contains("陣雨") || Description_array[0].contains("短暫雨") || Description_array[0].contains("雨")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].contains("晴午後") || Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        FRI_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
                                                case "六":
                                                    if (Description_array[0].equals("晴")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                                    } else if (Description_array[0].equals("晴時多雲") || Description_array[0].equals("多雲時晴")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.sunpartlyclear));
                                                    } else if (Description_array[0].equals("多雲") || Description_array[0].equals("多雲時陰") || Description_array[0].equals("陰時多雲")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.mostlycloudy));
                                                    } else if (Description_array[0].contains("陰")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                                                    } else if (Description_array[0].equals("多雲陣雨") || Description_array[0].equals("多雲短暫雨") || Description_array[0].equals("多雲短暫陣雨") || Description_array[0].equals("多雲時陰短暫雨") || Description_array[0].equals("多雲時陰短暫陣雨") || Description_array[0].equals("陰時多雲短暫雨") || Description_array[0].equals("陰時多雲短暫陣雨") || Description_array[0].equals("陰時多雲有雨") || Description_array[0].equals("陰時多雲有陣雨") || Description_array[0].equals("陰時多雲陣雨")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.cloudywithrain));
                                                    } else if (Description_array[0].equals("多雲時晴短暫陣雨") || Description_array[0].equals("多雲時晴短暫雨") || Description_array[0].equals("晴時多雲短暫陣雨") || Description_array[0].equals("晴短暫陣雨") || Description_array[0].equals("晴午後陰短暫雨") || Description_array[0].equals("晴午後陰短暫陣雨") || Description_array[0].equals("晴時多雲陣雨") || Description_array[0].equals("多雲時晴陣雨")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.afternooncloudywithshowers));
                                                    } else if (Description_array[0].contains("雷") && Description_array[0].contains("雨") || Description_array[0].contains("雷")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorms));
                                                    } else if (Description_array[0].contains("霧")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                                                    } else if (Description_array[0].contains("雪")) {
                                                        SAT_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                                                    }
                                                    break;
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
                                            if (value == "null") {
                                                value = "0";
                                            }
                                            week_PoP.setText(value + " % ");
                                            week_PoP_list.add(week_PoP.getText().toString());
                                            switch (getWeek(PoP_startTime)) {
                                                case "天":
                                                    SUN_PoP.setText(value + " % ");
                                                    break;
                                                case "一":
                                                    MON_PoP.setText(value + " % ");
                                                    break;
                                                case "二":
                                                    TUE_PoP.setText(value + " % ");
                                                    break;
                                                case "三":
                                                    WED_PoP.setText(value + " % ");
                                                    break;
                                                case "四":
                                                    THU_PoP.setText(value + " % ");
                                                    break;
                                                case "五":
                                                    FRI_PoP.setText(value + " % ");
                                                    break;
                                                case "六":
                                                    SAT_PoP.setText(value + " % ");
                                                    break;
                                            }
                                        }
                                        break;
                                    case "MaxT":
                                        for (int k = 0; k < time.length(); k++) {
                                            JSONObject jsonObject3 = time.getJSONObject(k);
                                            String startTime = jsonObject3.getString("startTime");
                                            String MaxT_startTime = startTime.substring(0, 10);
                                            MaxT_Hour.setText(startTime.substring(11, 13));
                                            MaxT_Hour_list.add(MaxT_Hour.getText().toString());
                                            MaxT_Day.setText(MaxT_startTime);
                                            MaxT_Day_list.add(MaxT_Day.getText().toString());
                                            JSONObject elementValue = jsonObject3.getJSONObject("elementValue");
                                            String value = elementValue.getString("value");
                                            switch (getWeek(MaxT_startTime)) {
                                                case "天":
                                                    SUN_Max.setText(value + " °C ");
                                                    break;
                                                case "一":
                                                    MON_Max.setText(value + " °C ");
                                                    break;
                                                case "二":
                                                    TUE_Max.setText(value + " °C ");
                                                    break;
                                                case "三":
                                                    WED_Max.setText(value + " °C ");
                                                    break;
                                                case "四":
                                                    THU_Max.setText(value + " °C ");
                                                    break;
                                                case "五":
                                                    FRI_Max.setText(value + " °C ");
                                                    break;
                                                case "六":
                                                    SAT_Max.setText(value + " °C ");
                                                    break;
                                            }
                                            HighTemperature.setText(value);
                                            HighTemperature_list.add(HighTemperature.getText().toString());
                                        }
                                        break;
                                    case "MinT":
                                        for (int k = 0; k < time.length(); k++) {
                                            JSONObject jsonObject3 = time.getJSONObject(k);
                                            String startTime = jsonObject3.getString("startTime");
                                            String MinT_startTime = startTime.substring(0, 10);
                                            MinT_Day.setText(MinT_startTime);
                                            MinT_Day_list.add(MinT_Day.getText().toString());
                                            JSONObject elementValue = jsonObject3.getJSONObject("elementValue");
                                            String value = elementValue.getString("value");
                                            switch (getWeek(MinT_startTime)) {
                                                case "天":
                                                    SUN_Min.setText(value + " °C ");
                                                    break;
                                                case "一":
                                                    MON_Min.setText(value + " °C ");
                                                    break;
                                                case "二":
                                                    TUE_Min.setText(value + " °C ");
                                                    break;
                                                case "三":
                                                    WED_Min.setText(value + " °C ");
                                                    break;
                                                case "四":
                                                    THU_Min.setText(value + " °C ");
                                                    break;
                                                case "五":
                                                    FRI_Min.setText(value + " °C ");
                                                    break;
                                                case "六":
                                                    SAT_Min.setText(value + " °C ");
                                                    break;
                                            }
                                            LowTemperature.setText(value);
                                            LowTemperature_list.add(LowTemperature.getText().toString());
                                        }
                                        break;
                                }
                            }
                        }
                        CompleteAdd(citycount);
                    }
                }
                dbcount = 1;
                setTemperatureText();
            }catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTemperatureText(){
        Integer current_Time = Integer.valueOf(Today_Time.getText().toString()).intValue();
        String S_current_Time = "";

        if(current_Time >= 6 && current_Time < 18){
            if(dao_Week.getWDweather(Location.getText().toString()).get(0).getHour().equals("12"))
                S_current_Time = "12";
            else
                S_current_Time = "06";
        }else{
            if(dao_Week.getWDweather(Location.getText().toString()).get(0).getHour().equals("00"))
                S_current_Time = "00";
            else
                S_current_Time = "18";
        }
        for(int y = 0 ; y < dao_Week.getWDweather(Location.getText().toString()).size() ; y++) {
            if (dao_Week.getWDweather(Location.getText().toString()).get(y).getDay().equals(dao_Week.getWDweather(Location.getText().toString()).get(y).getMaxT_Day()) &&
                    dao_Week.getWDweather(Location.getText().toString()).get(y).getHour().equals(S_current_Time)) {
                LowTemperature.setText("L" + dao_Week.getWDweather(Location.getText().toString()).get(y).getLowTemperature() + "°");
                HighTemperature.setText("H" + dao_Week.getWDweather(Location.getText().toString()).get(y).getHighTemperature() + "°");
                break;
            }
        }
    }

    private String getWeek(String pTime) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    //整理進入DB
    public void CompleteAdd(int citycount){
        getWeekWeather.setDay(Today_date.getText().toString());
        getWeekWeather.setCityName(locationList.get(citycount));

        for (int a = 0; a < WD_Day_list.size(); a++) {
            getWeekWeather.setWD_Day(WD_Day_list.get(a));
            getWeekWeather.setWeatherDescription(WeatherDescription_array_list.get(a));
            getWeekWeather.setPoP_Day(PoP_Day_list.get(a));
            getWeekWeather.setPoPh(week_PoP_list.get(a));
            getWeekWeather.setMaxT_Day(MaxT_Day_list.get(a));
            getWeekWeather.setHour(MaxT_Hour_list.get(a));
            getWeekWeather.setHighTemperature(HighTemperature_list.get(a));
            getWeekWeather.setMinT_Day(MinT_Day_list.get(a));
            getWeekWeather.setLowTemperature(LowTemperature_list.get(a));

            String[] Tdaylist_array,Today_date_array;
            Integer Tdaylist_Y ,Tdaylist_MM ,Tdaylist_DD ,Today_date_Y ,Today_date_MM ,Today_date_DD;
            Tdaylist_array = MaxT_Day_list.get(0).split("-");
            Today_date_array =Today_date.getText().toString().split("-");
            Tdaylist_Y = Integer.valueOf(Tdaylist_array[0]);
            Tdaylist_MM = Integer.valueOf(Tdaylist_array[1]);
            Tdaylist_DD = Integer.valueOf(Tdaylist_array[2]);
            Today_date_Y = Integer.valueOf(Today_date_array[0]);
            Today_date_MM = Integer.valueOf(Today_date_array[1]);
            Today_date_DD = Integer.valueOf(Today_date_array[2]);

            if(dao_Week.getCount() != 330){
                dao_Week.insert(getWeekWeather);
            }else{
                if(Today_date_Y >= Tdaylist_Y && Today_date_MM >= Tdaylist_MM && Today_date_DD >= Tdaylist_DD) {
                    getWeekWeather.setId(dbcount);
                    dao_Week.update(getWeekWeather);
                    dbcount++;
                }
            }
        }
        WD_Day_list = new ArrayList<String>();
        WeatherDescription_array_list = new ArrayList<String>();
        PoP_Day_list = new ArrayList<String>();
        week_PoP_list = new ArrayList<String>();
        MaxT_Day_list = new ArrayList<String>();
        HighTemperature_list = new ArrayList<String>();
        MinT_Day_list = new ArrayList<String>();
        LowTemperature_list = new ArrayList<String>();
        MaxT_Hour_list = new ArrayList<String>();
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
