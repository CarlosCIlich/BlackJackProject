package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.Carta;
import co.edu.univalle.poe.mvc.model.GestorApuesta;
import co.edu.univalle.poe.mvc.model.Repartir;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

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


    private Repartir repartir;
    private GestorApuesta gestorApuesta;

    @FXML
    private void initialize(){
        repartir = new Repartir();
        gestorApuesta = new GestorApuesta();
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

        repartir.nuevoMazo();
        repartir.pedirCarta();
        repartir.pedirCarta();

        int puntaje = repartir.calcularPuntaje(repartir.getManoUser());

        lblPuntajeUsuario.setText("Tu Puntaje: " + puntaje);
        lblFichas.setText("" + gestorApuesta.getFichas());
        mostrarCartas();
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

    public void mostrarCartas(){

        hboxCartasJugador.getChildren().clear();

        for(Carta carta: repartir.getManoUser()) {
            ImageView imgCarta = crearImagenCarta(carta);
            hboxCartasJugador.getChildren().add(imgCarta);
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

        alerta.getButtonTypes().setAll(btnSalir, btnCancelar);

        // Identificamos el boton seleccionado
        Optional<ButtonType> respuesta = alerta.showAndWait();

        // Retornamos verdadero si la respuesta esta presente y el boton accinado es el de salir.
        return respuesta.isPresent() && respuesta.get() == btnSalir;

    }

}
