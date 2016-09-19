package com.example.randypfohl.popularmovies;

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


public class DetailFragment extends Fragment {
        private final String LOG_TAG = MainActivity.class.getSimpleName();

        public DetailFragment() {
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
            Bundle intent = getActivity().getIntent().getExtras();
            Movie movie = intent.getParcelable(getString(R.string.movie_object));

                ((TextView) root.findViewById(R.id.detailTitle)).setText(movie.getTitle());
                ((TextView) root.findViewById(R.id.detailRelease)).setText(movie.getRelease());
                ((TextView) root.findViewById(R.id.detailRating)).setText(movie.getVoteAve());
                ((TextView) root.findViewById(R.id.detailSynopsis)).setText(movie.getPlot());



        final String MOVIE_BASE_URL = getString(R.string.posterURI);
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(getString(R.string.image_Size))
                .appendPath(movie.getPosterUrl().substring(1)).build();

        String url = buildUri.toString();
        Log.e(LOG_TAG, url);

        Picasso.with(getActivity()).load(url).into((ImageView)root.findViewById(R.id.detailPoster));

        return root;
        }

    }
