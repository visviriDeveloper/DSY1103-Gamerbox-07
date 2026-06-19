package cl.duoc.gamerbox.comentarios.controller;

import cl.duoc.gamerbox.comentarios.dto.ComentarioDto;
import cl.duoc.gamerbox.comentarios.model.Comentario;
import cl.duoc.gamerbox.comentarios.service.ComentarioService;
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
@RequestMapping("/api/v1/comentarios")
@Tag(name = "Sistema de Comentarios", description = "Endpoints para la publicación, consulta y moderación de comentarios en reseñas")
public class ComentarioController {
    private final ComentarioService service;
    public ComentarioController(ComentarioService service) { this.service = service; }

    @Operation(summary = "Obtener comentarios por reseña", description = "Retorna una lista con todos los comentarios activos asociados a una reseña específica.")
    @GetMapping("/resena/{idResena}")
    public ResponseEntity<List<Comentario>> porResena(
            @Parameter(description = "ID de la reseña", example = "15")@PathVariable Long idResena) { return ResponseEntity.ok(service.porResena(idResena)); }

    @Operation(summary = "Publicar un comentario", description = "Registra un nuevo comentario en la base de datos asociado a un usuario y una reseña.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comentario publicado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación de datos (ej. texto mayor a 500 caracteres)")
    })
    @PostMapping
    public ResponseEntity<Comentario> crear(@Parameter(description = "Cuerpo del comentario a publicar")
                                                @Valid @RequestBody ComentarioDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }

    @Operation(summary = "Ocultar comentario (Borrado lógico)", description = "Cambia el estado del comentario a inactivo para que deje de mostrarse en la reseña.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentario ocultado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El comentario especificado no existe")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Comentario> desactivar(@PathVariable Long id) { return ResponseEntity.ok(service.desactivar(id)); }
}