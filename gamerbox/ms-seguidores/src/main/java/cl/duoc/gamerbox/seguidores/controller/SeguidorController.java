package cl.duoc.gamerbox.seguidores.controller;

import cl.duoc.gamerbox.seguidores.seguidoresDTO.SeguidorDTO;
import cl.duoc.gamerbox.seguidores.model.Seguidor;
import cl.duoc.gamerbox.seguidores.service.SeguidorService;
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
@RequestMapping("/api/v1/seguidores")
@Tag(name = "Sistema de Seguidores", description = "Endpoints para la gestión de las relaciones y conexiones sociales entre los usuarios")
public class SeguidorController {
    private final SeguidorService service;
    public SeguidorController(SeguidorService service) { this.service = service; }

    @Operation(summary = "Listar usuarios seguidos", description = "Retorna una lista con todas las relaciones activas donde el usuario especificado es el seguidor.")
    @GetMapping("/siguiendo/{idSeguidor}")
    public ResponseEntity<List<Seguidor>> listarSiguiendo(@Parameter(description = "ID del usuario para buscar a quién sigue", example = "42")
                                                              @PathVariable Long idSeguidor) { return ResponseEntity.ok(service.listarSiguiendo(idSeguidor)); }

    @Operation(summary = "Comenzar a seguir a un usuario", description = "Crea un nuevo registro de seguimiento entre dos usuarios válidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Relación de seguimiento creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. IDs nulos o negativos)")
    })
    @PostMapping
    public ResponseEntity<Seguidor> seguir(@Parameter(description = "Estructura con los IDs del seguidor y el seguido")
                                               @Valid @RequestBody SeguidorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.seguir(dto)); }


    @Operation(summary = "Dejar de seguir (Borrado lógico)", description = "Desactiva una relación de seguimiento existente, cambiando su estado a inactivo para mantener el historial.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se ha dejado de seguir al usuario exitosamente"),
            @ApiResponse(responseCode = "404", description = "El registro de seguimiento no existe")
    })
    @PatchMapping("/{id}/dejardeseguir")
    public ResponseEntity<Seguidor> dejarDeSeguir(@Parameter(description = "ID único del registro de seguimiento a desactivar", example = "1")
                                                      @PathVariable Long id) { return ResponseEntity.ok(service.dejarDeSeguir(id)); }
}