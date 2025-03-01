package org.example.recedamjavafx.dao;

import org.example.recedamjavafx.models.TuReceta;
import org.example.recedamjavafx.models.Usuario;
import org.example.recedamjavafx.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TuRecetaDAO {

    // Método para guardar una receta del usuario
    public static void agregarTuRecetaABaseDeDatos(Long usuarioId, String nombreReceta, String instrucciones, String imagenUrl) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Obtener el usuario desde la base de datos
            Usuario usuario = session.get(Usuario.class, usuarioId);

            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                return;  // No continuar si el usuario no se encuentra
            }

            // Crear el objeto TuReceta
            TuReceta receta = new TuReceta();
            receta.setUsuario(usuario);
            receta.setNombreReceta(nombreReceta);
            receta.setInstruccion(instrucciones);
            receta.setImagenUrl(imagenUrl);

            // Guardar la receta en la base de datos
            session.save(receta);
            transaction.commit();

            System.out.println("Receta añadida correctamente.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public static List<TuReceta> obtenerMisRecetasBaseDeDatos(Long usuarioId) {
        List<TuReceta> recetas = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM TuReceta t WHERE t.usuario.id = :usuarioId";

            Query<TuReceta> hqlQuery = session.createQuery(hql, TuReceta.class);

            hqlQuery.setParameter("usuarioId", usuarioId);

            recetas = hqlQuery.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return recetas;
    }

    public static void eliminarRecetaDeMisRecetas(Long usuarioId, Long recetaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Eliminar la receta en la tabla tu_receta
            String hql = "DELETE FROM TuReceta tr WHERE tr.usuario.id = :usuarioId AND tr.id = :recetaId";
            Query query = session.createQuery(hql);
            query.setParameter("usuarioId", usuarioId);
            query.setParameter("recetaId", recetaId);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
