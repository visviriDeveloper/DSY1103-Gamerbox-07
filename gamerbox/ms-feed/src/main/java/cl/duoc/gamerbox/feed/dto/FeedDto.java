package cl.duoc.gamerbox.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Objeto de transferencia de datos para registrar una nueva actividad en el timeline")
public class FeedDto {

    @NotNull @Positive
    @Schema(description = "ID del usuario asociado a la actividad", requiredMode = Schema.RequiredMode.REQUIRED, example = "42")
    private Long idUsuario;

    @NotNull @Positive
    @Schema(description = "ID de referencia de la actividad generada (ej. ID de una reseña)", requiredMode = Schema.RequiredMode.REQUIRED, example = "105")
    private Long feed;

    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long id) { this.idUsuario = id; }
    public Long getFeed() { return feed; } public void setFeed(Long f) { this.feed = f; }
}
