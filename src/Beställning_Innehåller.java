public class Beställning_Innehåller {
    int beställningId;
    Sko sko;
    int antal;
    double delsumma;

    public Beställning_Innehåller(){}

    public Beställning_Innehåller(int beställningId, Sko sko, int antal, double delsumma) {
        this.beställningId = beställningId;
        this.sko = sko;
        this.antal = antal;
        this.delsumma = delsumma;
    }

    public int getBeställningId() {
        return beställningId;
    }

    public void setBeställningId(int beställningId) {
        this.beställningId = beställningId;
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
}