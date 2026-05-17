package cl.duoc.gamerbox.listas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ListaDto {

    @NotNull @Positive
    private Long idUsuario;

    @NotBlank @Size(max = 100)
    private String nombreLista;

    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreLista() { return nombreLista; } public void setNombreLista(String nombreLista) { this.nombreLista = nombreLista; }
}
