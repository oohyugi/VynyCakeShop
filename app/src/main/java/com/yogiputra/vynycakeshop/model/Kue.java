package com.yogiputra.vynycakeshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by koba on 12/17/15.
 */
public class Kue {

    public Kue(){

    }

    private int id_user;
    private int id_kue;
    private String nama_kue;
    private String harga;
    private String gambar;
    private String desc;
    private String message;
    private int code;
private int id_pesan;
    private int id_kat;
    private String result;
private String total;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
    public void setId_kue(int id_kue){
        this.id_kue=id_kue;
    }
    public int getId_kue(){
        return id_kue;
    }
    public void setNama_kue(String nama_kue){
        this.nama_kue=nama_kue;
    }
    public String getNama_kue(){
        return nama_kue;
    }
    public void setHarga(String harga){
        this.harga=harga;
    }
    public String getHarga(){
        return harga;
    }
    public void setGambar(String gambar){
        this.gambar=gambar;
    }
    public String getGambar(){
        return gambar;
    }
    public void setDesc(String desc){
        this.desc=desc;
    }
    public String getDesc(){
        return desc;
    }


    //beli
    public void setId_user(int id_user){
        this.id_user=id_user;
    }
    public int getId_user(){
        return id_user;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code){
        this.code=code;
    }
    public int getCode(){
        return code;
    }


    public void setId_pesan(int id_pesan){
        this.id_pesan=id_pesan;
    }
    public int getId_pesan(){
        return id_pesan;
    }
    public void setId_kat(int id_kat){
        this.id_kat=id_kat;
    }
    public int getId_kat(){
        return id_kat;
    }

    public void setTotal(String total){
        this.total=total;
    }
    public String getTotal(){
       return total;
    }
}
