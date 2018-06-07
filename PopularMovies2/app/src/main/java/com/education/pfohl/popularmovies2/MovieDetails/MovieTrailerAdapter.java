package com.education.pfohl.popularmovies2.MovieDetails;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.models.Trailer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieTrailerAdapter extends RecyclerView.Adapter<Trailer> {

    private static final String LOG_TAG = com.education.pfohl.popularmovies2.MovieList.MovieImageAdapter.class.getSimpleName();
    private ArrayList<Trailer> trailers;
    private Context context;

    public MovieTrailerAdapter(Activity context, int resourceID, ArrayList<Trailer> trailers) {
        super(context, resourceID, trailers);
        this.context = context;
        this.trailers = trailers;
    }


    //todo fix this to set up on click correctly and load correct list item view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String key = getItem(position).getKey();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_list_item, parent, false);
        }

        ((TextView) parent.findViewById(R.id.trailer_title)).setText(getItem(position).name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(context, key);
            }
        });
        return convertView;
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }


    //todo make this a recycler view adapter god damn it.
    @NonNull
    @Override
    public Trailer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Trailer holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
