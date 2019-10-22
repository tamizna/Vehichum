package com.tamizna.vehichum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

public class BensinMotor extends AppCompatActivity implements View.OnClickListener {

    SearchView cariBensinMotor;
    ImageButton imageButtonView;
    MapsFragment mapsFragment = new MapsFragment();
    ListFragment listFragment = new ListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bensin_motor);
        cariBensinMotor = findViewById(R.id.searchView);
        imageButtonView = findViewById(R.id.imgbtn_view);

        imageButtonView.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, mapsFragment).commit();
    }

    @Override
    public void onClick(View v) {
        try {
            final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.frame);

            if (fragmentInFrame instanceof MapsFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, listFragment).commit();
                imageButtonView.setImageResource(R.drawable.map_view);
            } else if (fragmentInFrame instanceof ListFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, mapsFragment).commit();
                imageButtonView.setImageResource(R.drawable.list_view);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

