package cl.duoc.gamerbox.usuarios.controller;

import cl.duoc.gamerbox.usuarios.service.UsuarioService;
import cl.duoc.gamerbox.usuarios.usuarioDTO.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/usuarios")

@Tag(name = "Gestión de Usuarios", description = "Endpoints para el registro, consulta y modificación de las cuentas de usuario de la plataforma")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Listar a todos los usuarios", description = "Obtiene una lista completa con los perfiles de todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }


    @Operation(summary = "Buscar usuario por ID", description = "Obtiene los detalles de un usuario específico mediante su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe en la base de datos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(
            @Parameter(description = "ID del usuario a consultar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Valida y crea una nueva cuenta de usuario en la plataforma.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado y guardado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación (Ej. correo inválido o contraseña corta)")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(
            @Parameter(description = "Datos requeridos para la creación del usuario")
            @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO nuevoUsuario = usuarioService.crear(dto);
        // Retornamos 201 CREATED para indicar que el recurso se guardó exitosamente
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar perfil de usuario", description = "Modifica los datos existentes de una cuenta de usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El usuario a modificar no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario a modificar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar usuario", description = "Borra físicamente a un usuario del sistema. Retorna un código 204 sin contenido si la operación es exitosa.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El usuario especificado no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "1")
            @PathVariable Long id) {
        usuarioService.eliminar(id);
        // Retornamos 204 NO CONTENT, estándar para borrados exitosos sin cuerpo de respuesta
        return ResponseEntity.noContent().build();
    }
}

