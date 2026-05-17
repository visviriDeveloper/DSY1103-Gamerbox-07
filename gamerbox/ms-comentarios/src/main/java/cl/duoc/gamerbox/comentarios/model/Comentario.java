package cl.duoc.gamerbox.comentarios.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_resena", nullable = false)
    private Long idResena;

    @Column(name = "texto_comentario", nullable = false, length = 500)
    private String textoComentario;

    @Column(name = "fecha_comentario", nullable = false)
    private LocalDateTime fechaComentario;

    @Column(name = "comentario_activo", nullable = false)
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
