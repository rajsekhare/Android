package com.raj.zoho.database;

import android.arch.persistence.room.TypeConverter;

import com.raj.zoho.network.model.Languages;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LanguageConverter {
    @TypeConverter
    public String fromList(List<Languages> items) {
        if (items == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Languages>>() {
        }.getType();
        return gson.toJson(items, type);
    }

    @TypeConverter
    public List<Languages> toList(String itemString) {
        if (itemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Languages>>() {
        }.getType();
        return gson.fromJson(itemString, type);
    }
}
