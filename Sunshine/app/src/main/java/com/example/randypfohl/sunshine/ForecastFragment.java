package com.example.randypfohl.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */

public class ForecastFragment extends Fragment {


    ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);


        // Create some dummy data for the ListView.  Here's a sample weekly forecast
        String[] data = {
                "Mon 6/23 - Never - 31/17",
                "Tue 6/24 -  going - 21/8",
                "Wed 6/25 - to - 22/17",
                "Thurs 6/26 - give - 18/11",
                "Fri 6/27 - you - 21/10",
                "Sat 6/28 - up - 23/18",
                "Sun 6/29 - never - 20/7",
                "Sat 6/30 - up - 23/18",
                "Sat 7/1 - never - 24/18",
                "Sat 7/2 - going - 21/10",
                "Sat 7/3 - to - 20/19",
                "Sat 7/4 - let - 23/18",
                "Sat 7/5 - you - 31/23",
                "Sat 7/6 - down - 29/21",
                "Sat 7/7 - never - 28/16",
                "Sat 7/8 - going - 27/18",
                "Sat 7/9 - to - 25/18",
                "Sat 7/10 - run - 24/20",
                "Sat 7/11 - around - 23/21",
                "Sat 7/12 - and - 17/10",
                "Sat 7/13 - desert - 29/18",
                "Sat 7/14 - you - 27/18"

        };

        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));

        // This code is used to initialize the adapter
        // Now that we have some dummy foreacst data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source ( like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        // ID of list item layout
                        R.layout.list_item_forecast, //The name of the Layout ID
                        //ID of textview to populate
                        R.id.list_item_forecast_textview, // The ID of the textview to populate
                        weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        // this is used to set an array adapter on the ListView.
        listView.setAdapter(mForecastAdapter);

        return rootView;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



