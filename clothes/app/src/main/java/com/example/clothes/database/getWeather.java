package com.example.clothes.database;

public class getWeather {
    private  Long id;
    private  String Temperature;
    private  String HighTemperature;
    private  String LowTemperature;
    private  String PoPh;
    private  String WeatherDescription;


    public getWeather() {
    }

    public getWeather(Long id, String Temperature, String HighTemperature,
                            String LowTemperature, String PoPh ,
                            String WeatherDescription ) {
        this.id = id;
        this.Temperature = Temperature;
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

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    public String getHighTemperature() {
        return HighTemperature;
    }

    public void setHighTemperature(String name) {
        this.HighTemperature = HighTemperature;
    }

    public String getLowTemperature() {
        return LowTemperature;
    }

    public void setLowTemperature(String type) {
        this.LowTemperature = LowTemperature;
    }

    public String getPoPh() {
        return PoPh;
    }

    public void setPoPh(String style) {
        this.PoPh = PoPh;
    }

    public String getWeatherDescription() {
        return WeatherDescription;
    }

    public void setWeatherDescription(String updatetime) { this.WeatherDescription = WeatherDescription; }
}
