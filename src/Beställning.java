import java.util.ArrayList;
import java.util.List;


public class Beställning implements Printable{
    int ordernummer; // primärnyckel
    Kund kund;
    List<BeställdVara> beställdaVaror;
    double summa;

    public Beställning() {
        kund = new Kund();
        beställdaVaror = new ArrayList<>();
    }

    public Beställning(int id, Kund kund, double summa) {
        this.ordernummer = id;
        this.kund = kund;
        beställdaVaror = new ArrayList<>();
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

    public List<BeställdVara> getBeställdaVaror() {
        return beställdaVaror;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    @Override
    public String print() {
        return getId() + " " + getKund().print() + " " + getBeställdaVaror() + " " + getSumma();
    }
}