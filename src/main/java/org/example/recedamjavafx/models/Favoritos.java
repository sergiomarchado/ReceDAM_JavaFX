package org.example.recedamjavafx.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "favoritos")
public class Favoritos {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_receta", nullable = false)
    private String nombre_receta;

    @Column(name = "instruccion", columnDefinition = "TEXT")
    private String instruccion;

    @Column(name = "imagen_url")
    private String imagenUrl;

}
