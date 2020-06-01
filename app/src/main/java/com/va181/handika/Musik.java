package com.va181.handika;

import java.util.Date;

public class Musik   {

    private int idMusik;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String penyanyi;
    private String lirik;
    private String link;

    public Musik(int idMusik, String judul, Date tanggal, String gambar, String penyanyi, String lirik, String link) {
        this.idMusik = idMusik;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.penyanyi = penyanyi;
        this.lirik = lirik;
        this.link = link;
    }

    public int getIdMusik() { return idMusik; }

    public void setidMusik(int idMusik) {
        this.idMusik= idMusik;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getPenyanyi() {
        return penyanyi;
    }

    public void setPenyanyi(String penyanyi) {
        this.penyanyi = penyanyi;
    }


    public String getLirik() {
        return lirik;
    }

    public void setLirik(String lirik) {
        this.lirik = lirik;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}