package com.guguscode.dapenduk.Model;

public class DataModel {

    private int id;
    private String nama;
    private String jk;
    private String alm;
    private String tgl;
    private String tpt;
    private String pk;


    public DataModel(int id, String nama, String jk, String alm, String tgl, String tpt, String pk) {
        this.id = id;
        this.nama = nama;
        this.jk = jk;
        this.alm = alm;
        this.tgl = tgl;
        this.tpt = tpt;
        this.pk = pk;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJk() {
        return jk;
    }

    public String getAlm() {
        return alm;
    }

    public String getTgl() {
        return tgl;
    }

    public String getTpt() {
        return tpt;
    }

    public String getPk() {
        return pk;
    }

}
