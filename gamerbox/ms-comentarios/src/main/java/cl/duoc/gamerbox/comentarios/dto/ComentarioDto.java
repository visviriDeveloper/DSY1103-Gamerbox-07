package cl.duoc.gamerbox.comentarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto de transferencia de datos para la publicación de un nuevo comentario")
public class ComentarioDto {

    @NotNull @Positive
    @Schema(description = "ID del usuario autor del comentario", requiredMode = Schema.RequiredMode.REQUIRED, example = "42")
    private Long idUsuario;

    @NotNull @Positive
    @Schema(description = "ID de la reseña que se está comentando", requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    private Long idResena;

    @NotBlank @Size(max = 500)
    @Schema(description = "Contenido en texto del comentario", requiredMode = Schema.RequiredMode.REQUIRED, example = "Tienes mucha razón sobre el sistema de combate.")
    private String textoComentario;

    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long id) { this.idUsuario = id; }
    public Long getIdResena() { return idResena; } public void setIdResena(Long id) { this.idResena = id; }
    public String getTextoComentario() { return textoComentario; } public void setTextoComentario(String t) { this.textoComentario = t; }
}
