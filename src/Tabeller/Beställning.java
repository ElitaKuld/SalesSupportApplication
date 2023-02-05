package Tabeller;

import java.util.Objects;

public class Beställning implements Printable {
    private int ordernummer; // primärnyckel
    private Kund kund;
    private double summa;

    public Beställning() {
    }

    public Beställning(int id, Kund kund, double summa) {
        this.ordernummer = id;
        this.kund = kund;
        this.summa = summa;
    }

    public int getId() {
        return ordernummer;
    }

    public void setId(int id) {
        this.ordernummer = id;
    }

    public Kund getKund() {
        return kund;
    }

    public void setKund(Kund kund) {
        this.kund = kund;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
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
        Beställning other = (Beställning) obj;
        return Objects.equals(ordernummer, ordernummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordernummer);
    }

    @Override
    public String getData() {
        return getId() + " " + getKund().getData() + " " + getSumma();
    }
}