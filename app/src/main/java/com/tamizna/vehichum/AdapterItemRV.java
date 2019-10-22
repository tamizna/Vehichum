package com.tamizna.vehichum;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterItemRV extends RecyclerView.Adapter<AdapterItemRV.ViewHolder> {

    private ArrayList<FirebaseMarker> daftarBarang;
    private Context context;
    double curLat, curLong;


    public AdapterItemRV(ArrayList<FirebaseMarker> barangs, double lt, double lg, Context ctx) {
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarBarang = barangs;
        curLat = lt;
        curLong = lg;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvAddres, tvTime;
        Button infoTempat;

        ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.txt_judullist);
            tvAddres = v.findViewById(R.id.txt_alamatlist);
            tvTime = v.findViewById(R.id.txt_jamlist);
            infoTempat = v.findViewById(R.id.btn_detaillist);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String name = daftarBarang.get(position).getNama();
        final String alamat = daftarBarang.get(position).getAlamat();
        final String jamBuka = daftarBarang.get(position).getJamBuka();
        final String jamTutup = daftarBarang.get(position).getJamTutup();

        holder.tvTitle.setText(name);
        holder.tvAddres.setText(alamat);

        if (jamBuka.equals("00:00") && jamTutup.equals("00:00")) {
            holder.tvTime.setText("24 jam");
        } else {
            holder.tvTime.setText(jamBuka + " - " + jamTutup);
        }

        holder.infoTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahDetail = new Intent(context, DetailTempat.class);
                pindahDetail.putExtra("NAMA_TEMPAT", holder.tvTitle.getText());
                pindahDetail.putExtra("ALAMAT", holder.tvAddres.getText());
                pindahDetail.putExtra("JAM", holder.tvTime.getText());
                context.startActivity(pindahDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarBarang.size();
    }


}
