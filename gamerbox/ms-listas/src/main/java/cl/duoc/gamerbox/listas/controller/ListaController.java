package cl.duoc.gamerbox.listas.controller;

import cl.duoc.gamerbox.listas.dto.ListaDto;
import cl.duoc.gamerbox.listas.model.Lista;
import cl.duoc.gamerbox.listas.service.ListaService;
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
@RequestMapping("/api/v1/listas")
@Tag(name = "Gestión de Listas", description = "Endpoints para la creación, consulta y desactivación de listas de videojuegos de los usuarios")
public class ListaController {
    private final ListaService service;
    public ListaController(ListaService service) { this.service = service; }

    @Operation(summary = "Obtener lista por ID", description = "Retorna los detalles de una lista específica usando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La lista no existe o está desactivada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Lista> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }

    @Operation(summary = "Obtener listas de un usuario", description = "Devuelve una colección con todas las listas activas creadas por un usuario en particular.")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Lista>> porUsuario(@Parameter(description = "ID del usuario propietario", example = "42") @PathVariable Long idUsuario) { return ResponseEntity.ok(service.porUsuario(idUsuario)); }

    @Operation(summary = "Crear nueva lista", description = "Genera una nueva lista vacía asociada a un usuario. Valida que los campos no estén vacíos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lista creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en el cuerpo de la petición")
    })
    @PostMapping
    public ResponseEntity<Lista> crear(@Parameter(description = "Datos requeridos para inicializar la lista") @Valid @RequestBody ListaDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }

    @Operation(summary = "Desactivar lista (Borrado lógico)", description = "Oculta la lista cambiando su estado a inactivo en lugar de eliminarla físicamente de la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista desactivada correctamente"),
            @ApiResponse(responseCode = "404", description = "La lista especificada no existe")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Lista> desactivar(@Parameter(description = "ID de la lista a desactivar", example = "1") @PathVariable Long id) { return ResponseEntity.ok(service.desactivar(id)); }
}