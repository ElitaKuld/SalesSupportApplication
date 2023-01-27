public class Sko implements Printable{
    String märke;
    String modell;
    String färg;
    String storlek;

    public Sko(){}

    public Sko(String märke, String modell, String färg, String storlek) {
        this.märke = märke;
        this.modell = modell;
        this.färg = färg;
        this.storlek = storlek;
    }

    public String getMärke() {
        return märke;
    }

    public void setMärke(String märke) {
        this.märke = märke;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public String getStorlek() {
        return storlek;
    }

    public void setStorlek(String storlek) {
        this.storlek = storlek;
    }

    @Override
    public String print() {
        return getMärke() + " " + getModell() + " " + getFärg() + " " + getStorlek();
    }
}