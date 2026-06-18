package cl.duoc.gamerbox.resenas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
@Schema(description = "Entidad que representa la opinión y calificación de un usuario sobre un videojuego específico")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la reseña", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idResena;

    @Column(name = "id_juego", nullable = false)
    @Schema(description = "Identificador del juego (Referencia a ms-juegos)", example = "15")
    private Long idJuego;

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "Identificador del autor (Referencia a ms-usuarios)", example = "42")
    private Long idUsuario;

    @Column(name = "titulo", nullable = false, length = 100)
    @Schema(description = "Título corto de la reseña", example = "Obra maestra indiscutible")
    private String titulo;

    @Column(name = "cuerpo", nullable = false, length = 1000)
    @Schema(description = "Texto detallado con la opinión del jugador", example = "El diseño de niveles es increíble y la jugabilidad responde perfecto...")
    private String cuerpo;

    @Column(name = "plataforma", nullable = false, length = 50)
    @Schema(description = "Plataforma en la que el usuario jugó el título", example = "PC")
    private String plataforma;

    @Column(name = "calificacion", nullable = false)
    @Schema(description = "Puntuación otorgada al juego (1 a 5)", example = "5")
    private Integer calificacion;

    @Column(name = "fecha_posteo", nullable = false)
    @Schema(description = "Fecha y hora en la que se publicó la reseña", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaPosteo;

    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado de visibilidad de la reseña (borrado lógico)", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
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
