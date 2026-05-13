package com.melisoft.ms_resenias.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResenia;

    @NotNull(message = "El ID del juego es obligatorio")
    private Long idJuego;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El cuerpo de la reseña no puede estar vacío")
    private String cuerpo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La plataforma es obligatoria")
    private Plataforma plataforma;

    @Min(value = 1, message = "La calificación mínima es 1 estrella")
    @Max(value = 5, message = "La calificación máxima es 5 estrellas")
    @NotNull(message = "La calificación es obligatoria")
    private Integer calificacion;

    private LocalDateTime fechaPosteo;

    private Boolean estado;

    @PrePersist
    public void prePersist() {
        this.fechaPosteo = LocalDateTime.now();
        this.estado = true;
    }
}