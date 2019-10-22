package com.tamizna.vehichum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnMobil, btnMotor, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        btnMobil = findViewById(R.id.imgbtn_mobil);
        btnMotor = findViewById(R.id.imgbtn_motor);
        btnAbout = findViewById(R.id.btn_about);

        btnMobil.setOnClickListener(this);
        btnMotor.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_mobil:
                Intent pindahMobil = new Intent(MainMenu.this, MenuMobil.class);
                startActivity(pindahMobil);
                break;
            case R.id.imgbtn_motor:
                Intent pindahMotor = new Intent(MainMenu.this, MenuMotor.class);
                startActivity(pindahMotor);
                break;
            case R.id.btn_about:
                AboutDialog about = new AboutDialog(MainMenu.this);
                about.show();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainMenu.this.finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

}