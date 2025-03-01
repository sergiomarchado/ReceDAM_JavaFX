package org.example.recedamjavafx.dao;

import org.example.recedamjavafx.models.Favoritos;
import org.example.recedamjavafx.models.Usuario;
import org.example.recedamjavafx.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FavoritosDAO {

    // Obtener los favoritos del usuario desde la tabla Favoritos
    public static List<Favoritos> obtenerFavoritosDeBaseDeDatos(Long usuarioId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Favoritos f WHERE f.usuario.id = :usuarioId";
            Query<Favoritos> query = session.createQuery(hql, Favoritos.class);
            query.setParameter("usuarioId", usuarioId);

            return query.list();  // Devuelve la lista de favoritos
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Verificar si la receta ya está en los favoritos del usuario
    public static boolean verificarExistenciaRecetaEnFavoritos(Long usuarioId, String nombreReceta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "SELECT COUNT(f) FROM Favoritos f WHERE f.usuario.id = :usuarioId AND f.nombre_receta = :nombreReceta";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("usuarioId", usuarioId);
            query.setParameter("nombreReceta", nombreReceta);

            Long count = query.uniqueResult();
            return count > 0;  // Si ya existe, retorna true
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para agregar receta a favoritos
    public static void agregarRecetaFavoritosABaseDeDatos(Long usuarioId, String nombreReceta, String instrucciones, String imagenUrl) {
        // Asegurarse de que los parámetros no sean nulos
        if (nombreReceta == null || nombreReceta.trim().isEmpty()) {
            nombreReceta = "Receta sin nombre";  // Asignar un nombre por defecto si es null o vacío
        }
        if (instrucciones == null || instrucciones.trim().isEmpty()) {
            instrucciones = "Instrucciones no disponibles";  // Asignar un valor por defecto si es null o vacío
        }
        if (imagenUrl == null) {
            imagenUrl = "";  // Asegurarse de que la URL no sea null
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Verificar si la receta ya está en favoritos
            if (verificarExistenciaRecetaEnFavoritos(usuarioId, nombreReceta)) {
                System.out.println("La receta ya está en tus favoritos.");
                return;  // No agregar la receta si ya existe
            }

            // Obtener el usuario desde la base de datos
            Usuario usuario = session.get(Usuario.class, usuarioId);

            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                return;  // No continuar si el usuario no se encuentra
            }

            // Crear el objeto Favoritos
            Favoritos favorito = new Favoritos();
            favorito.setUsuario(usuario);  // Asignar el usuario
            favorito.setNombre_receta(nombreReceta);  // Asignar el nombre de la receta
            favorito.setInstruccion(instrucciones);  // Asignar las instrucciones
            favorito.setImagenUrl(imagenUrl);  // Asignar la URL de la imagen

            // Guardar el favorito en la base de datos
            session.save(favorito);

            transaction.commit();
            System.out.println("Receta agregada a favoritos.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Eliminar receta de los favoritos del usuario
    public static void eliminarRecetaDeFavoritosBBDD(Long usuarioId, Long recetaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Eliminar la relación en la tabla Favoritos
            String hql = "DELETE FROM Favoritos f WHERE f.usuario.id = :usuarioId AND f.id = :recetaId";
            Query query = session.createQuery(hql);
            query.setParameter("usuarioId", usuarioId);
            query.setParameter("recetaId", recetaId);
            query.executeUpdate(); // Ejecutar eliminación

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
