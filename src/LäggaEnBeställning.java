import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LäggaEnBeställning {
    public static void main(String[] args) throws IOException {
        final Repository repository = new Repository();
        final Scanner scanner = new Scanner(System.in);
        final List<Kund> allCustomersList = repository.getAllCustomers();

        allCustomersList.stream().forEach(customer -> System.out.println(customer.getData()));
        System.out.println();

        System.out.println("Hej och välkommen till vår webbshop!\n" +
                "För att lägga en beställning vänligen ange ditt namn och efternamn:");
        final String name = scanner.nextLine();
        System.out.println("Ange ditt lösenord:");
        final String password = scanner.nextLine();
        System.out.println();

        final Kund customer = allCustomersList.stream().filter(c -> c.getNamn().equalsIgnoreCase(name)).findAny().orElse(null);
        if (customer != null) {
            if (customer.getLösenord().equalsIgnoreCase(password)) {
                System.out.println("Tack " + customer.getNamn() + ". Du kan nu fortsätta med din beställning.");
            } else {
                System.out.println("Felaktigt lösenord.");
                System.exit(0);
            }
        } else {
            System.out.println("Det finns ingen kund med detta namn i vår databas.");
            System.exit(0);
        }

        System.out.println("Vilken skokategori är du intresserad av? Ange siffran som motsvarar önskade kategorin:\n" +
                "Damskor : 1\nHerrskor : 2\nBarnskor : 3\nSportskor : 4\nPromenadskor : 5\nSandaler : 6");
        final String svarKategori = scanner.nextLine().trim();
        System.out.println();
        final String category;
        if (svarKategori.equals("1")) {
            category = "Damskor";
            System.out.println("Du har valt : Damskor");
        } else if (svarKategori.equals("2")) {
            category = "Herrskor";
            System.out.println("Du har valt : Herrskor");
        } else if (svarKategori.equals("3")) {
            category = "Barnskor";
            System.out.println("Du har valt : Barnskor");
        } else if (svarKategori.equals("4")) {
            category = "Sportskor";
            System.out.println("Du har valt : Sportskor");
        } else if (svarKategori.equals("5")) {
            category = "Promenadskor";
            System.out.println("Du har valt : Promenadskor");
        } else if (svarKategori.equals("6")) {
            category = "Sandaler";
            System.out.println("Du har valt : Sandaler");
        } else {
            category = null;
            System.out.println("Okänd kategori");
        }

        // skapa en lista med alla skor tillhörande någon kategori
        final List<Sko_ingår_i_kategori> allCategoriesWithRespectiveShoesList = repository.getAllCategoriesWithRespectiveShoes();
        //allCategoriesWithRespectiveShoesList.stream().forEach(s -> System.out.println(s.getSko().getId() + s.getSko().getMärke().getNamn() + s.getKategori().getNamn()));

        // välja ut den önskade kategorin med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantCategoryShoes = allCategoriesWithRespectiveShoesList.stream().
                filter(s -> s.getKategori().getNamn().equalsIgnoreCase(category)).toList();

        System.out.println("Vilket av dem nedanstående märkena skulle du vilja beställa?");
        relevantCategoryShoes.stream().map(s -> s.getSko().getMärke().getNamn()).distinct().forEach(namn -> System.out.println(namn));
        final String brand = scanner.nextLine().trim();
        System.out.println();

        // välja ut det önskade märket med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantBrandShoes = relevantCategoryShoes.stream().
                filter(s -> s.getSko().getMärke().getNamn().equalsIgnoreCase(brand)).toList();

        System.out.println("Vilken modell skulle du vilja beställa?");
        relevantBrandShoes.stream().map(s -> s.getSko().getModell().getNamn()).distinct().
                forEach(namn -> System.out.println(namn));
        final String model = scanner.nextLine().trim();
        System.out.println();

        // välja ut den önskade modellen med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantModelShoes = relevantBrandShoes.stream().
                filter(s -> s.getSko().getModell().getNamn().equalsIgnoreCase(model)).toList();

        System.out.println("Vilken färg skulle du vilja beställa?");
        relevantModelShoes.stream().map(s -> s.getSko().getFärg().getNamn()).distinct().
                forEach(namn -> System.out.println(namn));
        final String colour = scanner.nextLine().trim();
        System.out.println();

        // välja ut den önskade färgen med tillhörande skor
        final List<Sko_ingår_i_kategori> relevantColourShoes = relevantModelShoes.stream().
                filter(s -> s.getSko().getFärg().getNamn().equalsIgnoreCase(colour)).toList();

        System.out.println("Vilken storlek skulle du vilja beställa?");
        relevantColourShoes.stream().map(s -> s.getSko().getStorlek()).forEach(storlek -> System.out.println(storlek));
        final String size = scanner.nextLine().trim();
        System.out.println();

        System.out.println("Hur många par av denna sko skulle du vilja beställa?");
        final int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        // peka ut den önskade skon
        final Sko shoeToOrder = relevantColourShoes.stream().filter(s -> s.getSko().getStorlek().equalsIgnoreCase(size)).findAny().get().getSko();
        final int shoeId = shoeToOrder.getId();
        final int customerId = customer.getId();

        // kontrollera om kunden redan har en beställning hos oss
        final List<Beställning> allOrdersList = repository.getAllOrders();

        System.out.println("Du ska nu beställa följande sko:\n" + shoeToOrder.getData() + ", antal: " + amount);
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

        repository.addToCart(customerId, orderId, shoeId, amount);
    }
}