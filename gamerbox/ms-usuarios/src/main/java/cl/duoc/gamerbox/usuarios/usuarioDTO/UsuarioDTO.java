package cl.duoc.gamerbox.usuarios.usuarioDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UsuarioDTO(
                Long idUsuario,

                @NotBlank(message = "El nombre de usuario no puede estar vacio")
                @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres")
                String nombreUsuario,

                @NotBlank(message = "El nombre del correo no puede estar vacio")
                @Email(message = "El correo debe ser valido")
                String correoUsuario,

                @NotBlank(message = "La contraseña es obligatoria vacia")
                @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
                @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                String passwordUsuario,
                LocalDateTime fechaRegistro,
                Boolean activo

) {}
