package com.example.clothes.database;

public class getWeather {
    private  Long id;
    private  String CityName;
    private  String Temperature;
    private  String HighTemperature;
    private  String LowTemperature;
    private  String PoPh;
    private  String WeatherDescription;


    public getWeather() {
    }

    public getWeather(Long id, String CityName, String Temperature, String HighTemperature,
                            String LowTemperature, String PoPh , String WeatherDescription ) {
        this.id = id;
        this.CityName = CityName;
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

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
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

    public void setHighTemperature(String HighTemperature) {
        this.HighTemperature = HighTemperature;
    }

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
