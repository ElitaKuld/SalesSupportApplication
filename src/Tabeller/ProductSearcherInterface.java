package Tabeller;

@FunctionalInterface
public interface ProductSearcherInterface {
    boolean search (Beställning_Innehåller orderWithContent, String wordToSearch);
}
