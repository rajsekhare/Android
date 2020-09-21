package com.raj.zoho.network.model.WeatherModel;

public class Rain {
    private String oneh;
    public String get1h ()
    {
        return oneh;
    }

    public void set1h (String oneh)
    {
        this.oneh = oneh;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [1h = "+oneh+"]";
    }
}
