package com.example.randypfohl.myportfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button moviesButton;
    private Button stockButton;
    private Button biggerButton;
    private Button materialButton;
    private Button ubiquitousButton;
    private Button capstoneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.moviesButton = (Button)findViewById(R.id.moviesButton);
        moviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "This button will launch my Popular Movies app!", Toast.LENGTH_SHORT).show();
            }
        });


        this.stockButton = (Button)findViewById(R.id.stockButton);
        stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "This button will launch my Stock Hawk app!", Toast.LENGTH_SHORT).show();
            }
        });


        this.biggerButton = (Button)findViewById(R.id.biggerButton);
        biggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "This button will launch my Build It Bigger app!", Toast.LENGTH_SHORT).show();
            }
        });


        this.materialButton = (Button)findViewById(R.id.materialButton);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "This button will launch my Make Your App Material app!", Toast.LENGTH_SHORT).show();
            }
        });


        this.ubiquitousButton = (Button)findViewById(R.id.ubiquitousButton);
        ubiquitousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "This button will launch my Go Ubiquitous app!", Toast.LENGTH_SHORT).show();
            }
        });


        this.capstoneButton = (Button)findViewById(R.id.capstoneButton);
        capstoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "This button will launch my Capstone app!", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
