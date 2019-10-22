package com.tamizna.vehichum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailTempat extends AppCompatActivity {

    TextView nama, alamat, jam;
    Button call;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tempat);

        nama = findViewById(R.id.detail_judul);
        alamat = findViewById(R.id.detail_alamat);
        jam = findViewById(R.id.detail_jam);
        call = findViewById(R.id.detail_hubungi);
        foto = findViewById(R.id.detail_foto);

        String namaTemp = getIntent().getExtras().getString("NAMA_TEMPAT");
        nama.setText(namaTemp);

    }
}
