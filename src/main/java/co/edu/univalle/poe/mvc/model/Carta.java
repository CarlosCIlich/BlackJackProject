package co.edu.univalle.poe.mvc.model;

// CLASE Carta.
// Representa una carta de la baraja con su respectivo valor y
// palo, permitiendo identificar si es una figura (J, Q, K) o un 4.

public class Carta {

    private String palo;
    private String valor;

    public Carta(String valor, String palo){
        this.palo = palo;
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }

    public String getPalo(){
        return palo;
    }

    public String toString(){
        return valor+palo;
    }

    public boolean isFigura(){
        return (valor.equals("J") || valor.equals("K") || valor.equals("Q"));
    }

    public boolean isCuatro(){
        return valor.equals("4");
    }

}