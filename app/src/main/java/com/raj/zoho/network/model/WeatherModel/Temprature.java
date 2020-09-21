package com.raj.zoho.network.model.WeatherModel;

public class Temprature {
    private String rain;

    private String visibility;

    private String timezone;

    private String main;

    private String clouds;

    private String sys;

    private String dt;

//    private Coord coord;

    private Weather[] weather;

    private String name;

    private String cod;

    private String id;

    private String base;

    private String wind;

    public String getRain ()
    {
        return rain;
    }

    public void setRain (String rain)
    {
        this.rain = rain;
    }

    public String getVisibility ()
    {
        return visibility;
    }

    public void setVisibility (String visibility)
    {
        this.visibility = visibility;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getMain ()
    {
        return main;
    }

    public void setMain (String main)
    {
        this.main = main;
    }

    public String getClouds ()
    {
        return clouds;
    }

    public void setClouds (String clouds)
    {
        this.clouds = clouds;
    }

    public String getSys ()
    {
        return sys;
    }

    public void setSys (String sys)
    {
        this.sys = sys;
    }

    public String getDt ()
    {
        return dt;
    }

    public void setDt (String dt)
    {
        this.dt = dt;
    }

//    public Coord getCoord ()
//    {
//        return coord;
//    }
//
//    public void setCoord (Coord coord)
//    {
//        this.coord = coord;
//    }

    public Weather[] getWeather ()
    {
        return weather;
    }

    public void setWeather (Weather[] weather)
    {
        this.weather = weather;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCod ()
    {
        return cod;
    }

    public void setCod (String cod)
    {
        this.cod = cod;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBase ()
    {
        return base;
    }

    public void setBase (String base)
    {
        this.base = base;
    }

    public String getWind ()
    {
        return wind;
    }

    public void setWind (String wind)
    {
        this.wind = wind;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rain = "+rain+", visibility = "+visibility+", timezone = "+timezone+", main = "+main+", clouds = "+clouds+", sys = "+sys+", dt = "+dt+", weather = "+weather+", name = "+name+", cod = "+cod+", id = "+id+", base = "+base+", wind = "+wind+"]";
    }
}
