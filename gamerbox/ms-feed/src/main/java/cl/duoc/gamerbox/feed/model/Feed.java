package cl.duoc.gamerbox.feed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeed;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    // Representa el ID de la reseña o actividad que se mostrará
    @Column(name = "feed", nullable = false)
    private Long feed;

    @Column(name = "fecha_actividad", nullable = false)
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
