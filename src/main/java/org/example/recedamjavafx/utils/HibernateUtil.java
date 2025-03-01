package org.example.recedamjavafx.utils;

import org.example.recedamjavafx.models.Favoritos;
import org.example.recedamjavafx.models.TuReceta;
import org.example.recedamjavafx.models.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    // Usamos una variable estática para el SessionFactory, ya que solo se necesita una instancia en toda la aplicación
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Método para construir la SessionFactory
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Configuración de la base de datos
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url",
                    "jdbc:mysql://localhost:3306/proyecto_javafx?useSSL=false&serverTimezone=UTC");

            //CREDENCIALES PARA LA CONEXIÓN.
            configuration.setProperty("hibernate.connection.username", "root"); // Reemplaza con tu usuario
            configuration.setProperty("hibernate.connection.password", "TU CONTRASEÑA"); // Reemplaza con tu contraseña

            // Configuración de Hibernate
            configuration.setProperty("hibernate.show_sql", "true"); // Visualizar SQL en consola
            configuration.setProperty("hibernate.format_sql", "true"); // Formatea SQL para mejor legibilidad

            // Especificamos el dialecto para MySQL
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            // 'update' para desarrollo, 'validate' o 'none' en producción
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Agregar las clases de entidades que usamos
            configuration.addAnnotatedClass(Usuario.class);
            configuration.addAnnotatedClass(Favoritos.class);
            configuration.addAnnotatedClass(TuReceta.class);

            // Construir la SessionFactory
            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Error en la inicialización de Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Método para obtener el SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Método para cerrar el SessionFactory (usado cuando la aplicación finaliza)
    public static void shutdown() {
        // Cierra el SessionFactory cuando ya no sea necesario
        getSessionFactory().close();
    }
}
