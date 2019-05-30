package com.example.clothes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import static com.example.clothes.json_test.loadJson;

public class Weekweather extends AppCompatActivity {

    private Button btn1; //宣告Button
    private EditText txt1; //宣告EditText
    private TextView tv1; //宣告TextView

    public void main(String[] args) {
        String url = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/O-A0003-001?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON";
        String json = loadJson(url);
        System.out.println(json);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekweather);

        btn1 = (Button) findViewById(R.id.enter);  //取得Button
        txt1 = (EditText) findViewById(R.id.CityName); //取得EditText
        tv1 = (TextView) findViewById(R.id.json_test);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //取得EditText的輸入內容
                String content = txt1.getText().toString();
                //顯示在Debug Console
                Log.d("debug", "button click");
                //使用Toast顯示在螢幕上
                Toast.makeText(Weekweather.this, content, Toast.LENGTH_SHORT).show();
//                tv1.setText(json.get("CITY"));
            }
        });

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }
    }

    /*** 天氣預報類，天氣預報的基本屬性。* @author siqi**/
        private String city;
        private String date;
        private String weekDay;
        private String weather;
        private String temperature; /*溫度*/
        private String windDir;/*風向*/
        private String wind;/*風力*/
        private String dayOrNight;

        /***獲取天氣預報的城市。* @return*/
        public String getCity() {
            return city;
        }

        /*** 設置天氣預報的城市。** @param city*/
        public void setCity(String city) {
            this.city = city;
        }

        /***獲取天氣預報的日期，格式為"1月28日"* @return*/
        public String getDate() {
            return date;
        }

        /**
         * 設置天氣預報的日期，格式為"1月28日"
         *
         * @param date
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * 獲取天氣預報报的星期
         *
         * @return
         */
        public String getWeekDay() {
            return weekDay;
        }

        /*** 設置天氣預報的星期* @param weekDay*/
        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        /*** 獲取天氣* @return*/
        public String getWeather() {
            return weather;
        }

        /*** 設置天氣* @param weather*/
        public void setWeather(String weather) {
            this.weather = weather;
        }

        /*** 獲取溫度* @return*/
        public String getTemperature() {
            return temperature;
        }

        /*** 設置溫度* @param temperature*/
        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        /*** 獲取風向* @return*/
        public String getWindDir() {
            return windDir;
        }

        /*** 設置風向* @param windDir*/
        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        /*** 獲取風力* @return*/
        public String getWind() {
            return wind;
        }

        /*** 設置風力* @param wind*/
        public void setWind(String wind) {
            this.wind = wind;
        }

        /*** 獲取天氣預報是白天還是晚上* @return*/
        public String getDayOrNight() {
            return dayOrNight;
        }

        /*** 設置天氣預報是白天還是晚上* @param dayOrNight*/
        public void setDayOrNight(String dayOrNight) {
            this.dayOrNight = dayOrNight;
        }

        /*** 天氣預報的字串*/
        public String toString() {
            return "WeatherReport [city=" + city + ", date=" + date + ", weekDay="
                    + weekDay + ", weather=" + weather + ", temperature="
                    + temperature + ", windDir=" + windDir + ", wind=" + wind
                    + ", dayOrNight=" + dayOrNight + "]";
        }

    }

