package com.raj.zoho.network;
import com.raj.zoho.AppApplication;
import com.raj.zoho.BuildConfig;
import com.raj.zoho.network.interceptors.ErrorHandlerInterceptor;
import com.raj.zoho.network.interceptors.HeaderModifierInterceptor;


import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RestClient {
    private CountriesApiService apiService;

    @Inject
    public RestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.interceptors().add(new HeaderModifierInterceptor());
        httpClient.interceptors().add(new ErrorHandlerInterceptor(AppApplication.getContext()));
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(logging);
        }

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(CountriesApiService.BASE_URL)//passing API_URL
                .addConverterFactory(GsonConverterFactory.create()) //passing MoshiConverterFactory to convert json key and value into our object
                .client(httpClient.build())//passing OkHttpClient object
                .build();
        apiService = restAdapter.create(CountriesApiService.class);
    }


    //double checked locking singleTon Design.
    public CountriesApiService getApiService() {
        if (apiService == null) {
            synchronized (RestClient.class) {
                if (apiService == null)
                    new RestClient();
            }
        }
        return apiService;
    }

}
