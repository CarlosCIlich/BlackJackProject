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
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;

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
    private Label lblEstadoCartasCPU;
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
    @FXML
    private ImageView botonSonido;
    @FXML
    private Label lblApuestaActual;

    private GestorCartas gestorCartas;
    private GestorApuesta gestorApuesta;
    private EstadoJuego estadoJuego;
    private ResultadoRonda resultadoRonda;
    private EvaluadorJugadas evaluadorJugadas;

    private MediaPlayer loseSoundPlayer;
    private MediaPlayer musicPlayer;
    private MediaPlayer winSoundPlayer;
    private MediaPlayer empateSoundPlayer;

    private void reproducirSonido(MediaPlayer reproductor) {
        reproductor.stop();
        reproductor.seek(Duration.ZERO);
        reproductor.play();
    }
    @FXML
    private void onAlternarSonido() {
        boolean estaSilenciado = musicPlayer.isMute();
        musicPlayer.setMute(!estaSilenciado);
        String urlImagen;

        if (estaSilenciado) {
            urlImagen = "/co/edu/univalle/poe/mvc/images/icons/song.png";
        } else {
            urlImagen = "/co/edu/univalle/poe/mvc/images/icons/muted.png";
        }

        Image imagen = new Image(
                getClass().getResourceAsStream(urlImagen)
        );

        botonSonido.setImage(imagen);
    }
    @FXML
    private void initialize(){
        URL musicURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/Casino Background Music.mp3");
        URL perdisteURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/perdiste.mp3");
        URL ganasteURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/ganaste.mp3");
        URL empateURL = getClass().getResource("/co/edu/univalle/poe/mvc/audios/empate.mp3");

        Media music = new Media(musicURL.toExternalForm());
        Media musicperdiste = new Media(perdisteURL.toExternalForm());
        Media musicganaste = new Media(ganasteURL.toExternalForm());
        Media musicempate = new Media(empateURL.toExternalForm());

        musicPlayer = new MediaPlayer(music);
        loseSoundPlayer = new MediaPlayer(musicperdiste);
        winSoundPlayer = new MediaPlayer(musicganaste);
        empateSoundPlayer = new MediaPlayer(musicempate);

        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        musicPlayer.play();
        gestorCartas = new GestorCartas();
        gestorApuesta = new GestorApuesta();
        evaluadorJugadas = new EvaluadorJugadas();
        if (gestorApuesta.getFichas() > 10) {cambiarEstado(EstadoJuego.ESPERANDO_APUESTA);}
        else {cambiarEstado(EstadoJuego.SIN_FICHAS);}

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

        txtFieldApuesta.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("[0-9]*")) {
                txtFieldApuesta.setText(newText.replaceAll("[^0-9]", ""));
            }
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

        limpiarVista(hboxCartasCPU, lblpuntajeCPU);

        // Validamos que el usuario ingrese un numero.
        int apuesta;
        try{
            apuesta = Integer.parseInt(txtFieldApuesta.getText().trim());
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
        lblApuestaActual.setText("APUESTA ACTUAL: " + apuesta);

        gestorCartas.nuevoMazo();
        gestorCartas.pedirCarta();
        gestorCartas.pedirCarta();
        int puntaje = gestorCartas.calcularPuntaje(gestorCartas.getManoUser());

        lblPuntajeUsuario.setText("" + puntaje);
        lblEstadoCartasJugador.setText("" );
        lblEstadoCartasCPU.setText("" );
        lblFichas.setText("" + gestorApuesta.getFichas());
        hboxCartasJugador.getChildren().clear();
        mostrarCartas(hboxCartasJugador, gestorCartas.getManoUser());
        mostrarMensaje("Jugando", "...");

        cambiarEstado(EstadoJuego.TURNO_JUGADOR);
        procesarJugadaUser();

    }

    @FXML
    private void onPedirCarta(){
        gestorCartas.pedirCarta();
        int puntaje = gestorCartas.calcularPuntaje(gestorCartas.getManoUser());

        lblPuntajeUsuario.setText("" + puntaje);
        mostrarCartas(hboxCartasJugador, gestorCartas.getManoUser());

        procesarJugadaUser();

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
                txtFieldApuesta.clear();
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

    private void procesarJugadaUser() {

        int puntaje = gestorCartas.calcularPuntaje(gestorCartas.getManoUser());
        Jugadas jugada = evaluadorJugadas.evaluarJugada(gestorCartas.getManoUser(), puntaje);

        switch (jugada) {

            case PASARSE:
                int apuestaPerdida = gestorApuesta.getApuesta();
                finalizarRonda(
                        ResultadoRonda.PERDIDA,
                        "Te pasaste de 21!",
                        "Perdiste tu apuesta de " + apuestaPerdida + " fichas. Puedes volver a iniciar una apuesta."
                );

                reproducirSonido(loseSoundPlayer);
                break;

            case BLACKJACK:
                mostrarMensaje(
                        "BLACKJACK!",
                        "Esperando el turno de la CPU"
                );
            case VEINTIUNO:
                mostrarMensaje(
                        "Has llegado a 21!",
                        "Esperando el turno de la CPU"
                );

                onPlantarse();
                break;

            case VEINTE_Y_MEDIO:
                mostrarMensaje(
                        "VEINTE Y MEDIO",
                        "Este mazo solo pierde contra 21 o BLACKJACK! " + "Puedes plantarte o pedir otra carta."
                );
                break;

            case NORMAL:
                break;
        }
    }
    private void finalizarRonda(ResultadoRonda resultado, String titulo, String subtitulo){

        resultadoRonda = resultado;
        gestorApuesta.entregarGanancias(resultado);
        lblFichas.setText("" + gestorApuesta.getFichas());
        lblApuestaActual.setText("APUESTA ACTUAL: 0");

        if(gestorApuesta.getFichas() < 10){
            cambiarEstado(EstadoJuego.SIN_FICHAS);
            mostrarMensaje("Juego Terminado", "Te has quedado sin fichas para apostar.");
        } else {
            cambiarEstado(EstadoJuego.ESPERANDO_APUESTA);
            mostrarMensaje(titulo, subtitulo);
        }

    }

    private void turnoCPU(){

        hboxCartasCPU.getChildren().clear();
        //La CPU pide cartas mientras no se cumpla la condicion:
        while (gestorCartas.calcularPuntaje(gestorCartas.getManoCPU()) < 17){
            gestorCartas.pedirCartaCPU();
        }
        // Calculamos los puntajes de la CPU y del usuario
        int puntajeCPU = gestorCartas.calcularPuntaje(gestorCartas.getManoCPU());
        int puntajeUser = gestorCartas.calcularPuntaje(gestorCartas.getManoUser());

        lblpuntajeCPU.setText("" + puntajeCPU);
        mostrarCartas(hboxCartasCPU, gestorCartas.getManoCPU());

        //evaluamos al ganador
        procesarResultadoRonda(puntajeUser, puntajeCPU);

    }

    private void procesarResultadoRonda(int puntajeUser, int puntajeCPU) {

        int apuesta = gestorApuesta.getApuesta();

        ResultadoRonda resultado = evaluadorJugadas.evaluarGanador(
                puntajeUser,
                puntajeCPU,
                gestorCartas.getManoUser(),
                gestorCartas.getManoCPU()
        );

        switch (resultado) {

            case GANADA_BLACKJACK:
                finalizarRonda(
                        ResultadoRonda.GANADA_BLACKJACK,
                        "BLACKJACK!",
                        "Ganaste 2,5 veces tu apuesta. Se acreditan "
                                + ((apuesta * 5) / 2) + " fichas."
                );
                reproducirSonido(winSoundPlayer);
                break;

            case CPU_PASADA:
                finalizarRonda(
                        ResultadoRonda.GANADA_ORDINARIA,
                        "La CPU se paso de 21!",
                        "Ganaste 2 veces tu apuesta. Se acreditan "
                                + (apuesta * 2) + " fichas."
                );
                reproducirSonido(winSoundPlayer);
                break;

            case GANADA_ORDINARIA:
                finalizarRonda(
                        ResultadoRonda.GANADA_ORDINARIA,
                        "Has ganado!",
                        "Ganaste el doble de tu apuesta de "
                                + apuesta + " fichas."
                );
                reproducirSonido(winSoundPlayer);
                break;

            case PERDIDA:
                finalizarRonda(
                        ResultadoRonda.PERDIDA,
                        "Has perdido",
                        "La CPU obtuvo una mano superior a la tuya."
                );
                reproducirSonido(loseSoundPlayer);
                break;

            case EMPATE:
                finalizarRonda(
                        ResultadoRonda.EMPATE,
                        "Empate!",
                        "Se devuelven tus "
                                + apuesta + " fichas."
                );
                reproducirSonido(empateSoundPlayer);
                break;
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

        imageView.setOnMouseEntered(event -> {
            imageView.setScaleX(1.08);
            imageView.setScaleY(1.08);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        });


        String mensaje = "";

        if (nombreArchivo.startsWith("J")) {
            mensaje = "10 puntos";
        } else if (nombreArchivo.startsWith("Q")) {
            mensaje = "10 puntos";
        } else if (nombreArchivo.startsWith("K")) {
            mensaje = "10 puntos";
        } else if (nombreArchivo.startsWith("A")) {
            mensaje = "vale 1 u 11 puntos";
        } else {
            mensaje = carta.getValor() + " puntos";
        }

        if (!mensaje.isEmpty()) {
            Tooltip tooltip = new Tooltip(mensaje);
            tooltip.setShowDelay(Duration.millis(200));
            Tooltip.install(imageView, tooltip);
        }
        return imageView;
    }

    public void mostrarCartas(HBox auxHbox, LinkedList<Carta> cartas){

        HBox hbox = auxHbox;

        int cartasEnMazo = auxHbox.getChildren().size();
        int orderAnimacion = 0;

        for (int i = cartasEnMazo; i < cartas.size(); i++){
            Carta carta = cartas.get(i);
            ImageView imgCarta = crearImagenCarta(carta);

            double deltaX = -180.0;
            // Determinamos el deltaY dependiendo de si la carta parte del hboxJugador o del hboxCPU.
            double deltaY = (auxHbox == hboxCartasJugador) ? -140.0 : 140.0;

            //Enviamos la carta a la posicion del "mazo" y ponemos su opacidad en 0.0 (desde aqui comenzara la animacion).
            imgCarta.setTranslateX(deltaX);
            imgCarta.setTranslateY(deltaY);
            imgCarta.setOpacity(0.0);

            auxHbox.getChildren().add(imgCarta);

            //definimos un movimiento de translacion que devuelve las cartas a su posicion original (layout asignado por la Hbox al momento de agregar la imagen)
            PauseTransition pausa = getPausa(imgCarta, orderAnimacion);
            pausa.play();
            orderAnimacion++;

        }
    }

    private PauseTransition getPausa(ImageView imgCarta, int orderAnimacion) {
        TranslateTransition movimiento = new TranslateTransition(Duration.millis(400), imgCarta);
        movimiento.setToX(0);
        movimiento.setToY(0);

        // Definimos una transicion de "Fade" para que la carta vuelva a ser visible.
        FadeTransition aparicionCarta = new FadeTransition(Duration.millis(400), imgCarta);
        aparicionCarta.setToValue(1.0);

        // Asociamos ambas transiciones mediante una ParallelTransition.
        ParallelTransition animacion = new ParallelTransition(movimiento, aparicionCarta);

        // Creamos una pausa para que las cartas tengan un delay el momento de aparecer en la mano.
        PauseTransition pausa = new PauseTransition(Duration.millis(orderAnimacion * 250));
        pausa.setOnFinished(event -> animacion.play());
        return pausa;
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

    private void limpiarVista(HBox auxHbox, Label lblPuntaje){
        HBox hbox = auxHbox;
        auxHbox.getChildren().clear();

        Label lblNuevoTexto = new Label("Esperando Inicio de Turno (sin cartas)");
        lblNuevoTexto.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-weight: bold;"
        );
        auxHbox.getChildren().add(lblNuevoTexto);

        lblPuntaje.setText("--");
    }

}