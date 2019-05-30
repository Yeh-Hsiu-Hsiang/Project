package com.example.clothes;

import java.util.List;

public class WeatherSample {
    /**
     * 一个简单的应用
     *
     * @param args
     */
    public static void main(String[] args) {
        // 获取成都的天气预报信息。
        String city = "海南";
        System.out.println(city + "未来7天天氣預報訊息：");
        List<Weekweather> listReport = new WeatherUtil()
                .getWeatherReports(city);
        if (listReport.size() < 1) {
            System.out.println("沒有找到 " + city + " 的天氣預報。");
        } else {
            for (Weekweather report : listReport) {
                System.out.println(report);
            }
        }
    }

}
