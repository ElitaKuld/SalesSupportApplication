import Tabeller.*;

import java.io.IOException;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class SäljstödApplikation {

    final ProductSearcherInterface lookingForColour = (order, word) -> order.getSko().getFärg().getNamn().equals(word);
    final ProductSearcherInterface lookingForSize = (order, word) -> order.getSko().getStorlek().equals(word);
    final ProductSearcherInterface lookingForBrand = (order, word) -> order.getSko().getMärke().getNamn().equals(word);

    public void searchForProduct(String wordSearchParameter, ProductSearcherInterface psi, List<Beställning_Innehåller> allOrdersAndTheirContentList) {
        allOrdersAndTheirContentList.stream().filter(b -> psi.search(b, wordSearchParameter)).
                map(beställningInnehåller -> beställningInnehåller.getBeställning().getKund()).distinct().
                forEach(k -> System.out.println("Kund: " + k.getNamn() +
                        " , adress: " + k.getAdress() + " , ort: " + k.getOrt()));
    }

    public static void main(String[] args) throws IOException {
        final SäljstödApplikation salesSupportApplication = new SäljstödApplikation();
        final Repository repository = new Repository();
        final List<Beställning_Innehåller> allOrdersAndTheirContentList = repository.getAllOrdersAndTheirContent();
        final Scanner scanner = new Scanner(System.in);
        final Collator collator = Collator.getInstance(new Locale("sv", "SE"));
        // allOrdersAndTheirContentList.stream().forEach(b -> System.out.println(b.getData()));
        // System.out.println();

        System.out.println("Hej och välkommen till vår säljstödsapplikation!");

        while (true) {
            System.out.println("""
                    Vilken rapport skulle du vilja titta på? Ange siffran som motsvarar önskade rapporten:
                    1 : rapport som listar alla kunder som har handlat varor hos oss.
                    2 : rapport som listar alla kunder och hur många ordrar varje kund har lagt.
                    3 : rapport som listar alla kunder och hur mycket pengar varje kund, sammanlagt, har beställt för.
                    4 : rapport som listar beställningsvärde per ort.
                    5 : topplista över de mest sålda produkterna som listar varje modell och hur många ex som har sålts av den modellen.""");

            int answer = 0;
            try {
                answer = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input.");
                System.exit(0);
            }
            scanner.nextLine();
            System.out.println();

            final int reportNumber = answer;
            // En rapport som listar alla kunder, med namn och adress, som har handlat varor i en viss storlek, av en viss färg eller av ett visst märke.
            if (reportNumber == 1) {
                System.out.println("""
                        Vilket attribut vill du söka på? Ange:
                        1 : om du vill söka på färg.
                        2 : om du vill söka på storlek.
                        3 : om du vill söka på märke.""");

                try {
                    answer = scanner.nextInt();
                }
                catch (InputMismatchException e){
                    System.out.println("Invalid input.");
                    System.exit(0);
                }
                scanner.nextLine();
                System.out.println();

                final int answerNumber = answer;
                if (answerNumber == 1) {
                    System.out.println("Vilken färg är du intresserad av: ");
                    final List<String> allColoursList = allOrdersAndTheirContentList.stream().map(Beställning_Innehåller::getSko).map(Sko::getFärg).
                            map(Färg::getNamn).distinct().sorted(collator).toList();
                    allColoursList.forEach(System.out::println);
                    final String colour = scanner.nextLine().trim();
                    System.out.println();

                    if (allColoursList.stream().noneMatch(färg -> färg.equals(colour))) { // opposite to anyMatch
                        System.out.println("Fel angiven färg.");
                        System.exit(0);
                    } else {
                        System.out.println("Nedanstående kunder har handlat varor i följande färg: " + colour);
                        salesSupportApplication.searchForProduct(colour, salesSupportApplication.lookingForColour, allOrdersAndTheirContentList);
                    }


                } else if (answerNumber == 2) {
                    System.out.println("Vilken storlek är du intresserad av: ");
                    final List<String> allSizesList = allOrdersAndTheirContentList.stream().map(Beställning_Innehåller::getSko).map(Sko::getStorlek).
                            distinct().sorted().toList();
                    allSizesList.forEach(System.out::println);
                    final String size = scanner.nextLine();
                    System.out.println();

                    if (allSizesList.stream().noneMatch(storlek -> storlek.equals(size))) { // opposite to anyMatch
                        System.out.println("Fel angiven storlek.");
                        System.exit(0);
                    } else {
                        System.out.println("Nedanstående kunder har handlat varor i följande storlek: " + size);
                        salesSupportApplication.searchForProduct(size, salesSupportApplication.lookingForSize, allOrdersAndTheirContentList);
                    }

                } else if (answerNumber == 3) {
                    System.out.println("Vilket märke är du intresserad av: ");
                    final List<String> allBrandsList = allOrdersAndTheirContentList.stream().map(Beställning_Innehåller::getSko).map(Sko::getMärke).
                            map(Märke::getNamn).sorted(collator).distinct().toList();
                    allBrandsList.forEach(System.out::println);
                    final String brand = scanner.nextLine();
                    System.out.println();

                    if (allBrandsList.stream().noneMatch(märke -> märke.equals(brand))) { // opposite to anyMatch
                        System.out.println("Felt angivet märke.");
                        System.exit(0);
                    } else {
                        System.out.println("Nedanstående kunder har handlat varor av följande märke: " + brand);
                        salesSupportApplication.searchForProduct(brand, salesSupportApplication.lookingForBrand, allOrdersAndTheirContentList);
                    }

                } else {
                    System.out.println("Denna angivna siffra motsvarar inte någon sökparameter.");
                    System.exit(0);
                }


                // En rapport som listar alla kunder och hur många ordrar varje kund har lagt. Skriv ut namn och sammanlagda antalet ordrar för varje kund.
            } else if (reportNumber == 2) {
                System.out.println("Så många ordrar har varit lagda av varje kund:");

                final List<Beställning> allOrdersList = allOrdersAndTheirContentList.stream().
                        map(Beställning_Innehåller::getBeställning).distinct().toList();

                // grouping by customer
                final Map<Kund, List<Beställning>> groupedByCustomerMap = allOrdersList.stream().collect(Collectors.
                        groupingBy(Beställning::getKund));

                final Collection<List<Beställning>> justOrdersMap = groupedByCustomerMap.values();

                final Map<String, Integer> groupedByCustomerNameMap = justOrdersMap.stream().collect(Collectors.toMap(lista -> lista.get(0).
                        getKund().getNamn(), lista -> lista.size()));

                // Sorting customers by name
                final Map<String, Integer> groupedByCustomerNameMapSorted = new TreeMap<>(groupedByCustomerNameMap);

                groupedByCustomerNameMapSorted.forEach((k, v) -> System.out.println(k + " : " + v));


                // En rapport som listar alla kunder och hur mycket pengar varje kund, sammanlagt, har beställt för. Skriv ut varje kunds namn och summa.
            } else if (reportNumber == 3) {
                System.out.println("Så mycket pengar har varje kund beställt för:");

                final List<Beställning> allOrdersList = allOrdersAndTheirContentList.stream().
                        map(Beställning_Innehåller::getBeställning).distinct().toList();

                // grouping by customer
                final Map<Kund, List<Beställning>> groupedByCustomerMap = allOrdersList.stream().collect(Collectors.
                        groupingBy(Beställning::getKund));

                // creating new map with customer with respective amounts of money spent
                final Map<String, Double> groupedByCustomerNameMap = new HashMap<>();
                groupedByCustomerMap.forEach((k, v) -> groupedByCustomerNameMap.put(k.getNamn(), v.stream().mapToDouble(Beställning::getSumma).sum()));

                // Sorting customers by name
                final Map<String, Double> groupedByCustomerNameMapSorted = new TreeMap<>(groupedByCustomerNameMap);

                groupedByCustomerNameMapSorted.forEach((k, v) -> System.out.println(k + " : " + v));


                // En rapport som listar beställningsvärde per ort. Skriv ut orternas namn och summa.
            } else if (reportNumber == 4) {
                System.out.println("Så mycket pengar har det blivit spenderat per ort:");

                final List<Beställning> allOrdersList = allOrdersAndTheirContentList.stream().
                        map(Beställning_Innehåller::getBeställning).distinct().toList();

                // grouping by city
                final Map<String, List<Beställning>> groupedByCityMap = allOrdersList.stream().collect(Collectors.
                        groupingBy(beställning -> beställning.getKund().getOrt()));

                // creating new map with cities and how much money was spent
                final Map<String, Double> groupedByCityMapWithTotals = new HashMap<>();
                groupedByCityMap.forEach((k, v) -> groupedByCityMapWithTotals.put(k, v.stream().mapToDouble(Beställning::getSumma).sum()));

                // Sorting cities by name
                final Map<String, Double> groupedByCityMapWithTotalsSorted = new TreeMap<>(groupedByCityMapWithTotals);

                groupedByCityMapWithTotalsSorted.forEach((k, v) -> System.out.println(k + " : " + v));


                // En topplista över de mest sålda produkterna som listar varje modell och hur många ex som
                // har sålts av den modellen. Skriv ut namn på modellen och hur många ex som sålts.
            } else if (reportNumber == 5) {
                System.out.println("Topplista över sålda produkter:");

                // grouping by model
                final Map<Modell, List<Beställning_Innehåller>> groupedByModelMap = allOrdersAndTheirContentList.stream().
                        collect(Collectors.groupingBy(beställningInnehåller -> beställningInnehåller.getSko().getModell()));

                //groupedByModelMap.forEach((k, v) -> System.out.println(k.getNamn() + " : " + v));

                // creating new map with models and respective pairs of shoes sold
                final Map<String, Integer> groupedByModelNameMap = new HashMap<>();
                groupedByModelMap.forEach((k, v) -> groupedByModelNameMap.put(k.getNamn(), v.stream().mapToInt(Beställning_Innehåller::getAntal).sum()));

                groupedByModelNameMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                        forEach(System.out::println);

            } else {
                System.out.println("Denna angivna siffra motsvarar inte någon rapport.");
                System.exit(0);
            }
            System.out.println();
        }
    }
}