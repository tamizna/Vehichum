package com.tamizna.vehichum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SearchView;

public class BengkelMobil extends AppCompatActivity implements View.OnClickListener {

    SearchView cariBengkelMobil;
    ImageButton imageButtonView;
    MapsFragment mapsFragment = new MapsFragment();
    DetailFragment detailFragment = new DetailFragment();
    ListFragment listFragment = new ListFragment();
    FrameLayout detailFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bengkel_mobil);

        cariBengkelMobil = findViewById(R.id.searchView);
        imageButtonView = findViewById(R.id.imgbtn_view);
        detailFrame = findViewById(R.id.frame_detail);


        imageButtonView.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, mapsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_detail, detailFragment).commit();
        detailFrame.setVisibility(View.GONE);

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

