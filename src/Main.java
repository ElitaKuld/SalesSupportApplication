import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Repository repository = new Repository();
        List<Beställning> beställningsLista = repository.getAllOrders();
        beställningsLista.forEach(beställning -> System.out.println(beställning.getSumma() +
                " " + beställning.getKund().print() + beställning.getBeställdaVaror()));
    }
}