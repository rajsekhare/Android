package com.raj.zoho.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.raj.zoho.network.model.Countries;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CountriesDao {
    @Query("SELECT * FROM countries")
    LiveData<List<Countries>> getAll();

    @Insert(onConflict = REPLACE)
    void insertAll(Countries... countries);

    @Delete
    void delete(Countries user);

    @Insert(onConflict = REPLACE)
    void insertList(List<Countries> body);

    @Query("SELECT * FROM countries where name collate SQL_Latin1_General_CP1_CI_AS like :name")
    LiveData<List<Countries>> getCountryDetail(String name);

    @Query("SELECT * FROM countries where name like :name COLLATE NOCASE")
    Countries getCountryDetailNoLiveData(String name);

}
