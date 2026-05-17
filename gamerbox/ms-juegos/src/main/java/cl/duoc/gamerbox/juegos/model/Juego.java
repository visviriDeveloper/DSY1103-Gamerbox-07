package cl.duoc.gamerbox.juegos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "juegos")
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuego;

    @Column(name = "nombre_juego", nullable = false, length = 150)
    private String nombreJuego;

    @Column(name = "plataforma", nullable = false, length = 50)
    private String plataforma;

    @Column(name = "fecha_lanzamiento", nullable = false)
    private LocalDate fechaLanzamiento;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    public Juego() {}

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

