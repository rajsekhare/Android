package com.raj.zoho.activities.weather;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.raj.zoho.Constants;
import com.raj.zoho.GPSTracker;
import com.raj.zoho.PermissionAboveMarshmellow;
import com.raj.zoho.R;
import com.raj.zoho.Utils;
import com.raj.zoho.activities.home.HomeActivity;
import com.raj.zoho.adapters.WeatherAdapter;
import com.raj.zoho.network.model.WeatherModel.Daily;
import com.raj.zoho.network.model.WeatherModel.MainWeather;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity  extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;
    WeatherAdapter adapter;
    ArrayList<Daily> forecastdayArrayList = new ArrayList<Daily>();
    WeatherVM model;
    LinearLayout noData;
    @BindView(R.id.cityText)
    TextView tvCityName;
    @BindView(R.id.tempText)
    TextView tvTemp;
    @BindView(R.id.condition)
    TextView tvCondition;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.progressBar2)
    ProgressBar progress;
    AnyChartView anyChartView;
    @BindView(R.id.textView2)
    TextView textHeader;
    GPSTracker gps;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    boolean gps_enabled = false;
    boolean net_enabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        // bind the view using butterknife
        ButterKnife.bind(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e("","Exception gps_enabled");
        }

        try {
            net_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.e("","Exception network_enabled");
        }
        if(!net_enabled|!gps_enabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WeatherActivity.this);
            builder.setTitle("Location Not Enabled")
                    .setMessage("Go To Settings")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        anyChartView = findViewById(R.id.any_chart_view);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        anyChartView.setVisibility(View.GONE);
        textHeader.setVisibility(View.GONE);
        //  collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int resId = R.anim.layout_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);
        noData = findViewById(R.id.noData);
        model = ViewModelProviders.of(this).get(WeatherVM.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(WeatherActivity.this,HomeActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }
    public void myWeatherModel() {
          System.out.println("inside weather model");
        model.getWeatherData().observe(this, new Observer<MainWeather>() {
            @Override
            public void onChanged(@Nullable MainWeather weatherData) {


                collapsingToolbarLayout.setTitle(Constants.cityName);
                tvCityName.setText(Constants.cityName);
                tvTemp.setText(Utils.getCelciusFromKelvin(weatherData.getCurrent().getTemp()) + getString(R.string.mydegree));
                tvCondition.setText(weatherData.getCurrent().getWeather()[0].getDescription());
                System.out.println("the image is "+"http://openweathermap.org/img/w/" +weatherData.getCurrent().getWeather()[0].getIcon()+".png");

                Glide.with(WeatherActivity.this)
                        .load("https://openweathermap.org/img/w/" +weatherData.getCurrent().getWeather()[0].getIcon()+".png")
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .crossFade()
                        .into(image);
                List<Daily> forecastData = Arrays.asList(weatherData.getDaily());
                forecastdayArrayList.addAll(forecastData);

                setGraph();

                if (adapter == null) {
                    ArrayList<Daily> noRepeat = new ArrayList<Daily>();
                    for (Daily event : forecastdayArrayList) {
                        boolean isFound = false;
                        // check if the event name exists in noRepeat
                        for (Daily e : noRepeat) {
                            if (e.getDt().equals(event.getDt()) || (e.equals(event))) {
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) noRepeat.add(event);
                    }
                    adapter = new WeatherAdapter(WeatherActivity.this, noRepeat);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(true);
                } else {
                    adapter.notifyDataSetChanged();
                }
                progress.setVisibility(View.GONE);
                textHeader.setVisibility(View.VISIBLE);


            }
        });
    }
public void setGraph(){

    Cartesian cartesian = AnyChart.column();

    List<DataEntry> data = new ArrayList<>();
    for (int i = 0; i < forecastdayArrayList.size(); i++) {
        double num2 =Double.parseDouble(Utils.getCelciusFromKelvin(forecastdayArrayList.get(i).getTemp().getMax()));
        data.add(new ValueDataEntry(Utils.changeDateformat(forecastdayArrayList.get(i).getDt()),num2));
    }
    Column column = cartesian.column(data);

    column.color("#2c3e50");
    column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0d)
            .offsetY(5d)
            .format("{%Value}{groupsSeparator: } °c");

    cartesian.animation(true);
    cartesian.title("Weather report for coming days");
    cartesian.yScale().minimum(0d);

    cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } °c");

    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
    cartesian.interactivity().hoverMode(HoverMode.BY_X);
    cartesian.xAxis(0).title("Date");
    cartesian.yAxis(0).title("Temprature");
    anyChartView.setChart(cartesian);
    anyChartView.setVisibility(View.VISIBLE);
}
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
            Constants.cityLat=String.valueOf(latitude);
            Constants.cityLon=String.valueOf(longitude);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String address = addresses.get(0).getSubLocality();
            String cityName = addresses.get(0).getLocality();
            String stateName = addresses.get(0).getAdminArea();

            if(cityName!=null){
                System.out.println("the location is "+latitude+" "+longitude+" "+address+" "+cityName+stateName);
                Constants.cityName=address;
            }
            if (Utils.isNetworkAvailable(this)) {
                progress.setVisibility(View.VISIBLE);
                myWeatherModel();

            } else {
                noData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Snackbar
                        .make(coordinatorLayout, getString(R.string.noInternet), Snackbar.LENGTH_LONG).show();
            }
        } else {
            // Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10)
                .setFastestInterval(1000*601*1);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("", "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    protected boolean isLocationEnabled(){
        String le = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(le);
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            return false;
        } else {
            return true;
        }
    }
}

