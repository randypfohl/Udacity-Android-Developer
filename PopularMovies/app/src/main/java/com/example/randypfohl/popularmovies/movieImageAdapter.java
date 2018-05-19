package com.example.randypfohl.popularmovies;
import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by randypfohl on 9/9/16.
 */
public class MovieImageAdapter extends ArrayAdapter<String>{

        private static final String LOG_TAG = MovieImageAdapter.class.getSimpleName();
        private ArrayList<String> movieUrls;

        public MovieImageAdapter(Activity context, int resourceID, ArrayList<String> movieUrls) {
            super(context, resourceID, movieUrls);

            this.movieUrls = movieUrls;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String movieUrl = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
            }

            final String FORECAST_BASE_URL = getContext().getString(R.string.imageURI);

            final Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendPath(getContext().getString(R.string.image_Size))
                    // we substring because this string comes prefixed with "/" and will be translated into it's %2 value. So we remove it before appending.
                    .appendPath(movieUrls.get(position).substring(1))
                    .build();

            Picasso.with(getContext())
                    .load(builtUri.toString())
                    .into((ImageView) convertView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("success", "success");
                        }

                        @Override
                        public void onError() {
                            Log.e("failure", "bad " +builtUri.toString());
                        }
                    });

            return convertView;
        }
    }
