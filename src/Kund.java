public class Kund implements Printable {

    private int id; // primärnyckel
    private String namn;
    private String adress;
    private String ort;
    private String mobilnummer;
    private String epostadress;
    private String lösenord;

    public Kund(){}

    public Kund(int id, String namn, String adress, String ort, String mobilnummer, String epostadress, String lösenord) {
        this.id = id;
        this.namn = namn;
        this.adress = adress;
        this.ort = ort;
        this.mobilnummer = mobilnummer;
        this.epostadress = epostadress;
        this.lösenord = lösenord;
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

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }

    @Override
    public String getData() {
        return id + " " + namn + " " + adress + " " + ort + " " + mobilnummer + " " + epostadress + " " + lösenord;
    }
}