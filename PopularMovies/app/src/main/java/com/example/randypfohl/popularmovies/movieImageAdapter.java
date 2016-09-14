package com.example.randypfohl.popularmovies;
import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

/**
 * Created by randypfohl on 9/9/16.
 */
public class movieImageAdapter extends ArrayAdapter<String>{

        private static final String LOG_TAG = movieImageAdapter.class.getSimpleName();
        private String[] movieUrls;

        public movieImageAdapter(Activity context, int resourceID, String... movieUrls) {
            super(context, resourceID, movieUrls);
            // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
            // the second argument is used when the ArrayAdapter is populating a single TextView.
            // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
            // going to use this second argument, so it can be any value. Here, we used 0.
            this.movieUrls = movieUrls;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Gets the movie object from the ArrayAdapter at the appropriate position
            String movieUrl = getItem(position);

            // Adapters recycle views to AdapterViews.
            // If this is a new View object we're getting, then inflate the layout.
            // If not, this view already has the layout inflated from a previous call to getView,
            // and we modify the View widgets as usual.
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
            }

            final String FORECAST_BASE_URL = "http://image.tmdb.org/t/p/";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendPath("w780")
                    .appendPath(movieUrls[position])
                    .build();

            Picasso.with(getContext())
                    .load(builtUri.toString())
                    .into((ImageView) convertView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("success", "good");
                        }

                        @Override
                        public void onError() {
                            Log.e("failure", "bad");
                        }
                    });

            return convertView;
        }
    }
