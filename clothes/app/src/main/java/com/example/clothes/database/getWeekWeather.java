package com.example.clothes.database;

import java.io.Serializable;

public class getWeekWeather implements Serializable {
    private  Long id;
    private  String CityName;
    private  String Day, MaxT_Day, MinT_Day,WD_Day, PoP_Day;
    private  String Hour;
    private  String HighTemperature;
    private  String LowTemperature;
    private  String PoPh;
    private  String WeatherDescription;

    public getWeekWeather() {
    }

    public getWeekWeather(Long id, String CityName, String Day, String MaxT_Day, String MinT_Day, String WD_Day, String PoP_Day,
                      String Hour, String T_Hour, String HighTemperature,
                      String LowTemperature, String PoPh , String WeatherDescription ) {
        this.id = id;
        this.CityName = CityName;
        this.Day = Day;
        this.MaxT_Day = MaxT_Day;
        this.MinT_Day = MinT_Day;
        this.WD_Day = WD_Day;
        this.PoP_Day = PoP_Day;
        this.Hour = Hour;
        this.HighTemperature = HighTemperature;
        this.LowTemperature = LowTemperature;
        this.PoPh = PoPh;
        this.WeatherDescription = WeatherDescription;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return CityName;
    }
    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getDay() {
        return Day;
    }
    public void setDay(String Day) {
        this.Day = Day;
    }

    public String getMaxT_Day() {
        return MaxT_Day;
    }
    public void setMaxT_Day(String MaxT_Day) {
        this.MaxT_Day = MaxT_Day;
    }

    public String getMinT_Day() {
        return MinT_Day;
    }
    public void setMinT_Day(String MinT_Day) {
        this.MinT_Day = MinT_Day;
    }

    public String getWD_Day() {
        return WD_Day;
    }
    public void setWD_Day(String WD_Day) {
        this.WD_Day = WD_Day;
    }

    public String getPoP_Day() {
        return PoP_Day;
    }
    public void setPoP_Day(String PoP_Day) {
        this.PoP_Day = PoP_Day;
    }

    public String getHour() {
        return Hour;
    }
    public void setHour(String Hour) {
        this.Hour = Hour;
    }

    public String getHighTemperature() {
        return HighTemperature;
    }
    public void setHighTemperature(String HighTemperature) { this.HighTemperature = HighTemperature; }

    public String getLowTemperature() {
        return LowTemperature;
    }
    public void setLowTemperature(String LowTemperature) {
        this.LowTemperature = LowTemperature;
    }

    public String getPoPh() {
        return PoPh;
    }
    public void setPoPh(String PoPh) {
        this.PoPh = PoPh;
    }

    public String getWeatherDescription() {
        return WeatherDescription;
    }
    public void setWeatherDescription(String WeatherDescription) { this.WeatherDescription = WeatherDescription; }
}
