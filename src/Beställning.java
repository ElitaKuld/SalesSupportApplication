import java.util.ArrayList;
import java.util.List;

public class Beställning implements Printable{
    int id;
    Kund kund;
    List<BeställdVara> beställdaVaror = new ArrayList<>();
    double summa;

    public Beställning() {
    }

    public Beställning(int id, Kund kund, double summa) {
        this.id = id;
        this.kund = kund;
        this.summa = summa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return null;
    }
}