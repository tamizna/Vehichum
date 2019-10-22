package com.tamizna.vehichum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FirebaseMarker> daftarBarang;
    TextView load;
    String jenis;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View recview = inflater.inflate(R.layout.fragment_list, container, false);
        View itemList = inflater.inflate(R.layout.item_list, container, false);
        rvView = recview.findViewById(R.id.rvItems);
        load = recview.findViewById(R.id.txt_load);

        rvView.setVisibility(View.GONE);

        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvView.setLayoutManager(layoutManager);

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        jenis = String.valueOf(getActivity().getClass().getSimpleName());

        if (jenis.equals("BengkelMobil")) {
            database = FirebaseDatabase.getInstance().getReference("BengkelMobil");
        } else if (jenis.equals("BengkelMotor")) {
            database = FirebaseDatabase.getInstance().getReference("BengkelMotor");
        } else if (jenis.equals("BensinMobil")) {
            database = FirebaseDatabase.getInstance().getReference("SPBU");
        } else if (jenis.equals("BensinMotor")) {
            database = FirebaseDatabase.getInstance().getReference("POMMotor");
        } else if (jenis.equals("CuciMotor")) {
            database = FirebaseDatabase.getInstance().getReference("CuciMotor");
        } else {
            database = FirebaseDatabase.getInstance().getReference("CuciMobil");
        }

        /**
         * Mengambil data barang dari Firebase Realtime DB
         */
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarBarang = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    FirebaseMarker barang = noteDataSnapshot.getValue(FirebaseMarker.class);
                    barang.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarBarang.add(barang);
                }

                /**
                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new AdapterItemRV(daftarBarang, 0.0, 0.0, getActivity());
                rvView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                rvView.setVisibility(View.VISIBLE);
                load.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
        // Inflate the layout for this fragment
        return recview;
    }
}
