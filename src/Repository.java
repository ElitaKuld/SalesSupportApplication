import Tabeller.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    // Hämta alla kunder från databasen
    List<Kund> getAllCustomers() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));
        List<Kund> kundLista = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select Kund.id, Kund.namn, Kund.adress, Kund.ort, Kund.mobilnummer, Kund.epostadress, Kund.lösenord " +
                             "from Kund;")
        ) {

            while (resultSet.next()) {
                Kund kund = new Kund();

                kund.setId(resultSet.getInt("Kund.id"));
                kund.setNamn(resultSet.getString("Kund.namn"));
                kund.setAdress(resultSet.getString("Kund.adress"));
                kund.setOrt(resultSet.getString("Kund.ort"));
                kund.setMobilnummer(resultSet.getString("Kund.mobilnummer"));
                kund.setEpostadress(resultSet.getString("Kund.epostadress"));
                kund.setLösenord(resultSet.getString("Kund.lösenord"));

                kundLista.add(kund);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return kundLista;
    }

    // lägga en beställning
    void addToCart(int customerId, int orderId, int productId, int amount) throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));

             CallableStatement callableStatement = connection.prepareCall("call addToCart (?,?,?,?,?)");

        ) {
            callableStatement.setInt(1, customerId);
            callableStatement.setInt(2, orderId);
            callableStatement.setInt(3, productId);
            callableStatement.setInt(4, amount);
            callableStatement.registerOutParameter(5, Types.DOUBLE);

            callableStatement.executeQuery();
            double totals = callableStatement.getDouble(5);

            System.out.println("Din beställning är nu genomförd. Summa att betala: " + totals +
                    "\nVi har skickat en bekräftelse till din email-adress." +
                    "\nTack för att du har handlat hos oss!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
    }

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

    List<Beställning_Innehåller> getAllOrdersAndTheirContent() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));
        List<Beställning_Innehåller> beställningInnehållerLista = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select Beställning_Innehåller.id, Beställning_innehåller.antal, beställning_innehåller.delsumma,\n" +
                             "Beställning.ordernummer, Beställning.summa, \n" +
                             "Kund.id, Kund.namn, Kund.adress, Kund.ort, Kund.mobilnummer, Kund.epostadress, Kund.lösenord,\n" +
                             "Sko.id, Sko.storlek, Sko.antal_i_lager,\n" +
                             "Märke.id, Märke.namn,\n" +
                             "Modell.id, Modell.namn, Modell.pris,\n" +
                             "Färg.id, Färg.namn\n" +
                             "from Beställning_Innehåller, Beställning, Kund, Sko, Märke, Modell, Färg\n" +
                             "where Beställning_Innehåller.beställningId=Beställning.ordernummer and Beställning.kundId=Kund.id " +
                             "and Beställning_Innehåller.skoId=Sko.id \n" +
                             "and Sko.märkeId=Märke.id and Sko.modell_Id=Modell.id and Sko.färgId=Färg.id;")
        ) {

            while (resultSet.next()) {
                Beställning_Innehåller beställningInnehåller = new Beställning_Innehåller();
                Beställning beställning = new Beställning();
                Kund kund = new Kund();
                Sko sko = new Sko();

                beställningInnehåller.setId(resultSet.getInt("Beställning_Innehåller.id"));
                beställningInnehåller.setAntal(resultSet.getInt("Beställning_Innehåller.antal"));
                beställningInnehåller.setDelsumma(resultSet.getDouble("beställning_innehåller.delsumma"));

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
                beställningInnehåller.setBeställning(beställning);

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

    List<Sko_ingår_i_kategori> getAllCategoriesWithRespectiveShoes() throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/Settings.properties"));
        List<Sko_ingår_i_kategori> kategoriMedSkorLista = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(properties.getProperty("connectionString"), properties.getProperty("name"),
                properties.getProperty("password"));
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "select Sko_ingår_i_kategori.id, " +
                             "Sko.id, Sko.storlek, Sko.antal_i_lager, " +
                             "Märke.id, Märke.namn, " +
                             "Modell.id, Modell.namn, Modell.pris, " +
                             "Färg.id, Färg.namn, " +
                             "Kategori.id, Kategori.namn " +
                             "from Sko_ingår_i_kategori, Sko, Kategori, Märke, Modell, Färg " +
                             "where Sko_ingår_i_kategori.kategoriId=Kategori.id and Sko_ingår_i_kategori.skoId=Sko.id " +
                             "and sko.märkeId=Märke.id and sko.modell_Id=Modell.id and sko.färgId=Färg.id")
        ) {

            while (resultSet.next()) {
                Sko_ingår_i_kategori skoIngårIKategori = new Sko_ingår_i_kategori();
                Sko sko = new Sko();
                Kategori kategori = new Kategori();

                skoIngårIKategori.setId(resultSet.getInt("Sko_ingår_i_kategori.id"));

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

                skoIngårIKategori.setSko(sko);

                kategori.setId(resultSet.getInt("Kategori.id"));
                kategori.setNamn(resultSet.getString("Kategori.namn"));

                skoIngårIKategori.setKategori(kategori);

                kategoriMedSkorLista.add(skoIngårIKategori);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return kategoriMedSkorLista;
    }
}