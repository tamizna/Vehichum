package com.tamizna.vehichum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MenuMotor extends AppCompatActivity implements View.OnClickListener {

    CardView pomBensin, cuciMotor, bengkel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_motor);

        pomBensin = findViewById(R.id.cardview_pombensin);
        cuciMotor = findViewById(R.id.cardview_cucimotor);
        bengkel = findViewById(R.id.cardview_bengkel);

        pomBensin.setOnClickListener(this);
        cuciMotor.setOnClickListener(this);
        bengkel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview_pombensin:
                Intent pindahPomBensinMotor = new Intent(MenuMotor.this, BensinMotor.class);
                startActivity(pindahPomBensinMotor);
                break;
            case R.id.cardview_cucimotor:
                Intent pindahCuciMotor = new Intent(MenuMotor.this, CuciMotor.class);
                startActivity(pindahCuciMotor);
                break;
            case R.id.cardview_bengkel:
                Intent pindahBengkelMotor = new Intent(MenuMotor.this, BengkelMotor.class);
                startActivity(pindahBengkelMotor);
                break;
        }
    }
}
