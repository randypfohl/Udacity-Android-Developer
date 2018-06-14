package com.education.pfohl.popularmovies2.MovieDetails;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.pfohl.popularmovies2.R;
import com.education.pfohl.popularmovies2.models.Trailer;

import java.util.ArrayList;
import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerViewHolder> {

    private static final String LOG_TAG = com.education.pfohl.popularmovies2.MovieList.MovieImageAdapter.class.getSimpleName();
    private List<Trailer> trailers;
    private Context context;

    public MovieTrailerAdapter(Context context, List<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        holder.trailerTitle.setText(trailers.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(context, trailers.get(position).getKey());
            }
        });
    }


    @Override
    public int getItemCount() {
        return trailers.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView trailerTitle;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TrailerViewHolder(View itemView) {
            super(itemView);

            trailerTitle = (TextView) itemView.findViewById(R.id.trailer_title);
        }
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
}
