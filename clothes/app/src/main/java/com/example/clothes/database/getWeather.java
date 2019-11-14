package com.example.clothes.database;

import java.io.Serializable;

public class getWeather implements Serializable {
    private  Long id;
    private  String CityName, NowCity;
    private  String Day, T_Day, WD_Day, PoP_Day;
    private  String Hour, T_Hour, WD_Hour;
    private  String Temperature;
    private  String PoPh;
    private  String WeatherDescription;
    private  String Threehour_Description;


    public getWeather() {
    }

    public getWeather(Long id, String CityName, String NowCity,String Day, String T_Day, String WD_Day, String PoP_Day,
                      String Hour, String T_Hour, String WD_Hour, String Temperature, String PoPh , String WeatherDescription, String Threehour_Description) {
        this.id = id;
        this.CityName = CityName;
        this.NowCity = NowCity;
        this.Day = Day;
        this.T_Day = T_Day;
        this.WD_Day = WD_Day;
        this.PoP_Day = PoP_Day;
        this.Hour = Hour;
        this.T_Hour = T_Hour;
        this.WD_Hour = WD_Hour;
        this.Temperature = Temperature;
        this.PoPh = PoPh;
        this.WeatherDescription = WeatherDescription;
        this.Threehour_Description = Threehour_Description;
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

    public String getNowCity() {
        return NowCity;
    }
    public void setNowCity(String NowCity) {
        this.NowCity = NowCity;
    }

    public String getDay() {
        return Day;
    }
    public void setDay(String Day) {
        this.Day = Day;
    }

    public String getT_Day() {
        return T_Day;
    }
    public void setT_Day(String T_Day) {
        this.T_Day = T_Day;
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
    public void setPoP_Day(String PoP_Day) { this.PoP_Day = PoP_Day; }

    public String getHour() {
        return Hour;
    }
    public void setHour(String Hour) {
        this.Hour = Hour;
    }

    public String getT_Hour() {
        return T_Hour;
    }
    public void setT_Hour(String T_Hour) {
        this.T_Hour = T_Hour;
    }

    public String getWD_Hour() {
        return WD_Hour;
    }
    public void setWD_Hour(String WD_Hour) {
        this.WD_Hour = WD_Hour;
    }

    public String getTemperature() {
        return Temperature;
    }
    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
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
    public void setWeatherDescription(String WeatherDescription) { this.WeatherDescription = WeatherDescription;}

    public String getThreehour_Description(){return Threehour_Description;}
    public void setThreehour_Description(String threehour_Description){this.Threehour_Description = threehour_Description;}
}
