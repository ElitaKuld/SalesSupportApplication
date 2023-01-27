public class Modell {
    int id; // primärnyckel
    String namn;
    double pris;

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
}