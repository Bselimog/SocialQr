package com.example.bigcode;

public class Etkinlikler {
    private  String Etkinlik,Ay,Gun,Yil;

    public Etkinlikler() {
    }

    public Etkinlikler(String etkinlik, String ay, String gun, String yil) {
        Etkinlik = etkinlik;
        Ay = ay;
        Gun = gun;
        Yil = yil;
    }

    public String getEtkinlik() {
        return Etkinlik;
    }

    public void setEtkinlik(String etkinlik) {
        Etkinlik = etkinlik;
    }

    public String getAy() {
        return Ay;
    }

    public void setAy(String ay) {
        Ay = ay;
    }

    public String getGun() {
        return Gun;
    }

    public void setGun(String gun) {
        Gun = gun;
    }

    public String getYil() {
        return Yil;
    }

    public void setYil(String yil) {
        Yil = yil;
    }

    @Override
    public String toString() {
        return "Etkinlikler{" +
                "Etkinlik='" + Etkinlik + '\'' +
                ", Ay='" + Ay + '\'' +
                ", Gun='" + Gun + '\'' +
                ", Yil='" + Yil + '\'' +
                '}';
    }
}
