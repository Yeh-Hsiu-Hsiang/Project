package com.example.clothes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Weekweather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekweather);
    }

    /*** 天氣預報類，天氣預報的基本屬性。* @author siqi**/
    public class WeatherReport {
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

        /*** 獲取天氣預報是白天还是晚上* @return*/
        public String getDayOrNight() {
            return dayOrNight;
        }

        /*** 設置天氣預報是白天还是晚上* @param dayOrNight*/
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
}
