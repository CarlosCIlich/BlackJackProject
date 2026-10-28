package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.LinkedList;
import java.util.Optional;

public class BlackJackController {
    @FXML
    private Button btnCerrar;
    @FXML
    private Label lblmsgUsuario1;
    @FXML
    private Label lblmsgUsuario2;
    @FXML
    private TextField txtFieldApuesta;
    @FXML
    private Label lblPuntajeUsuario;
    @FXML
    private Button btnIniciarRonda;
    @FXML
    private Label lblFichas;
    @FXML
    private HBox hboxCartasJugador;
    @FXML
    private Button btnPedirCarta;
    @FXML
    private Button btnPlantarse;
    @FXML
    private Label lblpuntajeCPU;
    @FXML
    private HBox hboxCartasCPU;


    private Repartir repartir;
    private GestorApuesta gestorApuesta;
    private EstadoJuego estadoJuego;
    private ResultadoRonda resultadoRonda;

    @FXML
    private void initialize(){
        repartir = new Repartir();
        gestorApuesta = new GestorApuesta();
        cambiarEstado(EstadoJuego.ESPERANDO_APUESTA);
    }

    @FXML
    private void onIniciarRonda(){

        // Validamos que el usuario ingrese un numero.
        int apuesta;
        try{
            apuesta = Integer.parseInt(txtFieldApuesta.getText());
        } catch (NumberFormatException e){
            mostrarMensaje("Entrada Invalida!", "Por favor ingrese un numero entero mayor a 0.");
            return;
        }

        //Validamos que el monto de la apuesta sea valido.
        if (!gestorApuesta.realizarApuesta(apuesta)){
            mostrarMensaje("Apuesta no permitida", "La apuesta debe ser multiplo de 10 y no puede superar el total de tus fichas.");
            return;
        }

        //deshabilitamos el boton para iniciar ronda y creamos un mazo para el usuario.
        btnIniciarRonda.setDisable(true);

        repartir.nuevoMazo();
        repartir.pedirCarta();
        repartir.pedirCarta();

        int puntaje = repartir.calcularPuntaje(repartir.getManoUser());

        lblPuntajeUsuario.setText("Tu Puntaje: " + puntaje);
        lblFichas.setText("" + gestorApuesta.getFichas());
        mostrarCartas(hboxCartasJugador, repartir.getManoUser());

        cambiarEstado(EstadoJuego.TURNO_JUGADOR);
        //Revisar.
        revisarJugada();

    }

    @FXML
    private void onPedirCarta(){
        repartir.pedirCarta();
        int puntaje = repartir.calcularPuntaje(repartir.getManoUser());

        lblPuntajeUsuario.setText("Tu Puntaje: " + puntaje);
        mostrarCartas(hboxCartasJugador, repartir.getManoUser());

        revisarJugada();

    }

    @FXML
    private void onPlantarse(){
        cambiarEstado(EstadoJuego.TURNO_CPU);
        mostrarMensaje("Turno de la CPU", "Esperando...");
        turnoCPU();
    }

    private void cambiarEstado(EstadoJuego nuevoestado){
        estadoJuego = nuevoestado;

        switch (estadoJuego){
            case TURNO_CPU:
                txtFieldApuesta.setDisable(true);
                btnPlantarse.setDisable(true);
                btnPedirCarta.setDisable(true);
                btnIniciarRonda.setDisable(true);
                break;
            case ESPERANDO_APUESTA:
                txtFieldApuesta.setDisable(false);
                btnPlantarse.setDisable(true);
                btnPedirCarta.setDisable(true);
                btnIniciarRonda.setDisable(false);
                break;
            case SIN_FICHAS:
                txtFieldApuesta.setDisable(true);
                btnPlantarse.setDisable(true);
                btnPedirCarta.setDisable(true);
                btnIniciarRonda.setDisable(true);
                break;
            case TURNO_JUGADOR:
                txtFieldApuesta.setDisable(true);
                btnIniciarRonda.setDisable(true);
                btnPedirCarta.setDisable(false);
                btnPlantarse.setDisable(false);
                break;
        }
    }

    private void revisarJugada(){
        int puntaje = repartir.calcularPuntaje(repartir.getManoUser());

        if(puntaje > 21) {
            int apuestaPerdida = gestorApuesta.getApuesta();

            finalizarRonda(ResultadoRonda.PERDIDA, "Te pasaste de 21!", "Perdiste tu apuesta de " + apuestaPerdida + " fichas. Puedes volver a inicar una apuesta.");

        } else if (puntaje == 21) {
            mostrarMensaje("Haz llegado a 21!", "Esperando el turno de la CPU");
            onPlantarse();
        }

    }

    private void finalizarRonda(ResultadoRonda resultado, String titulo, String subtitulo){

        resultadoRonda = resultado;
        gestorApuesta.entregarGanancias(resultado);
        lblFichas.setText("" + gestorApuesta.getFichas());

        if(gestorApuesta.getFichas() <= 0){
            cambiarEstado(EstadoJuego.SIN_FICHAS);
            mostrarMensaje("Juego Terminado", "Te has quedado sin fichas para apostar.");
        } else {
            cambiarEstado(EstadoJuego.ESPERANDO_APUESTA);
            mostrarMensaje(titulo, subtitulo);
        }

    }

    private void turnoCPU(){
        //La CPU pide cartas mientras no se cumpla la condicion:
        while (repartir.calcularPuntaje(repartir.getManoCPU()) < 17){
            repartir.pedirCartaCPU();
        }
        // Calculamos los puntajes de la CPU y del usuario
        int puntajeCPU = repartir.calcularPuntaje(repartir.getManoCPU());
        int puntajeUser = repartir.calcularPuntaje(repartir.getManoUser());

        lblpuntajeCPU.setText("Puntaje CPU: " + puntajeCPU);
        mostrarCartas(hboxCartasCPU, repartir.getManoCPU());

        //evaluamos al ganador
        evaluarGanador(puntajeUser, puntajeCPU);

    }

    private void evaluarGanador(int puntajeUser, int puntajeCPU){
        int apuesta = gestorApuesta.getApuesta();
        //Caso BlackJack CPU faltante

        if(puntajeCPU > 21){
            if(puntajeUser == 21 && repartir.getManoUser().size() == 2){
                finalizarRonda(ResultadoRonda.GANADA_BLACKJACK, "BLACKJACK! La CPU se pasó de 21.", "Ganaste 2.5 sobre tu apuesta de " + apuesta + " fichas.");
            } else {
                finalizarRonda(ResultadoRonda.GANADA_ORDINARIA, "Has ganado!", "La CPU se paso de 21. Ganaste el doble de tu apuesta en fichas.");
            }
        } else if (puntajeUser > puntajeCPU) {
            if(puntajeUser == 21 && repartir.getManoUser().size() == 2) {
                finalizarRonda(ResultadoRonda.GANADA_BLACKJACK, "BLACKJACK!", "Ganaste 2.5 sobre tu apuesta de " + apuesta + " fichas.");
            } else {
                finalizarRonda(ResultadoRonda.GANADA_ORDINARIA, "Has ganado!", "Ganaste el doble de tu apuesta en fichas.");
            }

        } else if (puntajeCPU > puntajeUser) {
            finalizarRonda(ResultadoRonda.PERDIDA, "Has perdido", "La CPU gana con " + puntajeCPU);

        } else {
            finalizarRonda(ResultadoRonda.EMPATE, "Empate!", "Se te han devuelto tus " + apuesta + " fichas.");
        }

    }

    public void mostrarMensaje(String auxString1, String auxString2){
        lblmsgUsuario1.setText(auxString1);
        lblmsgUsuario2.setText(auxString2);
    }

    private ImageView crearImagenCarta(Carta carta){
        String nombreArchivo = carta.toString();
        String ruta = "/co/edu/univalle/poe/mvc/images/cards/" + nombreArchivo+".png";

        Image imagen = new Image(getClass().getResourceAsStream(ruta));
        ImageView imageView = new ImageView(imagen);

        imageView.setFitHeight(130);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void mostrarCartas(HBox auxHbox, LinkedList<Carta> cartas){

        HBox hbox = auxHbox;

        auxHbox.getChildren().clear();

        for(Carta carta: cartas) {
            ImageView imgCarta = crearImagenCarta(carta);
            auxHbox.getChildren().add(imgCarta);
        }
    }

    @FXML
    public void onCerrarJuego(){

        Stage ventana = obtenerVentana();
        if(confirmacionCierre()){
            ventana.close();
        }

    }
    // Revisa la ventana sobre la cual existe btnCerrar y la convierte a un objeto de tipo Stage.
    private Stage obtenerVentana(){
        Window ventana = btnCerrar.getScene().getWindow();
        return (Stage) ventana;
    }

    public boolean confirmacionCierre(){
        // Creamos los botones de Salir y Cancelar
        ButtonType btnSalir = new ButtonType("Salir del juego", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Seguir jugando", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Instanciamos una alerta
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        // Configuramos alerta con los valores correspondientes
        alerta.setTitle("Cerrar BlackJack");
        alerta.setHeaderText("Realmente desea salir del juego?");
        alerta.setContentText("Si sales ahora, la partida terminara.");
        Stage alertStage = (Stage) alerta.getDialogPane().getScene().getWindow();
        Image icono = new Image(getClass().getResourceAsStream("/co/edu/univalle/poe/mvc/images/icons/Imagen1.png"));
        alertStage.getIcons().add(icono);
        alerta.getButtonTypes().setAll(btnSalir, btnCancelar);

        // Identificamos el boton seleccionado
        Optional<ButtonType> respuesta = alerta.showAndWait();

        // Retornamos verdadero si la respuesta esta presente y el boton accinado es el de salir.
        return respuesta.isPresent() && respuesta.get() == btnSalir;

    }

}
