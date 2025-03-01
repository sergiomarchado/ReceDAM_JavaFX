package org.example.recedamjavafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.recedamjavafx.dao.UsuarioDAO;
import org.example.recedamjavafx.models.Usuario;

public class PerfilUsuarioController {

    @FXML
    private TextField nombreUsuarioField;  // Campo de texto para el nombre del usuario
    @FXML
    private TextField emailUsuarioField;   // Campo para el correo electrónico
    @FXML
    private PasswordField passwordUsuarioField;  // Campo para la contraseña
    @FXML
    private Button guardarButton;
    @FXML
    private Button cerrarButton;

    private Usuario usuario;

    // Se llamará cuando tengamos que recuperar en los campos de la pantalla de Perfil de Usuario los datos del Usuario
    public void cargarDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Seteamos todos los valores traidos de BBDD menos la Contraseña, que la mostramos vacía.
        nombreUsuarioField.setText(usuario.getNombre());
        emailUsuarioField.setText(usuario.getEmail());
        passwordUsuarioField.setText("");
    }

    @FXML
    private void initialize() {
        // Asignar las acciones a los botones de la ventana de Perfil Usuario
        guardarButton.setOnAction(event -> guardarCambios());
        cerrarButton.setOnAction(event -> cerrarPerfil());
    }

    private void guardarCambios() {
        // PASO1: Obtenemos los datos modificados
        String nuevoNombre = nombreUsuarioField.getText();
        String nuevoEmail = emailUsuarioField.getText();
        String nuevaPassword = passwordUsuarioField.getText();

        // PASO 2: Validamos que los campos no estén vacíos
        if (nuevoNombre.isEmpty() || nuevoEmail.isEmpty() || nuevaPassword.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }

        // PASO 3: Actualizamos los datos del usuario seteando los nuevos valores
        usuario.setNombre(nuevoNombre);
        usuario.setEmail(nuevoEmail);
        usuario.setPassword(nuevaPassword);

        // Guardamos el usuario actualizado en la base de datos
        try {
            // Ahora pasamos el nuevoEmail junto con el nuevoNombre y nuevaPassword
            UsuarioDAO.actualizarDatosUsuario(usuario.getId(), nuevoNombre, nuevoEmail, nuevaPassword);
            mostrarMensaje("Datos guardados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al guardar los cambios.");
        }
    }

    private void cerrarPerfil() {
        // Lógica para cerrar el perfil (cerramos la ventana)
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.close();  // Cierra la ventana actual
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
