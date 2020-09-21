package com.raj.zoho.network.model.WeatherModel;

public class MainWeather {

    private Current current;

    private String timezone;

    private String timezone_offset;

    private Daily[] daily;

    private String lon;

    //private String[] hourly;

    //private String[] minutely;

    private String lat;

    public Current getCurrent ()
    {
        return current;
    }

    public void setCurrent (Current current)
    {
        this.current = current;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getTimezone_offset ()
    {
        return timezone_offset;
    }

    public void setTimezone_offset (String timezone_offset)
    {
        this.timezone_offset = timezone_offset;
    }

    public Daily[] getDaily ()
    {
        return daily;
    }

    public void setDaily (Daily[] daily)
    {
        this.daily = daily;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

//    public String[] getHourly ()
//    {
//        return hourly;
//    }
//
//    public void setHourly (String[] hourly)
//    {
//        this.hourly = hourly;
//    }

//    public String[] getMinutely ()
//    {
//        return minutely;
//    }
//
//    public void setMinutely (String[] minutely)
//    {
//        this.minutely = minutely;
//    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [current = "+current+", timezone = "+timezone+", timezone_offset = "+timezone_offset+", daily = "+daily+", lon = "+lon+", lat = "+lat+"]";
    }
}
