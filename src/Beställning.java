
public class Beställning implements Printable {
    private int ordernummer; // primärnyckel
    private Kund kund;
    private double summa;

    public Beställning() {
        kund = new Kund();
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
    public String getData() {
        return getId() + " " + getKund().getData() + " " + getSumma();
    }
}