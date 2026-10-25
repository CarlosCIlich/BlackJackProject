package co.edu.univalle.poe.mvc.model;
import java.util.LinkedList;

public class Repartir {

    private Mazo mazo;
    private LinkedList<Carta> manoUser;
    private LinkedList<Carta> manoCPU;

    public Repartir(){
        mazo = new Mazo();
        manoUser = new LinkedList<Carta>();
        manoCPU = new LinkedList<Carta>();
    }

    public boolean pedirCarta(){
        Carta carta = mazo.repartirCarta();
        manoUser.add(carta);

        return true;
    }

    public LinkedList<Carta> getMano() {
        return manoUser;
    }
}
