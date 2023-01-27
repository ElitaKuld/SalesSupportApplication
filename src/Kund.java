public class Kund implements Printable{

    String namn;
    String adress;
    String ort;
    String mobilnummer;
    String epostadress;

    public Kund(){}

    public Kund(String namn, String adress, String ort, String mobilnummer, String epostadress) {
        this.namn = namn;
        this.adress = adress;
        this.ort = ort;
        this.mobilnummer = mobilnummer;
        this.epostadress = epostadress;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getMobilnummer() {
        return mobilnummer;
    }

    public void setMobilnummer(String mobilnummer) {
        this.mobilnummer = mobilnummer;
    }

    public String getEpostadress() {
        return epostadress;
    }

    public void setEpostadress(String epostadress) {
        this.epostadress = epostadress;
    }

    @Override
    public String print() {
        return namn + " " + adress + " " + ort + " " + mobilnummer + " " + epostadress;
    }
}