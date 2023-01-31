public class BeställdVara implements Printable{
    Sko sko;
    int antal;
    double delsumma;

    public BeställdVara() {
    }

    public BeställdVara(Sko sko, int antal, double delsumma) {
        this.sko = sko;
        this.antal = antal;
        this.delsumma = delsumma;
    }

    public Sko getSko() {
        return sko;
    }

    public void setSko(Sko sko) {
        this.sko = sko;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public double getDelsumma() {
        return delsumma;
    }

    public void setDelsumma(double delsumma) {
        this.delsumma = delsumma;
    }


    @Override
    public String print() {
        return antal + " " + sko.getMärke().getNamn() + " " + sko.getModell().getNamn() + " " +
                sko.getFärg().getNamn() + " " + sko.getStorlek() + " " + delsumma;
    }
}