package co.edu.univalle.poe.mvc;

import co.edu.univalle.poe.mvc.controller.BlackJackController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackJackApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                BlackJackApplication.class.getResource("view/BlackJack-vista.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        // Obtenemos el controlador despues de crear la scene
        BlackJackController controlador = fxmlLoader.getController();
        stage.setTitle("BlackJack Game");
        Image icono = new Image(getClass().getResourceAsStream("/co/edu/univalle/poe/mvc/images/icons/Imagen1.png"));
        stage.getIcons().add(icono);
        stage.setScene(scene);
        //stage.setResizable(false);

        // Revisamos si el usuario cancela el cierre, en cuyo caso se cancela el evento.
        stage.setOnCloseRequest( evento -> {
            boolean confirmarCierre = controlador.confirmacionCierre();
            if(!confirmarCierre){
                evento.consume();
            }
        });

        stage.show();
    }
}
