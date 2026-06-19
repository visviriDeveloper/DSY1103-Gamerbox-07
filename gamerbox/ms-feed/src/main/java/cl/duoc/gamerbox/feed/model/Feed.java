package cl.duoc.gamerbox.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feed")
@Schema(description = "Entidad que representa una acción o actividad en el muro de un usuario")

public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del registro en el feed", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idFeed;

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del usuario propietario del muro de actividad", example = "42")
    private Long idUsuario;

    // Representa el ID de la reseña o actividad que se mostrará
    @Column(name = "feed", nullable = false)
    @Schema(description = "ID de la reseña, comentario u otra actividad relevante a mostrar", example = "105")
    private Long feed;

    @Column(name = "fecha_actividad", nullable = false)
    @Schema(description = "Fecha y hora exacta en la que se registró la actividad", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaActividad;

    public Feed() {}

    @PrePersist
    public void onPrePersist() {
        if (fechaActividad == null) fechaActividad = LocalDateTime.now();
    }

    public Long getIdFeed() { return idFeed; } public void setIdFeed(Long id) { this.idFeed = id; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long id) { this.idUsuario = id; }
    public Long getFeed() { return feed; } public void setFeed(Long f) { this.feed = f; }
    public LocalDateTime getFechaActividad() { return fechaActividad; } public void setFechaActividad(LocalDateTime f) { this.fechaActividad = f; }
}
