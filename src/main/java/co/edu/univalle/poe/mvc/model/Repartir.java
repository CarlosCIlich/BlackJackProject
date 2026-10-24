package co.edu.univalle.poe.mvc.model;
import java.util.LinkedList;

public class Repartir {

    private Mazo mazo;
    private LinkedList<Carta> mano;

    public Repartir(){
        mazo = new Mazo();
        mano = new LinkedList<Carta>();
    }

    public boolean pedirCarta(){
        Carta carta = mazo.repartirCarta();
        mano.add(carta);

        return true;
    }

    public LinkedList<Carta> getMano() {
        return mano;
    }
}
