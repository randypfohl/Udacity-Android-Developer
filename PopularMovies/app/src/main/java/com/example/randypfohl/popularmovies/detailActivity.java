package com.example.randypfohl.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_container, new detailFragment())
                    .commit();
        }
    }

}
