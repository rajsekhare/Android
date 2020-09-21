package com.raj.zoho.activities.home;

import android.arch.lifecycle.LiveData;

import com.raj.zoho.database.CountriesDao;
import com.raj.zoho.network.RestClient;
import com.raj.zoho.network.model.Countries;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class HomeMod {

    private final CountriesDao countriesDao;
    private final RestClient restClient;

    public HomeMod(CountriesDao countriesDao, RestClient restClient) {
        this.countriesDao = countriesDao;
        this.restClient = restClient;
    }

    public LiveData<List<Countries>> getAllCountries() {
        refreshRecipe();
        // Returns a LiveData object directly from the database.
        System.out.println("the data from datbase "+countriesDao.getAll());
        return countriesDao.getAll();
    }

    private void refreshRecipe() {

        restClient.getApiService().getAllCountries().enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {
                if (response.isSuccessful()) {
                    System.out.println("the list in model method "+response.body());
                    new Thread(() -> countriesDao.insertList(response.body())).start();
                }
                // FIXME: 02/01/19 need to improve.
            }

            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {
                // FIXME: 02/01/19 need to add on failure as well
                System.out.println("the eror is "+t);
            }
        });
        // Check for errors here.

    }
}
