package cl.duoc.gamerbox.login.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank @Email
    private String correoUsuario;

    @NotBlank
    private String passwordUsuario;

    public String getCorreoUsuario() { return correoUsuario; } public void setCorreoUsuario(String c) { this.correoUsuario = c; }
    public String getPasswordUsuario() { return passwordUsuario; } public void setPasswordUsuario(String p) { this.passwordUsuario = p; }
}
