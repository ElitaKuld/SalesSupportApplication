import java.io.IOException;
import java.util.List;

public class Databas {
    Repository repository;
    List<Beställning> beställningsLista;
    List<Beställning_Innehåller> beställningInnehållerLista;


    public Databas() throws IOException {
        repository = new Repository();
        beställningsLista = repository.getAllOrders();
        beställningInnehållerLista = repository.getOrdersContent();
    }

    public List<Beställning> getOrdersWithRespectiveContent(){
        for (int i = 0; i < beställningsLista.size(); i++){
            for(Beställning_Innehåller innehåll : beställningInnehållerLista){
                if (beställningsLista.get(i).getId() == innehåll.getBeställning().getId()){
                    BeställdVara beställdVara = new BeställdVara();
                    beställdVara.setSko(innehåll.getSko());
                    beställdVara.setAntal(innehåll.getAntal());
                    beställdVara.setDelsumma(innehåll.getDelsumma());
                    beställningsLista.get(i).getBeställdaVaror().add(beställdVara);
                    System.out.println("Beställd vara lagd in i beställning nummer " + beställningsLista.get(i).getId());
                    System.out.println(beställdVara.print());
                }
            }
        }
        return beställningsLista;
    }
}
