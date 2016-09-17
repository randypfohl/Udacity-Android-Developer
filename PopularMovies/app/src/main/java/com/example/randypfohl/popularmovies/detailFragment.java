package com.example.randypfohl.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class detailFragment extends Fragment {
        private final String LOG_TAG = MainActivity.class.getSimpleName();

        public detailFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();


                String title = intent.getStringExtra(getString(R.string.detail_movie_title));
                String release = intent.getStringExtra(getString(R.string.detail_movie_release));
                String voteave = intent.getStringExtra(getString(R.string.detail_movie_vote_ave));
                String plot = intent.getStringExtra(getString(R.string.detail_movie_plot));
                String poster = intent.getStringExtra(getString(R.string.detail_movie_poster));

                ((TextView) root.findViewById(R.id.detailTitle)).setText(title);
                ((TextView) root.findViewById(R.id.detailRelease)).setText(release);
                ((TextView) root.findViewById(R.id.detailRating)).setText(voteave);
                ((TextView) root.findViewById(R.id.detailSynopsis)).setText(plot);



        final String MOVIE_BASE_URL = getString(R.string.posterURI);
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(getString(R.string.image_Size))
                .appendPath(poster.substring(1)).build();

        String url = buildUri.toString();
        Log.e(LOG_TAG, url);

        Picasso.with(getActivity()).load(url).into((ImageView)root.findViewById(R.id.detailPoster));

        return root;
        }

    }
