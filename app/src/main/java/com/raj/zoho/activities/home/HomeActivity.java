package com.raj.zoho.activities.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.raj.zoho.R;
import com.raj.zoho.SimpleIdlingResource;
import com.raj.zoho.adapters.CountriesAdapter;
import com.raj.zoho.database.CountriesDao;
import com.raj.zoho.database.DataBaseHelper;
import com.raj.zoho.network.model.Countries;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements CountriesAdapter.ListItemOnClickListener {

    @Nullable
    public static SimpleIdlingResource mIdlingResource;
    HomeVM viewModel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CountriesAdapter adapter;
    List<Countries> list;
    @BindView(R.id.progressBar1)
    ProgressBar progress;
    RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    private String TAG = HomeActivity.class.getName();
    private CountriesDao countryDao;
    private List<Countries> CountryDump;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            synchronized (HomeActivity.class) {
                if (mIdlingResource == null) {
                    mIdlingResource = new SimpleIdlingResource();
                }
            }
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        progress.setVisibility(View.VISIBLE);
        getIdlingResource();
        mIdlingResource.setIdleState(false);
        countryDao = DataBaseHelper.getInstance().countriesDao();
        viewModel = ViewModelProviders.of(this).get(HomeVM.class);
        viewModel.init();

//        bindAdapter();
//        getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>)this).forceLoad();
        setupObjserver();
    }

    private void setupObjserver() {
        viewModel.getCountryList().observe(this, countries -> {
//
            List<Countries> noRepeat = new ArrayList<Countries>();
            for (Countries event : countries) {
                boolean isFound = false;
                // check if the event name exists in noRepeat
                for (Countries e : noRepeat) {
                    if (e.getName().equals(event.getName()) || (e.equals(event))) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) noRepeat.add(event);
            }
            CountryDump=noRepeat;
            adapter = new CountriesAdapter(noRepeat, this);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = new GridLayoutManager(this, 3);
                // In landscape
            } else {
                // In portrait
                layoutManager = new GridLayoutManager(this, 1);
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
//            adapter.addAllItem(countries);
            mIdlingResource.setIdleState(true);
            progress.setVisibility(View.GONE);
        });
    }

    private void bindAdapter() {
        adapter = new CountriesAdapter(list, this);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(this, 3);
            // In landscape
        } else {
            // In portrait
            layoutManager = new GridLayoutManager(this, 1);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }
    private SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getDealsFromDb(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getDealsFromDb(newText);
                    return true;
                }

                private void getDealsFromDb(String searchText) {
                    searchText = "%"+searchText+"%";

                    if(countryDao!=null){
                        countryDao.getCountryDetail(searchText)
                                .observe(HomeActivity.this, new Observer<List<Countries>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Countries> deals) {

                                            if (deals.size() == 0) {
//                                                setupObjserver();
                                                CountriesAdapter adapter = new CountriesAdapter(CountryDump,
                                                        HomeActivity.this
                                                );
                                                recyclerView.setAdapter(adapter);
//
                                            }
                                            else{
                                                List<Countries> noRepeat = new ArrayList<Countries>();
                                                for (Countries event : deals) {
                                                    boolean isFound = false;
                                                    // check if the event name exists in noRepeat
                                                    for (Countries e : noRepeat) {
                                                        if (e.getName().equals(event.getName()) || (e.equals(event))) {
                                                            isFound = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!isFound) noRepeat.add(event);
                                                }
                                                CountriesAdapter adapter = new CountriesAdapter(noRepeat,
                                                        HomeActivity.this
                                                );
                                                recyclerView.setAdapter(adapter);
//                                                adapter.notifyDataSetChanged();
                                            }

                                    }
                                });

                    }
                }
            };
    @Override
    public void onListItemClick(Countries selectedObject, View view) {
        Log.d(TAG, "clicked :" + selectedObject.getName());
        CountryDetails.throwDetails(this,selectedObject);
    }

//    @Override protected void onDestroy() {
//        super.onDestroy();
//        SvgLoader.pluck().close();
//    }
}
