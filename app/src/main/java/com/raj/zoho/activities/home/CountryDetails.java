package com.raj.zoho.activities.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.raj.zoho.R;
import com.raj.zoho.network.model.Countries;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryDetails extends AppCompatActivity {
    @Nullable
    @BindView(R.id.imageView)
    ImageView flagImamge;
    @BindView(R.id.name)
    TextView countryName;
    @BindView(R.id.capital)
    TextView capitalName;
    @BindView(R.id.region)
    TextView regionName;
    @BindView(R.id.subregion)
    TextView subregionName;
    @BindView(R.id.population)
    TextView population;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.currency)
    TextView currency;
    @BindView(R.id.language)
    TextView language;
    String languages="";
    static Countries details ;
    public static void throwDetails(Context context, Countries country) {
        details=country;
        Intent intent = new Intent(context, CountryDetails.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        ButterKnife.bind(this);
        setData();
    }

    private void setData() {
        System.out.println("the flag indetails is "+details.getFlag());
        SvgLoader.pluck()
                .with(CountryDetails.this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(details.getFlag(), flagImamge);
        countryName.setText("Name: "+details.getName());
        capitalName.setText("Capital: "+details.getCapital());
        regionName.setText("Region: "+details.getRegion());
        subregionName.setText("Sub-Region: "+details.getSubregion());
        area.setText("Area: "+details.getArea()+" Sq.km");
        population.setText("Population: "+details.getPopulation());
        currency.setText("Currency : "+details.getCurrencies().get(0).getSymbol()+" "+details.getCurrencies().get(0).getName()+" ("+details.getCurrencies().get(0).getCode()+")");
        for (int i=0; i<details.getLanguages().size(); i++)
        {
            if(i==details.getLanguages().size()-1){
                languages=languages.concat(details.getLanguages().get(i).getName())+"";

            }
            else{
                languages=languages.concat(details.getLanguages().get(i).getName())+", ";

            }
        }
        language.setText("Languages: "+languages);

    }
//    @Override protected void onDestroy() {
//        super.onDestroy();
//        SvgLoader.pluck().close();
//    }
}
