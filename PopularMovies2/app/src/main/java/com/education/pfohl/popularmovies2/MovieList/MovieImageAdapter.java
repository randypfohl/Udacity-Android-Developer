package com.education.pfohl.popularmovies2.MovieList;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieImageAdapter extends ArrayAdapter<Movie>{

    private static final String LOG_TAG = MovieImageAdapter.class.getSimpleName();
    private ArrayList<Movie> movies;

    public MovieImageAdapter(Activity context, int resourceID, ArrayList<Movie> movies) {
        super(context, resourceID, movies);

        this.movies= movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String movieUrl = getItem(position).getPoster_path();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
        }

        final String TMDB_BASE_URL = getContext().getString(R.string.tmdb_base_url);

        final Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(getContext().getString(R.string.image_Size))
                // we substring because this string comes prefixed with "/" and will be translated into it's %2 value. So we remove it before appending.
                .appendPath(movies.get(position).getPoster_path().substring(1))
                .build();

        Picasso.get()
                .load(builtUri.toString())
                .into((ImageView) convertView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso", "success");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", e.toString());
                    }
                });

        return convertView;
    }
}
