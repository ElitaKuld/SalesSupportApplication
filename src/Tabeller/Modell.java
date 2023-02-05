package Tabeller;

import java.util.Objects;

public class Modell {
    private int id; // primärnyckel
    private String namn;
    private double pris;

    public Modell(){}

    public Modell(int id, String namn, double pris) {
        this.id = id;
        this.namn = namn;
        this.pris = pris;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
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
        Modell other = (Modell) obj;
        return Objects.equals(id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}