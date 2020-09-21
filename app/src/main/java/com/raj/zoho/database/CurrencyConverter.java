package com.raj.zoho.database;

import android.arch.persistence.room.TypeConverter;

import com.raj.zoho.network.model.Currency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CurrencyConverter {
    @TypeConverter
    public String fromList(List<Currency> items) {
        if (items == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Currency>>() {
        }.getType();
        return gson.toJson(items, type);
    }

    @TypeConverter
    public List<Currency> toList(String itemString) {
        if (itemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Currency>>() {
        }.getType();
        return gson.fromJson(itemString, type);
    }
}
