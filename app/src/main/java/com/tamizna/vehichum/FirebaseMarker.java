package com.tamizna.vehichum;

public class FirebaseMarker {
    public String nama, alamat, key;
    public double latitude, longitude;

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }

    public String getJamTutup() {
        return jamTutup;
    }

    public void setJamTutup(String jamTutup) {
        this.jamTutup = jamTutup;
    }

    public String jamBuka, jamTutup;

    public FirebaseMarker() {

    }

    public FirebaseMarker(String n, String a, double lt, double lg) {
        this.nama = n;
        this.alamat = a;
        this.latitude = lt;
        this.longitude = lg;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
