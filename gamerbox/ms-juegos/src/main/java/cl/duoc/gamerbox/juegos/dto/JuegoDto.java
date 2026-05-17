package cl.duoc.gamerbox.juegos.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class JuegoDto {

    @NotBlank
    private String nombreJuego;

    @NotBlank
    @Pattern(regexp = "PC|PS5|PS4|XBOX_SERIES|XBOX_ONE|NINTENDO_SWITCH", message = "Plataforma no válida")
    private String plataforma;

    @NotNull
    private LocalDate fechaLanzamiento;

    public String getNombreJuego() { return nombreJuego; } public void setNombreJuego(String nombreJuego) { this.nombreJuego = nombreJuego; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; } public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
}
