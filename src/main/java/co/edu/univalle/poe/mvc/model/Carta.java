package co.edu.univalle.poe.mvc.model;

// Representa una carta individual

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