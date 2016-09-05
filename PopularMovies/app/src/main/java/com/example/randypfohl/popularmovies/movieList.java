package com.example.randypfohl.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class movieList extends Fragment {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public ArrayAdapter<String> mForecastAdapter;
    public ArrayList<Movie> movies = new ArrayList<Movie>();

    public movieList() {
        // Required empty public constructor
    }



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
           /** mForecastAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_forecast,
                            R.id.list_item_forecast_textview,
                            new ArrayList<String>());

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(mForecastAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String forecast = mForecastAdapter.getItem(i);
                    Intent openDetail = new Intent(getActivity(), DetailActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, forecast);
                    startActivity(openDetail);

                }
            });

            return rootView;

            **/
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           
           //// TODO: 9/2/16 Create Menu folder and menu options for popular, refresh, top rated 
            //inflater.inflate(R.menu.forecastfragment, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            //if (id == R.id.action_refresh) {
            //    updateMovies();
            //    return true;
            //}

            return super.onOptionsItemSelected(item);
        }

        public void updateMovies(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());



           // String location = preference.getString(getString(R.string.pref_location_key)
                 //   ,getString(R.string.pref_location_default));
          //  weatherTask.execute(location);
        }

        @Override
        public void onStart(){
            super.onStart();
            updateMovies();
        }




     //// TODO: 9/2/16  add in async task to call movies api and return list of movies
    }
