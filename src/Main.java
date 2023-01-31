import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Databas databas = new Databas();
        List<Beställning> listaPåAllaBeställningar = databas.getOrdersWithRespectiveContent();
        Scanner scanner = new Scanner(System.in);

        System.out.println();

        for (Beställning beställning:listaPåAllaBeställningar){
            System.out.println(beställning.print());
        }

        System.out.println();

        System.out.println("Hej och välkommen till vår säljstödsapplikation!\n" +
                "Vänligen ange vilken rapport som ska visas:\n" +
                "1 - En rapport som listar alla kunder, med namn och adress, som har handlat varor i en viss \n" +
                "storlek, av en viss färg eller av ett visst märke.\n" +
                "2 - En rapport som listar alla kunder och hur många ordrar varje kund har lagt. Skriv ut namn \n" +
                "och sammanlagda antalet ordrar för varje kund.\n" +
                "3 - En rapport som listar alla kunder och hur mycket pengar varje kund, sammanlagt, har \n" +
                "beställt för. Skriv ut varje kunds namn och summa.\n" +
                "4 - En rapport som listar beställningsvärde per ort. Skriv ut orternas namn och summa.\n" +
                "5 - En topplista över de mest sålda produkterna som listar varje modell och hur många ex som \n" +
                "har sålts av den modellen. Skriv ut namn på modellen och hur många ex som sålts.");

    }
}