package com.yogiputra.vynycakeshop.model;

/**
 * Created by koba on 12/17/15.
 */
public class NewUser {
    public NewUser() {

    }

    private String message;
    private int code;
    private int id_user;
    private String nohp;
private String nama;
    private String alamat;




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
    public void setNama(String nama){
        this.nama=nama;
    }
    public String getNama(){
        return nama;
    }
    public void setAlamat(String alamat){
        this.alamat=alamat;
    }
    public String getAlamat(){
        return alamat;
    }

    public void setId_user(int id_user){
        this.id_user=id_user;
    }
    public int getId_user(){
        return id_user;
    }
    public void setNohp(String nohp){
        this.nohp=nohp;
    }
    public String getNohp(){
        return nohp;
    }
}


