package com.tamizna.vehichum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private String jamOperasional;

    public MyInfoWindowAdapter(Context ctx, String jamOp) {

        this.context = ctx;
        this.jamOperasional = jamOp;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.map_info_window, null);

        TextView judul = view.findViewById(R.id.txt_judulmap);
        TextView alamat = view.findViewById(R.id.txt_alamatmap);
        TextView jam = view.findViewById(R.id.jam_info);

        judul.setText(marker.getTitle());
        alamat.setText(marker.getSnippet());

        FirebaseMarker firebaseMarker = new FirebaseMarker();

        if (jamOperasional.equals("00:00 - 00:00")) {
            jamOperasional = "24 Jam";
        }
        jam.setText(jamOperasional);

        return view;
    }
}