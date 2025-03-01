package org.example.recedamjavafx.dao;

import org.example.recedamjavafx.models.Usuario;
import org.example.recedamjavafx.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Clase DAO (Data Access Object) encargada de gestionar las operaciones
 * relacionadas con los usuarios en la base de datos.
 * Utiliza Hibernate para la interacción con la base de datos.
 */

public class UsuarioDAO {

    private static Usuario usuarioActual = null;  // Variable estática para almacenar el usuario actual ya autentificado

// AUTENTIFICAR UN USUARIO MEDIANTE EMAIL Y CONTRASEÑA -----------------------------------------------------------------
    /*
    * Busca en la BBDD un usuario que coincida con las credenciales o los valores proporcionados (los cogemos de
    * los campos con los controllers).
    *
    * @param email  Correo Electrónico del Usuario.
    * @param password  Contraseña del Usuario.
    * @return Usuario identificado si las credenciales son correctas, sino será null.
    * */
    public static Usuario autenticarUsuario(String email, String password) {
        email = email.trim();
        password = password.trim();

        if (email.isEmpty() || password.isEmpty()) {
            // Sólo por depurar, imprimimos en pantalla para saber qué pasa.
            System.out.println("Email o contraseña vacíos.");
            return null;
        }


        Usuario usuario = null;
        /*
        INFO Hibernate: Similar a SQL pero trabaja con clases y objetos en lugar de tablas y columnas.

        * Session: Objeto de Hibernate. Representa a una conexión con la base de datos que permite ejecutar consultas
        SQL o HQL. Puede guardar actualizar y eliminar objetos en la BBDD. A diferencia de SessionFactory, Session es
        desechable, debe abrirse antes de hacer operaciones en la BBDD y cerrarse cuando ya no se necesite.

        * SessionFactory: Se encarga de Administrar conexiones con la BBDD, crea instancias de Session para interactuar
        y contiene URL de la BBDD, Credenciales, Dialecto SQL,etc. La tenemos administrada en la clase del proyecto
        HibernateUtil.

        * Hibernate.getSessionFactory().openSession(): Obtiene una nueva sesión desde SessionFactory para ejecutar
        * consultas. Usándolo con try(){} se cierra la sesión automáticamente cuando termina el bloque de código
        incluso cuando hay una excepción.
        * */
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Usuario u WHERE u.email = :email AND u.password = :password";

            // PASO 1: Creamos la Consulta
            /*
            * Esta línea de código se usa para ejecutar una consulta en Hibernate utilizando HQL
            * (Hibernate Query Language), que es similar a SQL pero orientado a objetos.
            *
            * Query<Usuario>: Es un objeto de tipo Query para obtener los resultados de la BBDD. Especificamos que el
            * resultado de la consulta será una lista de objetos de la clase Usuario.
            *
            * session.createQuery(): Método de Session que en Hibernate se utiliza para crear y ejecutar consultas
            * pasándole como argumentos el String con el formato que debe llevar el Query y la clase de los objetos
            * que queremos obtener como resultado.
            *
            * No usamos List<Usuario> porque sessio.createQuery() no devuelve directamente una lista, sino una consulta
            * preparada para ejecutarse.
            * */
            Query<Usuario> query = session.createQuery(hql, Usuario.class);

            // PASO 2: Asignación de parámetros de Consulta: recibimos como argumento al llamar a este método
            /*
            * Aquí reemplazamos los email y password de la consulta HQL con los valores reales que el usuario ha
            * ingresado en el formulario.
            *
            * En SQL tradicional sería como:
            * SELECT * FROM usuarios WHERE email = 'ejemplo@gmail.com' AND password = '12345';
            *
            * No usamos los valores directamente en el HQL para evitar la SQL Injection y proteger la BBDD de ataques
            * y además Hibernate puede reutilizar la consulta con diferentes valores.
            *
            * Eso sí, si no le pasamos los valores, Hibernate fallaría.
            * */
            query.setParameter("email", email);
            query.setParameter("password", password);

            // PASO 3: Ejecutar la Consulta.
            /*
            * Si el email y la contraseña coinciden con un usuario en la base de datos, usuario se asigna con ese objeto
            * Si no hay coincidencias, devolverá un null.
            *
            * Si hubiese varias coincidencias (cosa que tenemos que controlar) Hibernate lanzaría una excepción.
            * */
            usuario = query.uniqueResult(); // Devuelve el usuario encontrado


        } catch (Exception e) {
            System.out.println("Error al autentificar al Usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }

// Método para obtener un usuario por email ----------------------------------------------------------------------------
    public static Usuario obtenerUsuarioPorEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Usuario u WHERE u.email = :email";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("email", email);
            return query.uniqueResult(); // Devuelve el usuario si existe, o null si no lo encuentra
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

// Método para obtener un usuario por ID -------------------------------------------------------------------------------
    public static Usuario obtenerUsuarioPorId(Long usuarioId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Usuario.class, usuarioId);  // Devuelve el usuario con el ID proporcionado
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

// Establecer el usuario igualando el retorno de las consultas con usuario actual ------------------------------------
    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

// Obtener el usuario actual -----------------------------------------------------------------------------------------
    public static Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }


// Método para guardar o actualizar el usuario -----------------------------------------------------------------------
    public static void guardar(Usuario usuario) {
        // PASO 1: Instanciamos Transaction
        /*
        * Usamos Transaction para asegurar que si falla la consulta, los cambios se deshacen automáticamente.
        * Un ejemplo sería en una tienda: No tiene sentido que se reste el stock de un producto si el pago no se realizó
        * correctamente.
        *
        *
        * */
        Transaction transaction = null;

        // PASO 2: Abrimos Session de Hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // PASO 3: INICIAMOS LA TRANSACCIÓN
            transaction = session.beginTransaction();

            // PASO 4: GUARDAMOS O ACTUALIZAMOS EL USUARIO QUE RECIBIMOS COMO ARGUMENTO
            session.saveOrUpdate(usuario);  // Esto guarda el usuario si es nuevo, o lo actualiza si ya existe

            // PASO 5: Confirmamos la Transacción
            transaction.commit();

        } catch (Exception e) {
            // PASO 6: Si algo sale mal, hacemos un rollback para deshacer los cambios.
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

// Método para registrar un nuevo usuario -----------------------------------------------------------------------------
    public static void registrarUsuario(Usuario nuevoUsuario) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Verificar si el usuario ya existe
            if (obtenerUsuarioPorEmail(nuevoUsuario.getEmail()) != null) {
                throw new RuntimeException("Ya hay un Usuario registrado con ese Email.");
            }

            transaction = session.beginTransaction();
            session.save(nuevoUsuario);  // Guardamos el nuevo usuario
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

// Método para actualizar los datos del usuario -----------------------------------------------------------------------
    public static void actualizarDatosUsuario(Long usuarioId, String nuevoNombre, String nuevoEmail
            , String nuevaPassword) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Usuario usuario = session.get(Usuario.class, usuarioId);  // Cargar el usuario con el ID dado

            if (usuario != null) {
                usuario.setNombre(nuevoNombre);  // Actualizamos el nombre
                usuario.setEmail(nuevoEmail);  // Actualizamos el email
                usuario.setPassword(nuevaPassword);  // Actualizamos la contraseña
                session.update(usuario);  // Guardamos los cambios en la base de datos
                transaction.commit();
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
