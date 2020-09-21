package com.raj.zoho.network;

import com.raj.zoho.network.model.WeatherModel.MainWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
        //https://api.openweathermap.org/data/2.5/onecall?lat=11.786253&lon=77.800781&%20exclude=daily&appid=1fdefb16fa27c6199d6f19c718b3f636
        String BASE_URL = "https://api.openweathermap.org/data/2.5/";
        @GET("onecall")
        Call<MainWeather> getWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String api);

}
