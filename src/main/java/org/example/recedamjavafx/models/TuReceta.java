package org.example.recedamjavafx.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tu_receta")
public class TuReceta {
    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_receta", nullable = false)
    private String nombreReceta;

    @Column(name = "instruccion", columnDefinition = "TEXT")
    private String instruccion;

    @Column(name = "imagen_url")
    private String imagenUrl;

}
