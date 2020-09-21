package com.raj.zoho.database;

import android.arch.persistence.room.Room;

import com.raj.zoho.AppApplication;

public class DataBaseHelper {
    private static CountryDataBase db;
    private final static String TAG = DataBaseHelper.class.getName();

    private DataBaseHelper() {
    }

    /***
     *
     * @return an instance of CountryDataBase
     */

    public static CountryDataBase getInstance() {
        if (db == null) {
            // To make thread safe
            synchronized (DataBaseHelper.class) {
                // check again as multiple threads
                if (db == null) {
                    db = Room.databaseBuilder(AppApplication.getContext(),
                            CountryDataBase.class, "countries_db").build();

                }
            }
        }
        return db;
    }

    public static void dbClose() {
        if (db != null && db.isOpen())
            db.close();
        db = null;
    }
}