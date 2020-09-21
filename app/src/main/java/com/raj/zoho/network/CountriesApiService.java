package com.raj.zoho.network;

import com.raj.zoho.network.model.Countries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesApiService {
    String BASE_URL = "https://restcountries.eu/";
    String COUNTRIES_URL = "rest/v2/all";

    @GET(COUNTRIES_URL)
    Call<List<Countries>> getAllCountries();


}

