package com.tamizna.vehichum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MenuMobil extends AppCompatActivity implements View.OnClickListener {
    CardView pomBensin, cuciMobil, bengkel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mobil);

        pomBensin = findViewById(R.id.cardview_pombensin);
        cuciMobil = findViewById(R.id.cardview_cucimobil);
        bengkel = findViewById(R.id.cardview_bengkel);

        pomBensin.setOnClickListener(this);
        cuciMobil.setOnClickListener(this);
        bengkel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview_pombensin:
                Intent pindah_pombensinmobil = new Intent(MenuMobil.this, BensinMobil.class);
                startActivity(pindah_pombensinmobil);
                break;
            case R.id.cardview_cucimobil:
                Intent pindah_cucimobil = new Intent(MenuMobil.this, CuciMobil.class);
                startActivity(pindah_cucimobil);
                break;
            case R.id.cardview_bengkel:
                Intent pindah_bengkelmobil = new Intent(MenuMobil.this, BengkelMobil.class);
                startActivity(pindah_bengkelmobil);
                break;
        }
    }
}
