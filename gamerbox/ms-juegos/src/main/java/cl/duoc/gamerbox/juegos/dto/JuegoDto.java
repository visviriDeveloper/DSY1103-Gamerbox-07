package cl.duoc.gamerbox.juegos.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

@Schema(description = "Objeto de transferencia de datos para la creación de juegos")
public class JuegoDto {

    @NotBlank
    @Schema(description = "Nombre del título", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hades II")
    private String nombreJuego;

    @NotBlank
    @Pattern(regexp = "PC|PS5|PS4|XBOX_SERIES|XBOX_ONE|NINTENDO_SWITCH", message = "Plataforma no válida")
    @Schema(description = "Plataforma en la que se juega", requiredMode = Schema.RequiredMode.REQUIRED, example = "PS5")
    private String plataforma;

    @NotNull
    @Schema(description = "Fecha de salida al mercado", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-05-01")
    private LocalDate fechaLanzamiento;

    public String getNombreJuego() { return nombreJuego; } public void setNombreJuego(String nombreJuego) { this.nombreJuego = nombreJuego; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; } public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
}
