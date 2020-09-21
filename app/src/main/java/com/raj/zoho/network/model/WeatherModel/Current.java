package com.raj.zoho.network.model.WeatherModel;

public class Current {
    private Rain rain;

    private String sunrise;

    private String temp;

    private String visibility;

    private String uvi;

    private String pressure;

    private String clouds;

    private String feels_like;

    private String dt;

    private String wind_deg;

    private String dew_point;

    private String sunset;

    private Weather[] weather;

    private String humidity;

    private String wind_speed;

    public Rain getRain ()
    {
        return rain;
    }

    public void setRain (Rain rain)
    {
        this.rain = rain;
    }

    public String getSunrise ()
    {
        return sunrise;
    }

    public void setSunrise (String sunrise)
    {
        this.sunrise = sunrise;
    }

    public String getTemp ()
    {
        return temp;
    }

    public void setTemp (String temp)
    {
        this.temp = temp;
    }

    public String getVisibility ()
    {
        return visibility;
    }

    public void setVisibility (String visibility)
    {
        this.visibility = visibility;
    }

    public String getUvi ()
    {
        return uvi;
    }

    public void setUvi (String uvi)
    {
        this.uvi = uvi;
    }

    public String getPressure ()
    {
        return pressure;
    }

    public void setPressure (String pressure)
    {
        this.pressure = pressure;
    }

    public String getClouds ()
    {
        return clouds;
    }

    public void setClouds (String clouds)
    {
        this.clouds = clouds;
    }

    public String getFeels_like ()
    {
        return feels_like;
    }

    public void setFeels_like (String feels_like)
    {
        this.feels_like = feels_like;
    }

    public String getDt ()
    {
        return dt;
    }

    public void setDt (String dt)
    {
        this.dt = dt;
    }

    public String getWind_deg ()
    {
        return wind_deg;
    }

    public void setWind_deg (String wind_deg)
    {
        this.wind_deg = wind_deg;
    }

    public String getDew_point ()
    {
        return dew_point;
    }

    public void setDew_point (String dew_point)
    {
        this.dew_point = dew_point;
    }

    public String getSunset ()
    {
        return sunset;
    }

    public void setSunset (String sunset)
    {
        this.sunset = sunset;
    }

    public Weather[] getWeather ()
    {
        return weather;
    }

    public void setWeather (Weather[] weather)
    {
        this.weather = weather;
    }

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    public String getWind_speed ()
    {
        return wind_speed;
    }

    public void setWind_speed (String wind_speed)
    {
        this.wind_speed = wind_speed;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rain = "+rain+", sunrise = "+sunrise+", temp = "+temp+", visibility = "+visibility+", uvi = "+uvi+", pressure = "+pressure+", clouds = "+clouds+", feels_like = "+feels_like+", dt = "+dt+", wind_deg = "+wind_deg+", dew_point = "+dew_point+", sunset = "+sunset+", weather = "+weather+", humidity = "+humidity+", wind_speed = "+wind_speed+"]";
    }
}
