package co.edu.univalle.poe.mvc.model;
import java.util.LinkedList;

public class GestorCartas {

    private Mazo mazo;
    private LinkedList<Carta> manoUser;
    private LinkedList<Carta> manoCPU;

    public GestorCartas(){
        mazo = new Mazo();
        manoUser = new LinkedList<Carta>();
        manoCPU = new LinkedList<Carta>();
    }

    public boolean pedirCarta(){
        Carta carta = mazo.repartirCarta();
        manoUser.add(carta);
        return true;
    }

    public boolean pedirCartaCPU(){
        Carta carta = mazo.repartirCarta();
        manoCPU.add(carta);
        return true;
    }

    public void nuevoMazo(){
        mazo = new Mazo();
        manoUser.clear();
        manoCPU.clear();
    }

    public LinkedList<Carta> getManoUser() {
        return manoUser;
    }

    public LinkedList<Carta> getManoCPU() {
        return manoCPU;
    }

    public int calcularPuntaje(LinkedList<Carta> cartas){

        int puntaje = 0;
        int cantidadAses = 0;

        for (Carta carta: cartas){
            String valor = carta.getValor();
            switch (valor){
                case "As":
                    puntaje += 11;
                    cantidadAses++;
                    break;
                case "J":
                    puntaje += 10;
                    break;
                case "K":
                    puntaje += 10;
                    break;
                case "Q":
                    puntaje += 10;
                    break;
                default:
                    int auxPuntaje = Integer.parseInt(valor);
                    puntaje += auxPuntaje;
                    break;
            }
        }

        while (puntaje > 21 && cantidadAses > 0){
            puntaje -= 10;
            cantidadAses--;
        }

        return puntaje;

    }

}
