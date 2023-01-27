import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    List<Beställning> getAllOrders() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));
        List<Beställning> beställningsLista = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select Beställning.ordernummer, Beställning.summa, Kund.id, Kund.namn, Kund.adress, Kund.ort, Kund.mobilnummer, Kund.epostadress, Beställning_Innehåller.antal,\n" +
                             "Beställning_Innehåller.skoId, Märke.namn, Modell.namn, Färg.namn, Sko.storlek, beställning_innehåller.delsumma, Beställning_Innehåller.beställningId\n" +
                             "from Beställning, Kund, Beställning_Innehåller, Sko, Märke, Modell, Färg where beställning.KundId=Kund.id and Beställning_Innehåller.beställningId=Beställning.ordernummer \n" +
                             "and Sko.id=beställning_innehåller.SkoId and Sko.färgId=Färg.id and Sko.modell_Id=Modell.id and Sko.märkeId=Märke.id")
        ) {

            while (resultSet.next()) {
                Beställning beställning = new Beställning();
                Beställning_Innehåller beställningInnehåller = new Beställning_Innehåller();
                Kund kund = new Kund();

                beställning.setId(resultSet.getInt("Beställning.ordernummer"));
                beställning.setSumma(resultSet.getDouble("Beställning.summa"));

                kund.setNamn(resultSet.getString("Kund.namn"));
                kund.setAdress(resultSet.getString("Kund.adress"));
                kund.setOrt(resultSet.getString("Kund.ort"));
                kund.setMobilnummer(resultSet.getString("Kund.mobilnummer"));
                kund.setEpostadress(resultSet.getString("Kund.epostadress"));

                beställning.setKund(kund);

                beställningInnehåller.setBeställningId(resultSet.getInt("Beställning_Innehåller.beställningId"));
                if (beställning.getId()==beställningInnehåller.getBeställningId()){
                    BeställdVara beställdVara = new BeställdVara();
                    beställdVara.setAntal(resultSet.getInt("Beställning_Innehåller.antal"));
                    beställdVara.setDelsumma(resultSet.getDouble("Beställning_innehåller.delsumma"));

                    Sko sko = new Sko();
                    sko.setMärke(resultSet.getString("Märke.namn"));
                    sko.setModell(resultSet.getString("Modell.namn"));
                    sko.setFärg(resultSet.getString("Färg.namn"));
                    sko.setStorlek(resultSet.getString("Sko.storlek"));

                    beställdVara.setSko(sko);

                    beställning.beställdaVaror.add(beställdVara);

                }

                beställningsLista.add(beställning);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return beställningsLista;
    }
}