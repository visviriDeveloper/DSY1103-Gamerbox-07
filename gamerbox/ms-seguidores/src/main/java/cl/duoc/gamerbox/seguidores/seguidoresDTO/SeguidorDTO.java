package cl.duoc.gamerbox.seguidores.seguidoresDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SeguidorDTO {
    @NotNull
    @Positive
    private Long idSeguidor;

    @NotNull @Positive
    private Long idSeguido;

    public Long getIdSeguidor() { return idSeguidor; } public void setIdSeguidor(Long id) { this.idSeguidor = id; }
    public Long getIdSeguido() { return idSeguido; } public void setIdSeguido(Long id) { this.idSeguido = id; }

}
