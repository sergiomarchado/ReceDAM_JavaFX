package org.example.recedamjavafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.recedamjavafx.dao.UsuarioDAO;
import org.example.recedamjavafx.models.Usuario;

public class LoginController {

    // ATRIBUTOS Y CAMPOS FXML
    @FXML
    public TextField emailLoginField, emailRegisterField,nameRegisterField;
    @FXML
    public PasswordField passwordLoginField, passwordRegisterField, confirmPasswordField;
    @FXML
    public Button loginButton, registerButton, registerSubmitButton;
    @FXML
    public Label errorLabel;
    @FXML
    public VBox registerFields;

// INITIALIZE PRINCIPAL: seteamos las funciones a los botones ----------------------------------------------------------
    /*
    * Usamos el @FXML debido a que el método lo estamos poniendo como private.
    *
    * Si fuese público no haría falta, pero como estamos estableciendo el método como private, hay que ponerle el @FXML
    * para que JavaFX lo considere como parte del ciclo de vida del controlador.
    * */
    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> iniciarSesion());
        registerButton.setOnAction(event -> habilitarCamposRegistro());
        registerSubmitButton.setOnAction(event -> registrarNuevoUsuario());
    }

// MÉTODOS QUE SE EJECUTAN CUANDO EL USUARIO HACE CLICK EN LOS BOTONES -------------------------------------------------
    // INICIAR SESIÓN: Cuando el Usuario pulsa en el Botón Iniciar Sesión
    private void iniciarSesion() {
        // PASO 1: Guardamos el texto que han introducido en los campos.
        String email = emailLoginField.getText().trim(); //hacemos un trim por si hay algún espacio mal puesto
        String password = passwordLoginField.getText().trim();


        // PASO 2: Si esos campos están vacíos, mostramos mensaje y retornamos.
        if (email.isEmpty() || password.isEmpty()) {
            mostrarMensaje("No puede haber campos vacíos!. Por favor, introduce Email y contraseña.");
            return;
        }

        // Usar el DAO para contrastar con la base de datos, le pasamos los datos que hemos cogido de los campos
        Usuario usuario = UsuarioDAO.autenticarUsuario(email, password);

        if (usuario != null) {
            UsuarioDAO.setUsuarioActual(usuario);  // Establecemos el usuario autenticado
            // No usamos mostrarMensaje() porque queremos que el label se muestre con color verde
            errorLabel.setText("\"Bienvenid@, \" + usuario.getNombre() + \"!\"");
            errorLabel.setStyle("-fx-text-fill: green;"); // Cambiamos el color del texto a verde
            // Redirigimos a la siguiente pantalla con un método auxiliar.
            redirigirAMainController();
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos. Revise los campos introducidos nuevamente");
        }
    }

    // PULSACIÓN EN "Registrarse": hacemos campos registro visibles y escondemos los de login.
    private void habilitarCamposRegistro() {
        emailLoginField.setVisible(false);
        passwordLoginField.setVisible(false);
        loginButton.setVisible(false);

        registerFields.setVisible(true);
        registerSubmitButton.setVisible(true);
        registerButton.setVisible(false);
    }

    // Cuando el Usuario rellene los campos, se hará visible el botón de Registrar y llamará a este método
    private void registrarNuevoUsuario() {
        // Almacenamos los valores de los Campos
        String newUsername = emailRegisterField.getText().trim();
        String newPassword = passwordRegisterField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        String newName = nameRegisterField.getText().trim();

        // Si hay algún campo incompleto o vacío.
        if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() || newName.isEmpty()) {
            errorLabel.setText("Todos los campos deben ser completados para registrarte como Usuario.");
            return;
        }

        // Validar si las contraseñas de los 2 campos coinciden
        if (!newPassword.equals(confirmPassword)) {
            errorLabel.setText("Las contraseñas no coinciden. Revise los campos de Contraseña y Confirmar Contraseña");
            return;
        }

        // Verificar si el email ya está registrado.
        if (UsuarioDAO.obtenerUsuarioPorEmail(newUsername) != null) {
            errorLabel.setText("Este email ya está registrado como Usuario.");
            return;
        }

        // Crear un nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(newUsername);
        nuevoUsuario.setPassword(newPassword);
        nuevoUsuario.setNombre(newName);

        try {
            // Intentar registrar el nuevo usuario utilizando la clase DAO para volvar datos en la BBDD
            UsuarioDAO.registrarUsuario(nuevoUsuario);
            // Utilizamos el Label para informar al usuario.
            errorLabel.setText("Registro completado con éxito!. Ya puedes Iniciar Sesión. =D");
            errorLabel.setStyle("-fx-text-fill: green;"); // Cambiamos el color del texto a verde
            // Aquí usamos un método auxiliar
            resetLoginForm();
        } catch (RuntimeException e) {
            errorLabel.setText("Error al registrar el usuario. Inténtalo nuevamente.");
            errorLabel.setStyle("-fx-text-fill: red;"); // Aseguramos el color rojo para el Label de nuevo.
        }
    }

// MÉTODOS AUXILIARES --------------------------------------------------------------------------------------------------
    private void mostrarMensaje(String mensaje) {
        errorLabel.setText(mensaje);
    }

    private void resetLoginForm() {
        emailLoginField.setVisible(true);
        passwordLoginField.setVisible(true);
        loginButton.setVisible(true);

        registerFields.setVisible(false);
        registerSubmitButton.setVisible(false);
        registerButton.setVisible(true);
    }

    private void redirigirAMainController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/main.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Rece-DAM");
            stage.setHeight(750);
            stage.setWidth(900);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar la siguiente ventana.");
        }
    }
}
