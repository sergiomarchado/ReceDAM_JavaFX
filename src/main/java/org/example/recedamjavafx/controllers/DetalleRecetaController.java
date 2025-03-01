package org.example.recedamjavafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.recedamjavafx.dao.FavoritosDAO;
import org.example.recedamjavafx.dao.UsuarioDAO;
import org.example.recedamjavafx.models.Favoritos;
import org.example.recedamjavafx.models.TuReceta;
import org.example.recedamjavafx.models.Usuario;

public class DetalleRecetaController {

    @FXML
    private Button btnFavoritos;
    @FXML
    private Label nombreRecetaLabel;
    @FXML
    private ImageView imagenReceta;
    @FXML
    private TextArea instruccionesReceta;
    @FXML
    private Button btnCerrar;

    // Variables para manejar la receta dependiendo de donde venga
    private Favoritos favorito;
    private TuReceta tuReceta;

    // Este método recibe Favoritos (API o BBDD) y llama a método auxiliar mostrarReceta
    public void setFavorito(Favoritos favorito) {
        this.favorito = favorito;
        mostrarReceta(favorito != null ? favorito.getNombre_receta() : "Receta no encontrada",
                favorito != null ? favorito.getInstruccion() : "No hay instrucciones disponibles",
                favorito != null ? favorito.getImagenUrl() : null);
    }

    // Este método recibe TuReceta (BBDD) y llama a método auxiliar mostrarReceta
    public void setTuReceta(TuReceta tuReceta) {
        this.tuReceta = tuReceta;
        mostrarReceta(tuReceta != null ? tuReceta.getNombreReceta() : "Receta no encontrada",
                tuReceta != null ? tuReceta.getInstruccion() : "No hay instrucciones disponibles",
                tuReceta != null ? tuReceta.getImagenUrl() : null);
    }

    // MÉTODO AUXILIAR: Mostrar receta (ya sea Favoritos o TuReceta)
    private void mostrarReceta(String nombre, String instrucciones, String imagenUrl) {
        // Mostrar el nombre de la receta
        nombreRecetaLabel.setText(nombre);

        // Mostrar las instrucciones de la receta
        instruccionesReceta.setText(instrucciones);

        // Cargar la imagen si existe
        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            imagenReceta.setImage(new Image(imagenUrl));
        } else {
            imagenReceta.setImage(new Image(getClass().getResourceAsStream("/org/example/recedamjavafx/images/no_image.png")));
        }
    }

    // Asignamos los botones de la Página de Detalle Receta a sus métodos correspondientes
    @FXML
    private void initialize() {
        btnCerrar.setOnAction(event -> cerrarVentana());
        btnFavoritos.setOnAction(event -> agregarFavorito());
    }

    // FUNCIÓN DE AGREGAR A LA BBDD Lista de Recetas Favoritas (también se pueden agregar recetas de Mis Recetas)
    private void agregarFavorito() {
        try {
            Usuario usuario = UsuarioDAO.obtenerUsuarioActual();
            if (usuario != null) {
                // Obtener los datos de la receta, eligiendo entre Favoritos o TuReceta
                String nombreReceta = (favorito != null ? favorito.getNombre_receta() : tuReceta.getNombreReceta());
                String instrucciones = (favorito != null ? favorito.getInstruccion() : tuReceta.getInstruccion());
                String imagenUrl = (favorito != null ? favorito.getImagenUrl() : tuReceta.getImagenUrl());

                // Verificación adicional para asegurar que no se pasen valores vacíos o nulos
                if (nombreReceta.trim().isEmpty()) {
                    nombreReceta = "Receta sin nombre";
                }
                if (instrucciones.trim().isEmpty()) {
                    instrucciones = "Instrucciones no disponibles";
                }
                if (imagenUrl.trim().isEmpty()) {
                    imagenUrl = "";  // Asigna una cadena vacía si la URL es vacía
                }

                // Verificar si la receta ya está en los favoritos
                if (FavoritosDAO.verificarExistenciaRecetaEnFavoritos(usuario.getId(), nombreReceta)) {
                    mostrarMensaje("Esta receta ya está en tus favoritos.");
                    return;  // Si la receta ya existe, no agregarla
                }

                // Usar el DAO para agregar la receta a favoritos pasando los parámetros
                FavoritosDAO.agregarRecetaFavoritosABaseDeDatos(usuario.getId(), nombreReceta, instrucciones, imagenUrl);

                mostrarMensaje("Receta agregada a favoritos.");
            } else {
                mostrarMensaje("No hay usuario autenticado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al agregar receta a favoritos.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) nombreRecetaLabel.getScene().getWindow();
        stage.close();
    }
}
