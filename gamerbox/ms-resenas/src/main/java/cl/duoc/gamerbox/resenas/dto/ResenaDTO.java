package cl.duoc.gamerbox.resenas.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class ResenaDTO {

    @NotNull @Positive
    @Schema(description = "ID del juego que se está reseñando", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long idJuego;

    @NotNull @Positive
    @Schema(description = "ID del usuario autor de la reseña", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Long idUsuario;

    @NotBlank @Size(max = 100)
    @Schema(description = "Título de la reseña", requiredMode = Schema.RequiredMode.REQUIRED, example = "Gráficos impresionantes pero historia corta")
    private String titulo;

    @NotBlank @Size(max = 1000)
    @Schema(description = "Desarrollo de la reseña", requiredMode = Schema.RequiredMode.REQUIRED, example = "Me tomó 15 horas terminarlo. El combate es genial pero el jefe final decepciona.")
    private String cuerpo;

    @NotBlank
    @Pattern(regexp = "PC|PS5|PS4|XBOX_SERIES|XBOX_ONE|NINTENDO_SWITCH", message = "Plataforma no válida")
    @Schema(description = "Plataforma donde se experimentó el juego", requiredMode = Schema.RequiredMode.REQUIRED, example = "PS5")
    private String plataforma;

    @NotNull
    @Min(value = 1, message = "Mínimo 1 estrella")
    @Max(value = 5, message = "Máximo 5 estrellas")
    @Schema(description = "Calificación general otorgada al juego", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
    private Integer calificacion;

    public Long getIdJuego() { return idJuego; } public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getTitulo() { return titulo; } public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getCuerpo() { return cuerpo; } public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public Integer getCalificacion() { return calificacion; } public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }
}
