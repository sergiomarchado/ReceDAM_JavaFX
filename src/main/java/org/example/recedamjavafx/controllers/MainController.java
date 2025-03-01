package org.example.recedamjavafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.recedamjavafx.dao.FavoritosDAO;
import org.example.recedamjavafx.dao.RecetaDAO;
import org.example.recedamjavafx.dao.TuRecetaDAO;
import org.example.recedamjavafx.dao.UsuarioDAO;
import org.example.recedamjavafx.models.Favoritos;
import org.example.recedamjavafx.models.TuReceta;
import org.example.recedamjavafx.models.Usuario;

import java.io.IOException;
import java.util.List;


// CLASE PRINCIPAL QUE GESTIONA LA PANTALLA MAIN CUANDO YA HEMOS HECHO EL LOGIN
public class MainController {

// ATRIBUTOS -----------------------------------------------------------------------------------------------------------

    // Botones Menú Lateral
    @FXML
    private Button btnMenuInicio, btnFavoritos, btnMenuMiPerfil, btnMenuCerrarSesion, btnBuscarLupa, btnAniadeTuReceta,
            btnAniadirAFavoritas, btnMenuMisRecetas;
    // Labels informativos encima de los ListView
    @FXML
    public Label listaRecetasLabel, misRecetasLabel;

    // Botones que aparecen y desaparecen según el ListView u opción del Menú seleccionada
    @FXML
    public Button btnEliminarReceta;
    @FXML
    private Button btnEliminarFavorito;

    // Campo de Texto para realizar Búsquedas en la API.
    @FXML
    private TextField campoBuscarReceta;

    // List Views para mostrar las recetas, tanto de la API (Favoritos se reutiliza) como de la BBDD
    @FXML
    private ListView<Favoritos> listViewFavoritos; // Para mostrar favoritos
    @FXML
    private ListView<TuReceta> listViewMisRecetas; // Para mostrar mis recetas del usuario




// MÉTODO INITIALIZE PRINCIPAL -----------------------------------------------------------------------------------------
    @FXML
    private void initialize() {

        cargarRecetasDesdeAPI();  // Este método carga las recetas de la API al hacer login


        // Ocultar el Label de Mis Recetas (que perteneve al ListView inferior) al inicio
        misRecetasLabel.setVisible(false);

        // Acciones para los botones
        btnMenuInicio.setOnAction(event -> cargarPaginaInicio());
        btnFavoritos.setOnAction(event -> mostrarFavoritos()); // Mostrar favoritos cuando se hace clic
        btnMenuMiPerfil.setOnAction(event -> mostrarPerfil());
        btnMenuCerrarSesion.setOnAction(event -> cerrarSesion());
        btnBuscarLupa.setOnAction(event -> buscarRecetas());
        btnAniadeTuReceta.setOnAction(event -> agregarReceta()); // Abrir formulario de agregar receta
        btnAniadirAFavoritas.setOnAction(event -> agregarFavorito());
        btnEliminarFavorito.setOnAction(event -> eliminarFavorito());
        btnMenuMisRecetas.setOnAction(event -> mostrarMisRecetas());
        btnEliminarReceta.setOnAction(event -> eliminarMiReceta());
        campoBuscarReceta.setOnAction(event -> buscarRecetas());

        // Configuración del evento de doble click: se usa para actualizar el ListView en uso.
        listViewFavoritos.setOnMouseClicked(this::verRecetaDetalle);
        listViewMisRecetas.setOnMouseClicked(this::verRecetaDetalle);
    }



// AL INICIAR LA VENTANA CARGAMOS RECETAS DESDE LA API ----------------------------------------------------------------
    /*
    * Obtiene recetas de la API usando RecetaDAO.buscarRecetas().
    * Transforma los datos en ObservableList()
    * Carga las recetas en el ListView
    * Configura cómo se muestran las recetas en cada fila: sólo con el nombre
    * */
    private void cargarRecetasDesdeAPI() {
        try {
            // PASO 1: Obtenemos el texto de búsqueda del Usuario (motivo explicado en comentario multilínea debajo)
            /*
            * Cuando llamemos a RecetaDAO.buscarRecetas() necesitaremos pasarle el valor de query a esta URL:
            * String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + query;
            *
            * Esto se ejecuta al inicio, asique el valor que tendrá query será "" (cadena vacía). Si la query es "",
            * la URL será:
            * https://www.themealdb.com/api/json/v1/1/search.php?s=
            *
            * Esta URL devuelve una lista de recetas populares.
            *
            *
            * */
            String query = campoBuscarReceta.getText().trim();

            // PASO 2: Usamos RecetaDAO para buscar la receta, pero en la API (false) y le pasamos el texto de búsqueda
            /*
            * Lo hacemos a través de RecetaDAO.
            * Llamamos a su método buscarRecetas que a su vez es un método que criba según pasemos por argumentos
            * si tiene que realizar una búsqueda de la receta que le pasemos en la base de datos o en la API:
            *
            *       - Si no se desea buscar en la base de datos, solo se buscan recetas desde la API (false)
            * */
            List<Favoritos> recetasAPI = RecetaDAO.buscarRecetas(query, false);


            // PASO 3: Convertimos la lista de recetas que obtenemos a un formato compatible que necesita el ListView
            /*
            * ObservableList<T> nos permite tener ese formato compatible con ListView, ya que estos no funcionan
            * con listas normales List <T>, sino que necesitan un ObservableList<T> para detectar cambios y actualizar
            * la vista.
            *
            * FXCollections.observableArrayList() es un método estático que convierte una Lista estándar List<T> en un
            * ObservableList<T>.
            * */
            ObservableList<Favoritos> favoritosObservable = FXCollections.observableArrayList(recetasAPI);

            // PASO 4: Le damos al ListView su ObservableList<T> compatible para mostrar los datos.
            /*
            * listViewFavoritos es un ListView<Favoritos>, que necesita un ObservableList<T> como hemos explicado en el
            * paso anterior.
            *
            * El método setItems() recibe un ObservableList<T>, que es la lista de elementos a mostrar.
            * */
            listViewFavoritos.setItems(favoritosObservable);

            // Configuración del ListView para mostrar el nombre de la receta
            /*
            * setCellFactory() nos permite personalizar el diseño de cada celda representada en el ListView.
            *
            * Este método recibe un CallBack y tendríamos que sobrescribir su método call, pero para simplificar lo
            * hacemos con una lambda:
            *     - param -> es un parámetro de la función que representa a ListView<Favoritos>.
            *     - new ListCell<Favoritos> Es la celda que se creará dentro del ListView para cada elemento de la lista
            *
            * Lo que hacemos en conjunto es recibir una función que devuelve un ListCell<Favoritos> para cada elemento
            * de la ListView. Cada vez que el ListView necesita una nueva celda, llama a la función dentro de
            * setCellFactory()
            *
            * */
            listViewFavoritos.setCellFactory(param -> new ListCell<Favoritos>() {
                @Override
                protected void updateItem(Favoritos favorito, boolean empty) {
                    super.updateItem(favorito, empty);
                    if (empty || favorito == null) {
                        setText(null);
                    } else {
                        setText(favorito.getNombre_receta());  // Mostrar el nombre de la receta en cada celda.
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar las recetas.");
        }
    }


// VER RECETA DETALLE: Cuando hacemos doble click en receta, se despliega la ventana con el fxml correspondiente -------
    @FXML
    private void verRecetaDetalle(MouseEvent event) {
        if (event.getClickCount() == 2) {  // Detectar doble clic
            Object selectedItem = null;

            // PASO 1: Verificar qué lista ha sido seleccionada y qué item ha sido seleccionado. Además, lo almacenamos

            // Si la lista de Favoritos está enfocada y hay un item seleccionado
            if (listViewFavoritos.isFocused() && listViewFavoritos.getSelectionModel().getSelectedItem() != null) {
                // Guardamos la receta
                selectedItem = listViewFavoritos.getSelectionModel().getSelectedItem();

            // Lo mismo para el ListView de Mis Recetas
            } else if (listViewMisRecetas.isFocused() && listViewMisRecetas.getSelectionModel().getSelectedItem() != null) {
                selectedItem = listViewMisRecetas.getSelectionModel().getSelectedItem();
            }

            // PASO 2: Si el item que hemos obtenido y almacenado no es null, cargamos la ventana de Detalle Receta
            if (selectedItem != null) {
                try {
                    // Garantizamos selección del item limpia para que no se quede guardado en la siguiente llamada
                    listViewFavoritos.getSelectionModel().clearSelection();
                    listViewMisRecetas.getSelectionModel().clearSelection();

                    // Cargar la ventana de Detalles de la Receta
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/detalleReceta.fxml"));
                    Parent root = loader.load();

                    // Pasamos los datos del item seleccionado al controlador de detalle usando el set correspondiente
                    if (selectedItem instanceof Favoritos) {
                        DetalleRecetaController controller = loader.getController();
                        controller.setFavorito((Favoritos) selectedItem);  // Pasamos el Favorito al controller

                    } else if (selectedItem instanceof TuReceta) {
                        DetalleRecetaController controller = loader.getController();
                        controller.setTuReceta((TuReceta) selectedItem);  // Pasamos la receta del usuario al controller
                    }

                    // Mostrar la ventana
                    Stage stage = new Stage();
                    stage.setTitle("Detalles de la receta");
                    stage.setScene(new Scene(root));
                    stage.setWidth(500);
                    stage.setHeight(700);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarMensaje("Error al cargar la Ventana de la Receta.");
                }
            } else {
                mostrarMensaje("No se ha seleccionado ninguna receta.");
            }
        }
    }

// FUNCIONES DEL MENÚ --------------------------------------------------------------------------------------------------
    // CARGAR PÁGINA DE INICIO ------------------------------------------------------------------------
    private void cargarPaginaInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnMenuInicio.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Rece-DAM");
            stage.setHeight(750);
            stage.setWidth(900);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar la página de inicio.");
        }
    }

    // FUNCIÓN MOSTRAR PERFIL DEL MENÚ -----------------------------------------------------------------------
    private void mostrarPerfil() {

        // PASO 1: Intentar obtener el Perfil del Usuario y abrir la ventana de Perfil Usuario
        try {
            // Lo hacemos a través de método auxiliar que a su vez usa UsuarioDAO.obtenerUsuarioActual()
            Long usuarioId = obtenerUsuarioActualId();

            // DOBLE CHECKEO

            // Si usuarioId == null -> No hay ID (puede ser que se haya eliminado usuario en BBDD)
            if (usuarioId == null) {
                mostrarMensaje("El Usuario actual ya no existe en la Base de Datos.");
                return;
            }

            // usuario == null El ID existe, pero resto de campos no
            Usuario usuario = UsuarioDAO.obtenerUsuarioPorId(usuarioId);
            if (usuario == null) {
                mostrarMensaje("No se ha podido obtener Datos del Usuario.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/perfilUsuario.fxml"));
            Parent root = loader.load();
            PerfilUsuarioController controller = loader.getController();
            // Seteamos los datos a través de PerfilUsuarioController
            controller.cargarDatosUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Perfil de Usuario");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el perfil de usuario.");
        }
    }

    // FUNCIÓN CERRAR SESIÓN DEL MENÚ ------------------------------------------------------------------------------
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnMenuCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Rece_DAM - Inicio de Sesión");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cerrar sesión.");
        }
    }
    // FUNCIÓN BUSCAR RECETA EN EL CAMPO DE TEXTO O EN EL BOTÓN LUPA -----------------------------------------------
    private void buscarRecetas() {
        String query = campoBuscarReceta.getText().trim();

        // Si el campo de búsqueda está vacío, mostrar mensaje
        if (query.isEmpty()) {
            mostrarMensaje("Introduce un término de búsqueda.");
            return;
        }

        try {
            // Realizamos la búsqueda con el término ingresado usando RecetaDAO
            List<Favoritos> recetasAPI = RecetaDAO.buscarRecetas(query, false);

            // Comprobamos si no se encontraron recetas
            if (recetasAPI == null || recetasAPI.isEmpty()) {
                mostrarMensaje("No se encontraron recetas con ese término de búsqueda.");
            } else {
                // Si hay resultados, los mostramos en el ListView
                ObservableList<Favoritos> favoritosObservable = FXCollections.observableArrayList(recetasAPI);
                listViewFavoritos.setItems(favoritosObservable);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al realizar la búsqueda.");
        }
    }

    // MOSTRAR MIS RECETAS: muestra el list con las recetas de la tabla tu_receta-----------------------
    @FXML
    private void mostrarMisRecetas() {
        Long usuarioId = obtenerUsuarioActualId();
        if (usuarioId == null) {
            mostrarMensaje("Se ha perdido la conexión con el Usuario. Ahora mismo no se puede obtener Mis Recetas.");
            return;
        }

        List<TuReceta> misRecetas = TuRecetaDAO.obtenerMisRecetasBaseDeDatos(usuarioId);
        if (misRecetas == null || misRecetas.isEmpty()) {
            mostrarMensaje("No tienes recetas guardadas.");
            return;
        }

        ObservableList<TuReceta> misRecetasObservable = FXCollections.observableArrayList(misRecetas);
        listViewMisRecetas.setItems(misRecetasObservable);  // Mostrar mis recetas

        // ListView visible y habilitado
        listViewMisRecetas.setVisible(true);
        listViewMisRecetas.setDisable(false);

        // Ocultar visibilidad del Listview de Recetas Favoritas o Lista Recetas (api) y deshabilitarla
        listViewFavoritos.setVisible(false);
        listViewFavoritos.setDisable(true);

        // Cambiar el texto del Label y hacerlo visible
        misRecetasLabel.setText("Mis Recetas");
        misRecetasLabel.setVisible(true);

        // Mostrar el botón de eliminar receta si hay recetas
        btnEliminarReceta.setVisible(true);

        // Ocultar el botón de eliminar favorito
        btnEliminarFavorito.setVisible(false);

        // Configuración del CellFactory para mostrar el nombre de la receta
        listViewMisRecetas.setCellFactory(param -> new ListCell<TuReceta>() {
            @Override
            protected void updateItem(TuReceta tuReceta, boolean empty) {
                super.updateItem(tuReceta, empty);
                if (empty || tuReceta == null) {
                    setText(null);
                } else {
                    setText(tuReceta.getNombreReceta());  // Mostrar el nombre de la receta
                }
            }
        });
    }

// MÉTODOS ASOCIADOS A FUNCIÓN DE MIS RECETAS --------------------------------------------------------------------------
    private void agregarReceta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/recedamjavafx/tuReceta.fxml"));
            Parent root = loader.load();

            TuRecetaController controller = loader.getController();
            controller.mostrarFormulario();  // Llamar al método de TuRecetaController para inicializar el formulario vacío

            Stage stage = new Stage();
            stage.setTitle("Agregar Tu Receta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al abrir la ventana de agregar receta.");
        }
    }

    private void eliminarMiReceta() {
        // Obtener la receta seleccionada
        TuReceta recetaSeleccionada = listViewMisRecetas.getSelectionModel().getSelectedItem();

        if (recetaSeleccionada != null) {
            Long usuarioId = obtenerUsuarioActualId();

            if (usuarioId != null) {

                // Mostrar confirmación antes de eliminar
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Confirmación");
                alert.setHeaderText("¿Estás seguro de que deseas eliminar esta receta?");
                alert.setContentText(recetaSeleccionada.getNombreReceta());
                alert.showAndWait().ifPresent(response -> {

                    if (response == ButtonType.OK) {

                        try {
                            // Llamar al DAO para eliminar la receta
                            TuRecetaDAO.eliminarRecetaDeMisRecetas(usuarioId, recetaSeleccionada.getId());
                            mostrarMensaje("Receta eliminada de tus recetas.");

                            // Recargar la lista de Mis Recetas después de la eliminación
                            mostrarMisRecetas();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mostrarMensaje("Error al eliminar la receta.");
                        }
                    }
                });
            }
        } else {
            mostrarMensaje("Selecciona una receta para eliminar.");
        }
    }



    // MOSTRAR RECETAS FAVORITAS: muestra el list con las recetas de la tabla favoritos de la BBDD -----------------
    @FXML
    private void mostrarFavoritos() {
        Long usuarioId = obtenerUsuarioActualId();
        if (usuarioId == null) {
            mostrarMensaje("Se ha perdido la conexión con el Usuario. Ahora mismo no se puede obtener Recetas Favoritas.");
            return;
        }

        List<Favoritos> favoritos = FavoritosDAO.obtenerFavoritosDeBaseDeDatos(usuarioId);
        if (favoritos == null || favoritos.isEmpty()) {
            mostrarMensaje("No tienes recetas favoritas.");
            btnEliminarFavorito.setVisible(false); // Ocultar el botón si no hay favoritos
            return;
        }

        ObservableList<Favoritos> favoritosObservable = FXCollections.observableArrayList(favoritos);
        listViewFavoritos.setItems(favoritosObservable);  // Mostrar favoritos

        // Aseguramos que Recetas Favoritas esté visible y habilitada
        listViewFavoritos.setVisible(true);
        listViewFavoritos.setDisable(false);

        // Ocultar Mis Recetas y deshabilitarla
        listViewMisRecetas.setVisible(false);
        listViewMisRecetas.setDisable(true);

        // Cambiar el texto del Label y hacerlo visible
        listaRecetasLabel.setText("Recetas Favoritas");
        listaRecetasLabel.setVisible(true);

        // Mostrar el botón de eliminar favorito si hay favoritos
        btnEliminarFavorito.setVisible(true);

        // Ocultar el botón de eliminar receta
        btnEliminarReceta.setVisible(false);

        // Configuración del CellFactory para mostrar el nombre de la receta
        listViewFavoritos.setCellFactory(param -> new ListCell<Favoritos>() {
            @Override
            protected void updateItem(Favoritos favorito, boolean empty) {
                super.updateItem(favorito, empty);
                if (empty || favorito == null) {
                    setText(null);
                } else {
                    setText(favorito.getNombre_receta());  // Mostrar el nombre de la receta
                }
            }
        });
    }

// MÉTODOS ASOCIADOS A FUNCIÓN DE RECETAS FAVORITAS --------------------------------------------------------------------
    private void agregarFavorito() {
        Favoritos favoritoSeleccionado = listViewFavoritos.getSelectionModel().getSelectedItem();
        if (favoritoSeleccionado != null) {
            Long usuarioId = obtenerUsuarioActualId();

            if (usuarioId != null) {
                try {
                    // Verificar si la receta ya está en favoritos
                    boolean existe = FavoritosDAO.verificarExistenciaRecetaEnFavoritos(usuarioId, favoritoSeleccionado.getNombre_receta());
                    if (existe) {
                        mostrarMensaje("La receta ya está en tus favoritos.");
                        return;
                    }

                    String nombreReceta = favoritoSeleccionado.getNombre_receta() != null ? favoritoSeleccionado.getNombre_receta() : "Receta sin nombre";
                    String instrucciones = favoritoSeleccionado.getInstruccion() != null ? favoritoSeleccionado.getInstruccion() : "Instrucciones no disponibles";
                    String imagenUrl = favoritoSeleccionado.getImagenUrl() != null ? favoritoSeleccionado.getImagenUrl() : "";

                    // Pasar estos valores a FavoritosDAO para que se guarden en la base de datos
                    FavoritosDAO.agregarRecetaFavoritosABaseDeDatos(usuarioId, nombreReceta, instrucciones, imagenUrl);

                    mostrarMensaje("Receta añadida a favoritos.");
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarMensaje("Error al agregar receta a favoritos.");
                }
            } else {
                mostrarMensaje("Se ha perdido la conexión con el Usuario. Ahora mismo no se puede agregar Recetas Favoritas.");
            }
        } else {
            mostrarMensaje("Selecciona un favorito para agregar.");
        }
    }


    // Eliminar receta de favoritos ----------------------------------------------
    private void eliminarFavorito() {
        Favoritos favoritoSeleccionado = listViewFavoritos.getSelectionModel().getSelectedItem();

        if (favoritoSeleccionado != null) {
            Long usuarioId = obtenerUsuarioActualId();

            if (usuarioId != null) {
                // Mostrar confirmación antes de eliminar
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("¿Estás seguro de que deseas eliminar esta receta de tus favoritos?");
                alert.setContentText(favoritoSeleccionado.getNombre_receta());
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Llamar al DAO para eliminar el favorito
                            FavoritosDAO.eliminarRecetaDeFavoritosBBDD(usuarioId, favoritoSeleccionado.getId());
                            mostrarMensaje("Receta eliminada de favoritos.");

                            // Recargar la lista de favoritos después de la eliminación
                            mostrarFavoritos();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mostrarMensaje("Error al eliminar la receta de favoritos.");
                        }
                    }
                });
            }
        } else {
            mostrarMensaje("Selecciona una receta para eliminar.");
        }
    }



// MÉTODOS AUXILIARES 2 ------------------------------------------------------------------------------------------------

    private Long obtenerUsuarioActualId() {
        Usuario usuarioActual = UsuarioDAO.obtenerUsuarioActual();
        if (usuarioActual != null) {
            return usuarioActual.getId();
        }
        return null;
    }

    // Métodos para mostrar mensaje de alerta
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
