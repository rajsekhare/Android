package com.raj.zoho.network.model;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.raj.zoho.database.CurrencyConverter;
import com.raj.zoho.database.LanguageConverter;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

@Entity
public class Countries {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    private String capital;
    @SerializedName("region")
    private String region;
    @SerializedName("subregion")
    private String subregion;
    private int population;
    private double area;
    private String flag;
    @TypeConverters(CurrencyConverter.class)
    @SerializedName("currencies")
    private List<Currency> currencies;
    @TypeConverters(LanguageConverter.class)
    @SerializedName("languages")
    private List<Languages> languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Languages> languages) {
        this.languages = languages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "Country{" +
                        "image = '" + flag + '\'' +
                        ",population = '" + population + '\'' +
                        ",name = '" + name + '\'' +
                        ",area = '" + area + '\'' +
                        ",region = '" + region + '\'' +
                        ",subregion = '" + subregion + '\'' +
                        ",capital = '" + capital + '\'' +
                        ",currencies = '" + Arrays.toString(getCurrencies().toArray()) + '\'' +
                        ",languages = '" + Arrays.toString(getLanguages().toArray()) + '\'' +

                        "}";
    }
}
