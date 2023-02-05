import Tabeller.Beställning;
import Tabeller.Kund;
import Tabeller.Sko;
import Tabeller.Sko_ingår_i_kategori;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LäggaEnBeställning {
    public static void main(String[] args) throws IOException {
        final LäggaEnBeställning läggaEnBeställning = new LäggaEnBeställning();
        final Repository repository = new Repository();
        final Scanner scanner = new Scanner(System.in);
        final List<Kund> allCustomersList = repository.getAllCustomers();

        // allCustomersList.forEach(customer -> System.out.println(customer.getData()));
        // System.out.println();

        System.out.println("Hej och välkommen till vår webbshop!\n" +
                "För att lägga en beställning vänligen ange ditt namn och efternamn:");
        String name = scanner.nextLine();
        System.out.println("Ange ditt lösenord:");
        String password = scanner.nextLine();
        System.out.println();

        // Kontrollera att användaren anger sitt namn och lösenord
        while (name.equals("") || password.equals("")) {
            System.out.println("Namnet eller/och lösenordet kan inte vara tomt.\n" +
                    "Vänligen ange ditt namn och efternamn:");
            name = scanner.nextLine();
            System.out.println("Ange ditt lösenord:");
            password = scanner.nextLine();
            System.out.println();
        }

        final String finalName = name;
        final String finalPassword = password;
        final Kund customer = allCustomersList.stream().filter(c -> c.getNamn().equalsIgnoreCase(finalName)).findAny().orElse(null);
        if (customer != null) {
            if (customer.getLösenord().equalsIgnoreCase(finalPassword)) {
                System.out.println("Tack " + customer.getNamn() + ". Du kan nu fortsätta med din beställning.");
            } else {
                System.out.println("Felaktigt lösenord.");
                System.exit(0);
            }
        } else {
            System.out.println("Det finns ingen kund med detta namn i vår databas.");
            System.exit(0);
        }

        String category = läggaEnBeställning.printCategoryOptions(scanner);
        while (category==null){
            System.out.println("Okänd kategori. Prova gärna igen!");
            category = läggaEnBeställning.printCategoryOptions(scanner);
        }
        final String finalCategory = category;

        // skapa en lista med alla skor tillhörande någon kategori
        final List<Sko_ingår_i_kategori> allCategoriesWithRespectiveShoesList = repository.getAllCategoriesWithRespectiveShoes();

        // välja ut den önskade kategorin med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantCategoryShoes = allCategoriesWithRespectiveShoesList.stream().
                filter(s -> s.getKategori().getNamn().equalsIgnoreCase(finalCategory)).toList();

        System.out.println("Vilket av dem nedanstående märkena skulle du vilja beställa?");
        relevantCategoryShoes.stream().map(s -> s.getSko().getMärke().getNamn()).distinct().forEach(System.out::println);
        final String brand = scanner.nextLine().trim();
        System.out.println();

        // välja ut det önskade märket med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantBrandShoes = relevantCategoryShoes.stream().
                filter(s -> s.getSko().getMärke().getNamn().equalsIgnoreCase(brand)).toList();

        System.out.println("Vilken modell skulle du vilja beställa?");
        relevantBrandShoes.stream().map(s -> s.getSko().getModell().getNamn()).distinct().
                forEach(System.out::println);
        final String model = scanner.nextLine().trim();
        System.out.println();

        // välja ut den önskade modellen med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantModelShoes = relevantBrandShoes.stream().
                filter(s -> s.getSko().getModell().getNamn().equalsIgnoreCase(model)).toList();

        System.out.println("Vilken färg skulle du vilja beställa?");
        relevantModelShoes.stream().map(s -> s.getSko().getFärg().getNamn()).distinct().
                forEach(System.out::println);
        final String colour = scanner.nextLine().trim();
        System.out.println();

        // välja ut den önskade färgen med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantColourShoes = relevantModelShoes.stream().
                filter(s -> s.getSko().getFärg().getNamn().equalsIgnoreCase(colour)).toList();

        System.out.println("Vilken storlek skulle du vilja beställa?");
        relevantColourShoes.stream().map(s -> s.getSko().getStorlek()).forEach(System.out::println);
        final String size = scanner.nextLine().trim();
        System.out.println();

        int amount = läggaEnBeställning.printAmountQuestion(scanner);

        // peka ut den önskade skon
        final Sko shoeToOrder = relevantColourShoes.stream().filter(s -> s.getSko().getStorlek().equalsIgnoreCase(size)).findAny().get().getSko();
        final int shoeId = shoeToOrder.getId();
        final int customerId = customer.getId();

        // Kontrollera att kunden inte försöker beställa fler skor än det finns i lager
        while (shoeToOrder.getAntal_i_lager()<amount){
            System.out.println("Du försöker beställa fler par av denna sko än vi har i lager.\n" +
                    "Antal i lager: " + shoeToOrder.getAntal_i_lager());
            amount = läggaEnBeställning.printAmountQuestion(scanner);
        }

        final int finalAmount = amount;

        // kontrollera om kunden redan har en beställning hos oss
        final List<Beställning> allOrdersList = repository.getAllOrders();

        System.out.println("Du ska nu beställa följande skon:\n" + shoeToOrder.getData() + ", antal: " + finalAmount);
        System.out.println();

        // hitta en beställning som tillhör kunden
        final Beställning order = allOrdersList.stream().filter(o -> o.getKund().getId() == customerId).findAny().orElse(null);

        final int orderId;
        if (order != null) {
            System.out.println("Det ser ut som att du redan har en beställning hos oss. Vill du lägga till din valda sko på den befintliga beställningen?\n" +
                    "JA : om du vill göra det\n" +
                    "NEJ : om du vill göra en ny beställning");
            if (scanner.nextLine().trim().equals("JA")) {
                orderId = order.getId();
            } else orderId = 0;
        } else orderId = 0;
        System.out.println();

        repository.addToCart(customerId, orderId, shoeId, finalAmount);
    }

    public String printCategoryOptions(Scanner scanner){
        System.out.println("""
                Vilken skokategori är du intresserad av? Ange siffran som motsvarar önskade kategorin:
                Damskor : 1
                Herrskor : 2
                Barnskor : 3
                Sportskor : 4
                Promenadskor : 5
                Sandaler : 6""");
        String svarKategori = scanner.nextLine().trim();
        System.out.println();
        String category;
        switch (svarKategori) {
            case "1" -> {
                category = "Damskor";
                System.out.println("Du har valt : Damskor");
            }
            case "2" -> {
                category = "Herrskor";
                System.out.println("Du har valt : Herrskor");
            }
            case "3" -> {
                category = "Barnskor";
                System.out.println("Du har valt : Barnskor");
            }
            case "4" -> {
                category = "Sportskor";
                System.out.println("Du har valt : Sportskor");
            }
            case "5" -> {
                category = "Promenadskor";
                System.out.println("Du har valt : Promenadskor");
            }
            case "6" -> {
                category = "Sandaler";
                System.out.println("Du har valt : Sandaler");
            }
            default -> {
                category = null;
            }
        }
        return category;
    }

    public int printAmountQuestion(Scanner scanner){
        System.out.println("Hur många par av denna sko skulle du vilja beställa?");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return amount;
    }
}