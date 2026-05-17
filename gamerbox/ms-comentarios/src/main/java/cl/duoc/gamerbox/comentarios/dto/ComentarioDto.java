package cl.duoc.gamerbox.comentarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ComentarioDto {

    @NotNull @Positive
    private Long idUsuario;

    @NotNull @Positive
    private Long idResena;

    @NotBlank @Size(max = 500)
    private String textoComentario;

    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long id) { this.idUsuario = id; }
    public Long getIdResena() { return idResena; } public void setIdResena(Long id) { this.idResena = id; }
    public String getTextoComentario() { return textoComentario; } public void setTextoComentario(String t) { this.textoComentario = t; }
}
