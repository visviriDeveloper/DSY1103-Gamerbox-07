package cl.duoc.gamerbox.seguidores.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguidores")
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguimiento;

    @Column(name = "id_seguidor", nullable = false)
    private Long idSeguidor;

    @Column(name = "id_seguido", nullable = false)
    private Long idSeguido;

    @Column(name = "fecha_seguido", nullable = false)
    private LocalDateTime fechaSeguido;

    @Column(name = "activo", nullable = false)
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
