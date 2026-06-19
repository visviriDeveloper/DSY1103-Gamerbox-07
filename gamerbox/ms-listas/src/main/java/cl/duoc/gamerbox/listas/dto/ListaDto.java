package cl.duoc.gamerbox.listas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
@Schema(description = "Objeto de transferencia de datos utilizado para crear una nueva lista de juegos")
public class ListaDto {

    @NotNull @Positive
    @Schema(description = "ID del usuario que será el propietario de la lista", requiredMode = Schema.RequiredMode.REQUIRED, example = "42")
    private Long idUsuario;

    @NotBlank @Size(max = 100)
    @Schema(description = "Nombre descriptivo para la lista", requiredMode = Schema.RequiredMode.REQUIRED, example = "Juegos de terror multiplayer")
    private String nombreLista;

    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreLista() { return nombreLista; } public void setNombreLista(String nombreLista) { this.nombreLista = nombreLista; }
}
