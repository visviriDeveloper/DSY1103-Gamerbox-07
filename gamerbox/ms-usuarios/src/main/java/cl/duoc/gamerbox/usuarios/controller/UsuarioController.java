package cl.duoc.gamerbox.usuarios.controller;

import cl.duoc.gamerbox.usuarios.service.UsuarioService;
import cl.duoc.gamerbox.usuarios.usuarioDTO.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/usuarios")

@Tag(
        name = "Usuarios",
        description = "Operaciones relacionadas con usuarios"
)
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Listar a todos los usuarios",
            description = "Obtiene el total de usuarios registrados.")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa"
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }


    @Operation(
            summary = "Listar usuario por su ID",
            description = "Obtiene a un usuario especifico mediante su identificador unico")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(
            summary = "Registro de usuario",
            description = "Permite la creacion de un usuario.")
    @ApiResponse(
            responseCode = "201",
            description = "Indica que el recurso (usuario) se creo y guardo exitosamente."
    )
    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO nuevoUsuario = usuarioService.crear(dto);
        // Retornamos 201 CREATED para indicar que el recurso se guardó exitosamente
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Permite actualizar todos los datos de usuario")
    @ApiResponse(
            responseCode = "201",
            description = "Indica que el recurso (usuario) se actualizo y guardo exitosamente."
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Permite eliminar a un usuario")
    @ApiResponse(
            responseCode = "204",
            description = "Indica que el recurso (usuario) fue borrado exitosamente, no enviara datos por el body del JSON."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        // Retornamos 204 NO CONTENT, estándar para borrados exitosos sin cuerpo de respuesta
        return ResponseEntity.noContent().build();
    }
}

