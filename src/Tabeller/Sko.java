package Tabeller;

import java.util.Objects;

public class Sko implements Printable {
    private int id; // primärnyckel
    private Märke märke;
    private Modell modell;
    private Färg färg;
    private String storlek;
    private int antal_i_lager;

    public Sko(){
        märke = new Märke();
        modell = new Modell();
        färg = new Färg();
    }

    public Sko(int id, Märke märke, Modell modell, Färg färg, String storlek, int antal_i_lager) {
        this.id = id;
        this.märke = märke;
        this.modell = modell;
        this.färg = färg;
        this.storlek = storlek;
        this.antal_i_lager = antal_i_lager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Märke getMärke() {
        return märke;
    }

    public void setMärke(Märke märke) {
        this.märke = märke;
    }

    public Modell getModell() {
        return modell;
    }

    public void setModell(Modell modell) {
        this.modell = modell;
    }

    public Färg getFärg() {
        return färg;
    }

    public void setFärg(Färg färg) {
        this.färg = färg;
    }

    public String getStorlek() {
        return storlek;
    }

    public void setStorlek(String storlek) {
        this.storlek = storlek;
    }

    public int getAntal_i_lager() {
        return antal_i_lager;
    }

    public void setAntal_i_lager(int antal_i_lager) {
        this.antal_i_lager = antal_i_lager;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sko other = (Sko) obj;
        return Objects.equals(id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String getData() {
        return "Märke: " + getMärke().getNamn() + ", modell: " + getModell().getNamn() + ", färg: " + getFärg().getNamn() + ", storlek: "
                + getStorlek();
    }
}