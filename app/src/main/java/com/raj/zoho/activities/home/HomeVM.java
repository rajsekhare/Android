package com.raj.zoho.activities.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.raj.zoho.database.CountriesDao;
import com.raj.zoho.database.DataBaseHelper;
import com.raj.zoho.network.RestClient;
import com.raj.zoho.network.model.Countries;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public class HomeVM extends ViewModel {

    private LiveData<List<Countries>> countryLiveData;
    private HomeMod countryRepo;
    private CountriesDao dao;

    // Instructs Dagger 2 to provide the UserRepository parameter.
    public HomeVM() {
        dao = DataBaseHelper.getInstance().countriesDao();
        this.countryRepo = new HomeMod(dao, new RestClient());
    }


    public void init() {
        if (this.countryLiveData != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.

            return;
        }
        countryLiveData = countryRepo.getAllCountries();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println("the list in vm init else"+gson.toJson(this.countryLiveData));

    }

    public LiveData<List<Countries>> getCountryList() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println("the list in vm is method is"+gson.toJson(this.countryLiveData));
        return this.countryLiveData;
    }

}
