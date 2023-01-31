public class Beställning_Innehåller implements Printable{
    int id; // primärnyckel
    Beställning beställning;
    Sko sko;
    int antal;
    double delsumma;

    public Beställning_Innehåller(){
        beställning = new Beställning();
    }

    public Beställning_Innehåller(int id, Beställning beställning, Sko sko, int antal, double delsumma) {
        this.id = id;
        this.beställning = beställning;
        this.sko = sko;
        this.antal = antal;
        this.delsumma = delsumma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Beställning getBeställning() {
        return beställning;
    }

    public void setBeställning(Beställning beställning) {
        this.beställning = beställning;
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
        return getBeställning().getId() + " " + getSko().getModell().getNamn() +
                " " + getSko().getModell().getPris() + " " + getSko().getMärke().getNamn() + " " + getSko().getFärg().getNamn() +
                " " + getSko().getStorlek() + " " + getAntal() + " " + getDelsumma();
    }
}