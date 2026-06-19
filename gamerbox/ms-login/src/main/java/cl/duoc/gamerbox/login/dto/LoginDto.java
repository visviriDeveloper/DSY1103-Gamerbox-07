package cl.duoc.gamerbox.login.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "Objeto que recibe las credenciales del usuario para iniciar sesión")
public class LoginDto {

    @NotBlank @Email
    @Schema(description = "Correo electrónico registrado del usuario", requiredMode = Schema.RequiredMode.REQUIRED, example = "jugador@gamerbox.cl")
    private String correoUsuario;

    @NotBlank
    @Schema(description = "Contraseña en texto plano para ser validada", requiredMode = Schema.RequiredMode.REQUIRED, example = "MiPasswordSecreto123")
    private String passwordUsuario;

    public String getCorreoUsuario() { return correoUsuario; } public void setCorreoUsuario(String c) { this.correoUsuario = c; }
    public String getPasswordUsuario() { return passwordUsuario; } public void setPasswordUsuario(String p) { this.passwordUsuario = p; }
}
