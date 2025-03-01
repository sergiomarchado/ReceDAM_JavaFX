package org.example.recedamjavafx.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// REPRESENTA LA ENTIDAD USUARIO DENTRO DE LA BBDD
/*
* ANOTACIONES:
* @Entity: Indica que esta clase es una entidad gestionada por Hibernate.
*
* @Table(name = "usuario"): Especifíca que esta entidad se almacenará en la tabla usuarios de la BBDD.
*
* USAMOS LOMBOK PARA GENERAR LOS GETTER Y LOS SETTER.
* */
@Entity
@Table(name = "usuarios")
public class Usuario {
    // ATRIBUTOS-------------------------------------------------------------------------------------------------------
    /*
    * @Id:Indica que esta variable es la clave primaria de la tabla.
    *
    * @GeneratedValue(strategy = GenerationType.IDENTITIY: Indica que el valor se generará automáticamente en la BBDD
    *
    * @Colum(unique = true, nullable = false):
    *           unique = true indica que este campo debe ser único, no se pueden repetir registros con el mismo email.
    *           nullable = false: No se permiten valores NULL.
    *
    * @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY):
    *            - @OneToMany(mappedBy = "usuario"): indica que un usuario puede tener muchas recetas en lista favoritos
    *            - cascade = CascadeType.ALL: Si se elimina un usuario, se eliminarán también recetas favoritas.
    *            - fetch = FetchType.LAZY: Recetas favoritas no se cargarán junto al usuario, solo cuando se necesite.
    *
    *
    * */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(nullable = false)
    private String nombre;  // Añadimos el campo de nombre

    // Relación bidireccional con Favoritos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favoritos> recetasFavoritas;
}
