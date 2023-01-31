import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {


    // Hämta alla beställningar från databasen
    List<Beställning> getAllOrders() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));
        List<Beställning> beställningsLista = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select Beställning.ordernummer, Beställning.summa, " +
                             "Kund.id, Kund.namn, Kund.adress, Kund.ort, Kund.mobilnummer, Kund.epostadress, Kund.lösenord " +
                             "from Beställning, Kund where beställning.KundId=Kund.id;")
        ) {

            while (resultSet.next()) {
                Beställning beställning = new Beställning();
                Kund kund = new Kund();

                beställning.setId(resultSet.getInt("Beställning.ordernummer"));
                beställning.setSumma(resultSet.getDouble("Beställning.summa"));

                kund.setId(resultSet.getInt("Kund.id"));
                kund.setNamn(resultSet.getString("Kund.namn"));
                kund.setAdress(resultSet.getString("Kund.adress"));
                kund.setOrt(resultSet.getString("Kund.ort"));
                kund.setMobilnummer(resultSet.getString("Kund.mobilnummer"));
                kund.setEpostadress(resultSet.getString("Kund.epostadress"));
                kund.setLösenord(resultSet.getString("Kund.lösenord"));

                beställning.setKund(kund);

                beställningsLista.add(beställning);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return beställningsLista;
    }

    List<Beställning_Innehåller> getOrdersContent() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));
        List<Beställning_Innehåller> beställningInnehållerLista = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select Beställning_Innehåller.id, Beställning_Innehåller.beställningId, " +
                             "Beställning_Innehåller.antal, beställning_innehåller.delsumma," +
                             "Sko.id, Sko.storlek, Sko.antal_i_lager," +
                             "Märke.id, Märke.namn, " +
                             "Modell.id, Modell.namn, Modell.pris," +
                             "Färg.id, Färg.namn " +
                             "from Beställning_Innehåller, Sko, Märke, Modell, Färg where Sko.id=beställning_innehåller.SkoId " +
                             "and Sko.färgId=Färg.id and Sko.modell_Id=Modell.id and Sko.märkeId=Märke.id")
        ) {

            while (resultSet.next()) {
                Beställning_Innehåller beställningInnehåller = new Beställning_Innehåller();
                Sko sko = new Sko();

                beställningInnehåller.setId(resultSet.getInt("Beställning_Innehåller.id"));
                beställningInnehåller.getBeställning().setId(resultSet.getInt("Beställning_Innehåller.beställningId"));
                beställningInnehåller.setAntal(resultSet.getInt("Beställning_Innehåller.antal"));
                beställningInnehåller.setDelsumma(resultSet.getDouble("beställning_innehåller.delsumma"));

                sko.setId(resultSet.getInt("Sko.id"));
                sko.setStorlek(resultSet.getString("Sko.storlek"));
                sko.setAntal_i_lager(resultSet.getInt("Sko.antal_i_lager"));
                sko.getMärke().setId(resultSet.getInt("Märke.id"));
                sko.getMärke().setNamn(resultSet.getString("Märke.namn"));
                sko.getModell().setId(resultSet.getInt("Modell.id"));
                sko.getModell().setNamn(resultSet.getString("Modell.namn"));
                sko.getModell().setPris(resultSet.getDouble("Modell.pris"));
                sko.getFärg().setId(resultSet.getInt("Färg.id"));
                sko.getFärg().setNamn(resultSet.getString("Färg.namn"));

                beställningInnehåller.setSko(sko);

                beställningInnehållerLista.add(beställningInnehåller);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return beställningInnehållerLista;
    }
}