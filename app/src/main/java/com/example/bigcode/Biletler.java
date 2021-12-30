package com.example.bigcode;

public class Biletler {
    private  String ReferansNumarasi,EtkinlikAdi,EtkinlikTarihi;

    public Biletler() {
    }

    public Biletler(String referansNumarasi, String etkinlikAdi, String etkinlikTarihi) {
        ReferansNumarasi = referansNumarasi;
        EtkinlikAdi = etkinlikAdi;
        EtkinlikTarihi = etkinlikTarihi;
    }

    public String getReferansNumarasi() {
        return ReferansNumarasi;
    }

    public void setReferansNumarasi(String referansNumarasi) {
        ReferansNumarasi = referansNumarasi;
    }

    public String getEtkinlikAdi() {
        return EtkinlikAdi;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        EtkinlikAdi = etkinlikAdi;
    }

    public String getEtkinlikTarihi() {
        return EtkinlikTarihi;
    }

    public void setEtkinlikTarihi(String etkinlikTarihi) {
        EtkinlikTarihi = etkinlikTarihi;
    }

    @Override
    public String toString() {
        return "Biletler{" +
                "ReferansNumarasi='" + ReferansNumarasi + '\'' +
                ", EtkinlikAdi='" + EtkinlikAdi + '\'' +
                ", EtkinlikTarihi='" + EtkinlikTarihi + '\'' +
                '}';
    }
}
