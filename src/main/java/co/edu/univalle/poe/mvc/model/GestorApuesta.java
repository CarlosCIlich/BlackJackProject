package co.edu.univalle.poe.mvc.model;

public class GestorApuesta {

    private int fichas;
    private int apuesta;

    public GestorApuesta(){
        fichas = 1000;
        apuesta = 0;
    }

    public boolean realizarApuesta(int auxApuesta){
        // verificamos que la apuesta sea un numero mayor a 0, multiplo de 10 y <= al numero de fichas del usuario.
        if(auxApuesta > 0 && auxApuesta%10 == 0 && auxApuesta <= fichas) {
            apuesta = auxApuesta;
            fichas -= apuesta;
            return true;
        }
        return false;

    }

    public void entregarGanancias(ResultadoRonda resultado){
        switch (resultado){
            //Apuesta siempre es un multiplo de 10. Esta divicion es exacta por lo que no genera problemas.
            case GANADA_BLACKJACK: fichas += (apuesta*5)/2;
                break;
            case GANADA_ORDINARIA: fichas += (apuesta*2);
                break;
            case PERDIDA:
                break;
            case EMPATE: fichas += apuesta;
                break;
        }
        apuesta = 0;
    }

    public int getApuesta() {
        return apuesta;
    }

    public int getFichas() {
        return fichas;
    }
}
