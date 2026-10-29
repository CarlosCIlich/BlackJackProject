package co.edu.univalle.poe.mvc.model;

import java.util.LinkedList;

public class EvaluadorJugadas {

    public boolean esVeinteYMedio(LinkedList<Carta> cartas) {
        if (cartas.size() > 2) {
            return false;
        }

        Carta carta1 = cartas.get(0);
        Carta carta2 = cartas.get(1);

        return (carta1.isFigura() && carta2.isCuatro()) || (carta1.isCuatro() && carta2.isFigura());
    }

    public ResultadoRonda evaluarGanador(int puntajeUser,
                                         int puntajeCPU,
                                         LinkedList<Carta> manoUser,
                                         LinkedList<Carta> manoCPU) {

        boolean userBlackJack = (puntajeUser == 21 && manoUser.size() == 2);
        boolean cpuBlackJack = (puntajeCPU == 21 && manoCPU.size() == 2);

        boolean userVeinteYMedio = esVeinteYMedio(manoUser);
        boolean cpuVeinteYMedio = esVeinteYMedio(manoCPU);

        // La CPU se pasa de 21.
        if (puntajeCPU > 21) {
            if (userBlackJack) {
                return ResultadoRonda.CPU_PASADA;
            }
            return ResultadoRonda.GANADA_ORDINARIA;
        }

        // Ambos obtienen Blackjack.
        if (userBlackJack && cpuBlackJack) {
            return ResultadoRonda.EMPATE;
        }

        // Blackjack de la CPU.
        if (cpuBlackJack) {
            return ResultadoRonda.PERDIDA;
        }

        // Blackjack del usuario.
        if (userBlackJack) {
            return ResultadoRonda.GANADA_BLACKJACK;
        }

        // Empate con 21 puntos.
        if (puntajeUser == 21 && puntajeCPU == 21) {
            return ResultadoRonda.EMPATE;
        }

        // 21 puntos supera Veinte y Medio.
        if (puntajeUser == 21 && cpuVeinteYMedio) {
            return ResultadoRonda.GANADA_ORDINARIA;
        }

        if (puntajeCPU == 21 && userVeinteYMedio) {
            return ResultadoRonda.PERDIDA;
        }

        // Ambos obtienen Veinte y Medio.
        if (userVeinteYMedio && cpuVeinteYMedio) {
            return ResultadoRonda.EMPATE;
        }

        // Veinte y Medio supera las manos inferiores a 21.
        if (userVeinteYMedio && puntajeCPU < 21) {
            return ResultadoRonda.GANADA_ORDINARIA;
        }

        if (cpuVeinteYMedio && puntajeUser < 21) {
            return ResultadoRonda.PERDIDA;
        }

        // Comparación de puntajes normales.
        if (puntajeUser > puntajeCPU) {
            return ResultadoRonda.GANADA_ORDINARIA;
        }

        if (puntajeCPU > puntajeUser) {
            return ResultadoRonda.PERDIDA;
        }

        return ResultadoRonda.EMPATE;
    }


    public Jugadas evaluarJugada(LinkedList<Carta> cartas, int puntaje) {

        if (puntaje > 21) {
            return Jugadas.PASARSE;
        }

        if (puntaje == 21) {
            if (cartas.size() == 2) {
                return Jugadas.BLACKJACK;
            }

            return Jugadas.VEINTIUNO;
        }

        if (esVeinteYMedio(cartas)) {
            return Jugadas.VEINTE_Y_MEDIO;
        }

        return Jugadas.NORMAL;
    }

}