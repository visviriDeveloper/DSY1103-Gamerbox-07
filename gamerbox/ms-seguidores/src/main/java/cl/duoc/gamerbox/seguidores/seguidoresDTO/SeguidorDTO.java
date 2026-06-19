package cl.duoc.gamerbox.seguidores.seguidoresDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
@Schema(description = "Objeto de transferencia de datos utilizado para registrar un nuevo seguimiento")
public class SeguidorDTO {
    @NotNull
    @Positive
    @Schema(description = "ID del usuario que realiza la acción de seguir", requiredMode = Schema.RequiredMode.REQUIRED, example = "42")
    private Long idSeguidor;

    @NotNull @Positive
    @Schema(description = "ID del usuario destino al que se desea seguir", requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    private Long idSeguido;

    public Long getIdSeguidor() { return idSeguidor; } public void setIdSeguidor(Long id) { this.idSeguidor = id; }
    public Long getIdSeguido() { return idSeguido; } public void setIdSeguido(Long id) { this.idSeguido = id; }

}
