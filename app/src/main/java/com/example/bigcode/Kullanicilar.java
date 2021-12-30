package com.example.bigcode;

import android.media.Image;
import android.provider.MediaStore;

import de.hdodenhof.circleimageview.CircleImageView;

public class Kullanicilar {

    private  String dogumtarihi,isim,mail,sifre,soyisim,telefon,resim;

    public Kullanicilar() {
    }

    public Kullanicilar(String dogumtarihi, String isim, String mail, String sifre, String soyisim, String telefon, String resim) {
        this.dogumtarihi = dogumtarihi;
        this.isim = isim;
        this.mail = mail;
        this.sifre = sifre;
        this.soyisim = soyisim;
        this.telefon = telefon;
        this.resim = resim;
    }

    public String getDogumtarihi() {
        return dogumtarihi;
    }

    public void setDogumtarihi(String dogumtarihi) {
        this.dogumtarihi = dogumtarihi;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    @Override
    public String toString() {
        return "Kullanicilar{" +
                "dogumtarihi='" + dogumtarihi + '\'' +
                ", isim='" + isim + '\'' +
                ", mail='" + mail + '\'' +
                ", sifre='" + sifre + '\'' +
                ", soyisim='" + soyisim + '\'' +
                ", telefon='" + telefon + '\'' +
                ", resim='" + resim + '\'' +
                '}';
    }
}
