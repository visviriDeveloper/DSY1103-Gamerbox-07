package cl.duoc.gamerbox.juegos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "juegos")
@Schema(description = "Entidad que representa un videojuego en el catálogo de Gamerbox")
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincrementable del juego.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idJuego;

    @Column(name = "nombre_juego", nullable = false, length = 100)
    @Schema(description = "Nombre del título.", example = "Elden Ring")
    private String nombreJuego;

    @Column(name = "plataforma", nullable = false, length = 50)
    @Schema(description = "Plataforma principal de lanzamiento del titulo.", example = "PC")
    private String plataforma;

    @Column(name = "fecha_lanzamiento", nullable = false)
    @Schema(description = "Fecha oficial de lanzamiento del juego.", example = "2022-02-25")
    private LocalDate fechaLanzamiento;

    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado de disponibilidad del juego (borrado lógico).", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean estado;

    @PrePersist
    public void onPrePersist() {
        if (estado == null) estado = true;
    }

    // Getters y Setters
    public Long getIdJuego() { return idJuego; } public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public String getNombreJuego() { return nombreJuego; } public void setNombreJuego(String nombreJuego) { this.nombreJuego = nombreJuego; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; } public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
    public Boolean getEstado() { return estado; } public void setEstado(Boolean estado) { this.estado = estado; }
}

