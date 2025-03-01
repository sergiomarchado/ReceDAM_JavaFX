package org.example.recedamjavafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.recedamjavafx.dao.TuRecetaDAO;
import org.example.recedamjavafx.dao.UsuarioDAO;
import org.example.recedamjavafx.models.Usuario;

public class TuRecetaController {

    @FXML
    private TextField nombreRecetaField;
    @FXML
    private TextArea instruccionesRecetaField;
    @FXML
    private TextField imagenUrlField;
    @FXML
    private Button guardarRecetaButton;

    @FXML
    private void initialize() {
        guardarRecetaButton.setOnAction(event -> guardarReceta());
    }

    // Método para inicializar el formulario
    public void mostrarFormulario() {
        nombreRecetaField.clear();
        instruccionesRecetaField.clear();
        imagenUrlField.clear();  // Limpiar los campos antes de mostrar el formulario vacío
    }

    // Método que se ejecuta al presionar "Guardar"
    private void guardarReceta() {
        // Obtener los valores de los campos
        String nombreReceta = nombreRecetaField.getText().trim();
        String instrucciones = instruccionesRecetaField.getText().trim();
        String imagenUrl = imagenUrlField.getText().trim();

        if (nombreReceta.isEmpty() || instrucciones.isEmpty()) {
            mostrarMensaje("Por favor, rellena todos los campos.");
            return;
        }

        // Obtener el usuario actual
        Long usuarioId = obtenerUsuarioActualId();
        if (usuarioId == null) {
            mostrarMensaje("No hay usuario autenticado.");
            return;
        }

        // Guardar la receta en la base de datos
        try {
            TuRecetaDAO.agregarTuRecetaABaseDeDatos(usuarioId, nombreReceta, instrucciones, imagenUrl);
            mostrarMensaje("Receta añadida correctamente.");
            cerrarVentana();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al guardar la receta.");
        }
    }

    private Long obtenerUsuarioActualId() {
        Usuario usuarioActual = UsuarioDAO.obtenerUsuarioActual();
        if (usuarioActual != null) {
            return usuarioActual.getId();
        }
        return null;
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        // Cierra la ventana actual
        Stage stage = (Stage) nombreRecetaField.getScene().getWindow();
        stage.close();
    }
}
