package co.edu.univalle.poe.mvc.model;

import java.util.Arrays;
import java.util.Collections;

public class Mazo {

    private Carta[] cartas;
    private int siguienteCarta;

    public Mazo(){
        cartas = new Carta[52];
        siguienteCarta = 0;
        crearCartas();
        mezclar();
    }

    private void crearCartas(){
        String[] palos = {"P", "C", "D", "T"};
        String[] valores = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        int indice = 0;
        for(String palo: palos){
            for(String valor: valores){
                Carta auxCarta = new Carta(valor, palo);
                cartas[indice] = auxCarta;

                indice++;
            }
        }
    }

    private void mezclar(){

        Collections.shuffle(Arrays.asList(cartas));
    }

    public Carta repartirCarta(){
        if(!hayCartasDisponibles()){
            //This is just a placeholder.
            return null;
        }

        Carta cartaSeleccionada = cartas[siguienteCarta];
        siguienteCarta++;
        return cartaSeleccionada;
    }

    public boolean hayCartasDisponibles(){
        //mas conciso que un if statement.
        return siguienteCarta < cartas.length;
    }

}
