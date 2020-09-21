package com.raj.zoho.activities.weather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.raj.zoho.BuildConfig;
import com.raj.zoho.Constants;
import com.raj.zoho.network.WeatherApiService;
import com.raj.zoho.network.model.WeatherModel.MainWeather;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherVM extends ViewModel {

    //fetched asynchronously
    private MutableLiveData<MainWeather> weatherMutableLiveData;

    //get the data
    public LiveData<MainWeather> getWeatherData() {
        //null checks
        if (weatherMutableLiveData == null) {
            weatherMutableLiveData = new MutableLiveData<MainWeather>();
            callWeatherInformation();
        }
        return weatherMutableLiveData;
    }


    //get the JSON data from URL
    private void callWeatherInformation() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                // .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        WeatherApiService apiService = retrofit.create(WeatherApiService.class);
        Call<MainWeather> call = apiService.getWeatherData( Constants.cityLat, Constants.cityLon,Constants.key);


        call.enqueue(new Callback<MainWeather>() {
            @Override
            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                System.out.println("the response in main is "+response.body());

                if (response.body() == null)
                    return;
                if (response.isSuccessful())
                    //set list to our MutableLiveData
                    System.out.println("the response is "+response.body());
                    weatherMutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<MainWeather> call, Throwable t) {
                t.printStackTrace();
                weatherMutableLiveData.setValue(null);
            }
        });
    }


    private Interceptor interceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request(); //Current Request

            okhttp3.Response response = chain.proceed(
                    originalRequest); //Get response of the request

            if (BuildConfig.DEBUG) {
                //logging the response
                String bodyString = response.body().string();
                Log.e("RETROFIT", "Sending request %s with headers " +
                        "\n%s\nInput %s" +
                        "\nResponse HTTP %s %s \n" + "Body %s\n" + originalRequest.url()
                        + originalRequest.headers() + bodyToString(originalRequest) +
                        response.code() + response.message() + bodyString);
                response = response.newBuilder().body(
                        ResponseBody.create(response.body().contentType(), bodyString)).build();
            }

            return response;
        }
    };

    private String bodyToString(final Request request) {
        String body = "";
        try {

            Buffer buffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(buffer);
                body = buffer.readUtf8();
            }
            return body;
        } catch (final IOException e) {
            return "Not Working";
        }
    }



}