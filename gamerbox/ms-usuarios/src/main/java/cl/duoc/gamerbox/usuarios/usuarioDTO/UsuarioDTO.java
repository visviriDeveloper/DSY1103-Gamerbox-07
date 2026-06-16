package cl.duoc.gamerbox.usuarios.usuarioDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
@Schema(
        name = "UsuarioDTO",
        description = "Este DTO representa los datos necesarios del modelo de  usuario en Gamerbox"
)
public record UsuarioDTO(

                @Schema(
                        description = " El Identificador único, este atributo es autogenerado dentro del model",
                        example = "1"
                )
                Long idUsuario,

                @Schema(
                        description = "Nombre que tendra el usuario dentro de Gamerbox",
                        example = "Solaire_Astora28"
                )
                @NotBlank(message = "El nombre de usuario no puede estar vacio")
                @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres")
                String nombreUsuario,

                @Schema(
                        description = "El correo electrónico que se vinculara a la cuenta de usuario dentro de Gamerbox",
                        example = "MatiGaymer69@Gmail.com"
                )
                @NotBlank(message = "El nombre del correo no puede estar vacio")
                @Email(message = "El correo debe ser valido")
                String correoUsuario,

                @Schema(
                        description = "La contraseña unica del usuario.\nEn fase de desarrollo, se visualizara sin encriptacion.",
                        example = "KagurabachibestAnime4ever"
                )
                @NotBlank(message = "La contraseña es obligatoria vacia")
                @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
                @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                String passwordUsuario,
                @Schema(
                        description = "Fecha de registro de la creacion exitosa del usuario dentro del microservicio." +
                                "Es autogenerada.",
                        example = "2026-12-03T10:15:30"
                )
                LocalDateTime fechaRegistro,
                @Schema(
                        description = "Valor boolean que representa el estado(activada/desactivada) de la cuenta de usuario",example = "True"
                )
                Boolean activo

) {}
