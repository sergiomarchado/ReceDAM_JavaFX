package org.example.recedamjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * La clase HelloApplication es la clase principal que arranca la aplicación.
 * Se encarga de llamar a la interfaz de usuario inicial, en este caso, la pantalla de inicio de sesión (login.fxml).
 */

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Carga la jerarquía de objetos desde el fxml:  FXMLLoader( ubicación de URL, recursos de ResourceBundle  )
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/login.fxml"));

            // Creamos la Escena
            Scene scene = new Scene(fxmlLoader.load());

            // Seteo de algunos parámetros: Título, la propia scena con el fxml cargado, tamaño mínimo, rescalable.
            /*
             * INFO: La instancia de Stage no se crea explícitamente en el código de la clase. Al extender de Application,
             * Stage ya está gestionado y se pasa automáticamente al método start como parámetro.
             * */
            stage.setTitle("Rece_DAM - Inicio de Sesión");
            stage.setScene(scene);
            stage.setWidth(600);
            stage.setHeight(900);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
