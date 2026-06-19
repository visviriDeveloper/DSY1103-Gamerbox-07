package cl.duoc.gamerbox.seguidores.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguidores")
@Schema(description = "Entidad que representa la relación de seguimiento entre dos usuarios de la plataforma")
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del registro de seguimiento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idSeguimiento;

    @Column(name = "id_seguidor", nullable = false)
    @Schema(description = "ID del usuario que inicia el seguimiento", example = "42")
    private Long idSeguidor;

    @Column(name = "id_seguido", nullable = false)
    @Schema(description = "ID del usuario que está siendo seguido", example = "15")
    private Long idSeguido;

    @Column(name = "fecha_seguido", nullable = false)
    @Schema(description = "Fecha y hora exacta en la que se inició el seguimiento", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaSeguido;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Estado de la relación (true = siguiendo, false = dejó de seguir)", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean activo;

    public Seguidor() {}

    @PrePersist
    public void onPrePersist() {
        if (fechaSeguido == null) fechaSeguido = LocalDateTime.now();
        if (activo == null) activo = true;
    }

    public Long getIdSeguimiento() { return idSeguimiento; } public void setIdSeguimiento(Long id) { this.idSeguimiento = id; }
    public Long getIdSeguidor() { return idSeguidor; } public void setIdSeguidor(Long id) { this.idSeguidor = id; }
    public Long getIdSeguido() { return idSeguido; } public void setIdSeguido(Long id) { this.idSeguido = id; }
    public LocalDateTime getFechaSeguido() { return fechaSeguido; } public void setFechaSeguido(LocalDateTime f) { this.fechaSeguido = f; }
    public Boolean getActivo() { return activo; } public void setActivo(Boolean a) { this.activo = a; }
}
