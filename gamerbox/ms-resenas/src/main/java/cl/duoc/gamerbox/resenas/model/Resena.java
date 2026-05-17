package cl.duoc.gamerbox.resenas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @Column(name = "id_juego", nullable = false)
    private Long idJuego;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "cuerpo", nullable = false, length = 1000)
    private String cuerpo;

    @Column(name = "plataforma", nullable = false, length = 50)
    private String plataforma;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "fecha_posteo", nullable = false)
    private LocalDateTime fechaPosteo;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    public Resena() {}

    public Resena(Long idResena, Long idJuego, Long idUsuario, String titulo, String cuerpo, String plataforma, Integer calificacion, LocalDateTime fechaPosteo, Boolean estado) {
        this.idResena = idResena;
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.plataforma = plataforma;
        this.calificacion = calificacion;
        this.fechaPosteo = fechaPosteo;
        this.estado = estado;
    }

    @PrePersist
    public void onPrePersist() {
        if (fechaPosteo == null) fechaPosteo = LocalDateTime.now();
        if (estado == null) estado = true;
    }

    public Long getIdResena() { return idResena; } public void setIdResena(Long idResena) { this.idResena = idResena; }
    public Long getIdJuego() { return idJuego; } public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getTitulo() { return titulo; } public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getCuerpo() { return cuerpo; } public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public Integer getCalificacion() { return calificacion; } public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }
    public LocalDateTime getFechaPosteo() { return fechaPosteo; } public void setFechaPosteo(LocalDateTime fechaPosteo) { this.fechaPosteo = fechaPosteo; }
    public Boolean getEstado() { return estado; } public void setEstado(Boolean estado) { this.estado = estado; }
}
