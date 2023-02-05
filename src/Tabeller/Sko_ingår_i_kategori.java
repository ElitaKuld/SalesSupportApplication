package Tabeller;

import Tabeller.Kategori;
import Tabeller.Sko;

public class Sko_ingår_i_kategori {
    private int id;
    private Sko sko;
    private Kategori kategori;

    public Sko_ingår_i_kategori() {
        sko = new Sko();
        kategori = new Kategori();
    }

    public Sko_ingår_i_kategori(int id, Sko sko, Kategori kategori) {
        this.id = id;
        this.sko = sko;
        this.kategori = kategori;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sko getSko() {
        return sko;
    }

    public void setSko(Sko sko) {
        this.sko = sko;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
}