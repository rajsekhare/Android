package com.raj.zoho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.util.Objects;
import java.util.TimeZone;

public final class Utils {

    private static final String TAG = Utils.class.getName();



    private Utils() {

    }

    /***
     * using this method to set the view visible
     */


//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }


    public static String changeDateformat(String timestamp) {

        String dayFromDate = "";
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date date;
//        try {
//            date = dateformat.parse(Date);
//            DateFormat dayFormat = new SimpleDateFormat("EEEE");
//            dayFromDate = dayFormat.format(date);
//            Log.d("raj", "----------:: " + dayFromDate);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        long num = Long.parseLong(timestamp);
        Date date = new Date(num);
        DateFormat format = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String formatted = format.format(date);
        System.out.println(formatted);



        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//        String formatteds = sdf.format(new Date(num));
        String finaldate = sdf.format(num*1000);
        finaldate= finaldate.replace("September","Sep");

//        Long dateLong=Long.parseLong(sdf.format(num*1000));



        return finaldate.toString();
    }
    public static void hideViews(View... views) {
        for (View view : views) {
            if (view == null) continue;
            view.setVisibility(View.GONE);
        }
    }

    /***
     * using this method to set the view visible
     * @param views list of views...
     */
    public static void showViews(View... views) {
        for (View view : views) {
            if (view == null) continue;
            view.setVisibility(View.VISIBLE);
        }
    }


    /***
     * this method used to load image into specified imageView;
     * @param context pass context for Picasso to initiate;
     * @param imageView after loading the image will be appear on this widget;
     * @param url image url
     * @param errorImageDrawable in case error comes during fetching image from given url then this errorImageDrawable will be appear into imageView;
     */
    public static void loadImage(Context context, ImageView imageView, Uri url, int placeHolder, int errorImageDrawable) {
        Picasso.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(errorImageDrawable)
                .into(imageView);

    }

    public static void setBitmap(Context context, String url, SimpleExoPlayerView mPlayerView) {
        if (mPlayerView == null) return;
        if (TextUtils.isEmpty(url)) {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.question_mark));
            return;
        }
        Picasso.with(context)
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        mPlayerView.setDefaultArtwork(bitmap);
                        Log.d(TAG, "Bitmap loaded");
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                                (context.getResources(), R.drawable.question_mark));
                        Log.d(TAG, "Bitmap Failed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    /***
     * @param context passing to get the network status
     * @return boolean value as true or false
     * true: if network is available
     * false: if not available
     */
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) return false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (Objects.requireNonNull(connectivityManager).getActiveNetworkInfo() != null) {
            try {
                return connectivityManager.getActiveNetworkInfo().isConnected();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static String getCelciusFromKelvin(String value){
         DecimalFormat df = new DecimalFormat("00.0");

        float kelvin = Float.parseFloat(value);
        // Kelvin to Degree Celsius Conversion
        float celsius = kelvin - 273.15F;
        System.out.println("Celsius: "+ celsius);
        return String.valueOf(df.format(celsius));
    }


}
