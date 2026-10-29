package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;


import java.net.URL;
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
    private Label lblEstadoCartasJugador;
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
    @FXML
    private Button btnAyuda;

    private Repartir repartir;
    private GestorApuesta gestorApuesta;
    private EstadoJuego estadoJuego;
    private ResultadoRonda resultadoRonda;

    private MediaPlayer loseSoundPlayer;
    private MediaPlayer musicPlayer;
    private MediaPlayer winSoundPlayer;

    private void reproducirSonido(MediaPlayer reproductor) {
        reproductor.stop();
        reproductor.seek(Duration.ZERO);
        reproductor.play();
    }

    @FXML
    private void initialize(){
        URL musicURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/Casino Background Music.mp3");
        URL perdisteURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/perdiste.mp3");
        URL ganasteURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/ganaste.mp3");

        Media music = new Media(musicURL.toExternalForm());
        Media musicperdiste = new Media(perdisteURL.toExternalForm());
        Media musicganaste = new Media(ganasteURL.toExternalForm());

        musicPlayer = new MediaPlayer(music);
        loseSoundPlayer = new MediaPlayer(musicperdiste);
        winSoundPlayer = new MediaPlayer(musicganaste);

        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        musicPlayer.play();
        repartir = new Repartir();
        gestorApuesta = new GestorApuesta();
        cambiarEstado(EstadoJuego.ESPERANDO_APUESTA);

        btnIniciarRonda.setOnMouseEntered(event -> {
            btnIniciarRonda.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #3C8C79, #2B6557); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;"
            );
        });

        btnIniciarRonda.setOnMouseExited(event -> {
            btnIniciarRonda.setStyle("-fx-background-color: linear-gradient(to bottom, #2B6557, #2B6557); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;");
        });

        btnPlantarse.setOnMouseEntered(event -> {
            btnPlantarse.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #3CB568, #0d4822); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;"
            );
        });

        btnPlantarse.setOnMouseExited(event -> {
            btnPlantarse.setStyle("-fx-background-color: linear-gradient(to bottom, #2e8b50, #0d4822); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;");
        });

        btnPedirCarta.setOnMouseEntered(event -> {
            btnPedirCarta.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #4895DB, #2D547F); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;"
            );
        });

        btnPedirCarta.setOnMouseExited(event -> {
            btnPedirCarta.setStyle("-fx-background-color: linear-gradient(to bottom, #346C9F, #2D547F); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;");
        });

        btnAyuda.setOnMouseEntered(event -> {
            btnAyuda.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #36CF7D, #1D7044); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;"
            );
        });
        btnAyuda.setOnMouseExited(event -> {
            btnAyuda.setStyle("-fx-background-color: linear-gradient(to bottom, #27965B, #1D7044); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;");
        });

        btnCerrar.setOnMouseEntered(event -> {
            btnCerrar.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #D60000, #6D0000); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;"
            );
        });

        btnCerrar.setOnMouseExited(event -> {
            btnCerrar.setStyle("-fx-background-color: linear-gradient(to bottom, #9C0000, #6D0000); -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: #d4af37; -fx-border-width: 2px; -fx-background-radius: 25px; -fx-border-radius: 25px; -fx-cursor: hand;");
        });
    }

    @FXML
    private void onAbrirAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/co/edu/univalle/poe/mvc/view/help.fxml"
                    )
            );

            Parent root = loader.load();

            Stage ventanaAyuda = new Stage();
            ventanaAyuda.setTitle("Ayuda");
            Image iconohelp = new Image(getClass().getResourceAsStream("/co/edu/univalle/poe/mvc/images/icons/help.png"));
            ventanaAyuda.getIcons().add(iconohelp);
            ventanaAyuda.setScene(new Scene(root));
            ventanaAyuda.setResizable(false);
            ventanaAyuda.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        lblPuntajeUsuario.setText("" + puntaje);
        lblEstadoCartasJugador.setText("" );
        lblFichas.setText("" + gestorApuesta.getFichas());
        mostrarCartas(hboxCartasJugador, repartir.getManoUser());
        mostrarMensaje("Jugando", "...");

        cambiarEstado(EstadoJuego.TURNO_JUGADOR);
        //Revisar.
        revisarJugada();

    }

    @FXML
    private void onPedirCarta(){
        repartir.pedirCarta();
        int puntaje = repartir.calcularPuntaje(repartir.getManoUser());

        lblPuntajeUsuario.setText("" + puntaje);
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
            reproducirSonido(loseSoundPlayer);
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

        lblpuntajeCPU.setText("" + puntajeCPU);
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
                reproducirSonido(winSoundPlayer);
            } else {
                finalizarRonda(ResultadoRonda.GANADA_ORDINARIA, "Has ganado!", "La CPU se paso de 21. Ganaste el doble de tu apuesta en fichas.");
                reproducirSonido(winSoundPlayer);
            }
        } else if (puntajeUser > puntajeCPU) {
            if(puntajeUser == 21 && repartir.getManoUser().size() == 2) {
                finalizarRonda(ResultadoRonda.GANADA_BLACKJACK, "BLACKJACK!", "Ganaste 2.5 sobre tu apuesta de " + apuesta + " fichas.");
                reproducirSonido(winSoundPlayer);
            } else {
                finalizarRonda(ResultadoRonda.GANADA_ORDINARIA, "Has ganado!", "Ganaste el doble de tu apuesta en fichas.");
                reproducirSonido(winSoundPlayer);
            }

        } else if (puntajeCPU > puntajeUser) {
            finalizarRonda(ResultadoRonda.PERDIDA, "Has perdido", "La CPU gana con " + puntajeCPU);
            reproducirSonido(loseSoundPlayer);
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
