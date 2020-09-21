package com.raj.zoho.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raj.zoho.R;
import com.raj.zoho.Utils;
import com.raj.zoho.network.model.WeatherModel.Daily;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context mCtx;
    private ArrayList<Daily> forecastdayArrayList;

    public WeatherAdapter(Context mCtx, ArrayList<Daily> forecastdayArrayList) {
        System.out.println("the weather item is "+forecastdayArrayList);
        this.mCtx = mCtx;
        this.forecastdayArrayList = forecastdayArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //printing day, min temp, conditions and icon
        holder.date.setText(Utils.changeDateformat(forecastdayArrayList.get(position).getDt()));
//        holder.date.setText(forecastdayArrayList.get(position).getDt());

        holder.temp.setText(Utils.getCelciusFromKelvin(forecastdayArrayList.get(position).getTemp().getMax()) + mCtx.getString(R.string.mydegree));
        holder.detail.setText(forecastdayArrayList.get(position).getWeather()[0].getDescription());

        Glide.with(mCtx)
                .load("https://openweathermap.org/img/w/" +forecastdayArrayList.get(position).getWeather()[0].getIcon()+".png")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .crossFade()
                .into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return forecastdayArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.image)
        ImageView icon;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.textView)
        TextView temp;
        @BindView(R.id.dateText)
        TextView date;

        private ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}