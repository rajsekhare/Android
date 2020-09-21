package com.raj.zoho.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.raj.zoho.network.model.Countries;

@Database(entities = {Countries.class}, version = 1, exportSchema = false)
@TypeConverters({LanguageConverter.class, CurrencyConverter.class})
public abstract class CountryDataBase extends RoomDatabase {
    public abstract CountriesDao countriesDao();

}
