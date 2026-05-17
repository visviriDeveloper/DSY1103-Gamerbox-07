package cl.duoc.gamerbox.resenas.dto;
import jakarta.validation.constraints.*;

public class ResenaDTO {

    @NotNull @Positive
    private Long idJuego;

    @NotNull @Positive
    private Long idUsuario;

    @NotBlank @Size(max = 100)
    private String titulo;

    @NotBlank @Size(max = 1000)
    private String cuerpo;

    @NotBlank
    @Pattern(regexp = "PC|PS5|PS4|XBOX_SERIES|XBOX_ONE|NINTENDO_SWITCH", message = "Plataforma no válida")
    private String plataforma;

    @NotNull
    @Min(value = 1, message = "Mínimo 1 estrella")
    @Max(value = 5, message = "Máximo 5 estrellas")
    private Integer calificacion;

    public Long getIdJuego() { return idJuego; } public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getTitulo() { return titulo; } public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getCuerpo() { return cuerpo; } public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public Integer getCalificacion() { return calificacion; } public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }
}
