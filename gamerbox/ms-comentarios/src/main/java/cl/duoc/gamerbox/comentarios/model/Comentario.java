package cl.duoc.gamerbox.comentarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
@Schema(description = "Entidad que representa la respuesta o comentario de un usuario a una reseña específica")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del comentario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idComentario;

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del usuario que escribe el comentario", example = "42")
    private Long idUsuario;

    @Column(name = "id_resena", nullable = false)
    @Schema(description = "ID de la reseña a la cual pertenece este comentario", example = "15")
    private Long idResena;

    @Column(name = "texto_comentario", nullable = false, length = 500)
    @Schema(description = "Cuerpo del mensaje", example = "¡Totalmente de acuerdo contigo! El final me pareció increíble.")
    private String textoComentario;

    @Column(name = "fecha_comentario", nullable = false)
    @Schema(description = "Fecha y hora exacta de la publicación", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaComentario;

    @Column(name = "comentario_activo", nullable = false)
    @Schema(description = "Estado de visibilidad del comentario", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean comentarioActivo;

    public Comentario() {}

    @PrePersist
    public void onPrePersist() {
        if (fechaComentario == null) fechaComentario = LocalDateTime.now();
        if (comentarioActivo == null) comentarioActivo = true;
    }

    public Long getIdComentario() { return idComentario; } public void setIdComentario(Long id) { this.idComentario = id; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdResena() { return idResena; } public void setIdResena(Long idResena) { this.idResena = idResena; }
    public String getTextoComentario() { return textoComentario; } public void setTextoComentario(String texto) { this.textoComentario = texto; }
    public LocalDateTime getFechaComentario() { return fechaComentario; } public void setFechaComentario(LocalDateTime f) { this.fechaComentario = f; }
    public Boolean getComentarioActivo() { return comentarioActivo; } public void setComentarioActivo(Boolean activo) { this.comentarioActivo = activo; }
}
