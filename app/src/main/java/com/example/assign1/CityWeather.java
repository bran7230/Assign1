package com.example.assign1;

public class CityWeather {
    public String cityName;
    public String subtitle;
    public String condition;
    public int tempC;
    public int tempF;
    public int humidity;
    public int windKph;
    public int feelsLikeC;
    public String uvIndex;

    public CityWeather(String cityName, String subtitle, String condition, int tempC, int tempF, int humidity, int windKph, int feelsLikeC, String uvIndex) {
        this.cityName = cityName;
        this.subtitle = subtitle;
        this.condition = condition;
        this.tempC = tempC;
        this.tempF = tempF;
        this.humidity = humidity;
        this.windKph = windKph;
        this.feelsLikeC = feelsLikeC;
        this.uvIndex = uvIndex;
    }
}
