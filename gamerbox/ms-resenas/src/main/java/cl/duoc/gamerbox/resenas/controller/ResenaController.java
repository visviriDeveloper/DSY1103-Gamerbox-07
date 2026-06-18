package cl.duoc.gamerbox.resenas.controller;
import cl.duoc.gamerbox.resenas.dto.ResenaDTO;
import cl.duoc.gamerbox.resenas.model.Resena;
import cl.duoc.gamerbox.resenas.service.ResenaService;
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
@RequestMapping("/api/v1/resenas")
@Tag(name = "Sistema de Reseñas", description = "Endpoints para la gestión, publicación y lectura de las reseñas sobre los videojuegos")
public class ResenaController {

    private final ResenaService service;

    public ResenaController(ResenaService service) { this.service = service; }
    @Operation(summary = "Listar todas las reseñas", description = "Retorna el feed general de todas las reseñas activas en la plataforma.")
    @GetMapping
    public ResponseEntity<List<Resena>> listar() { return ResponseEntity.ok(service.listar()); }

    @Operation(summary = "Obtener reseña por ID", description = "Busca los detalles completos de una reseña específica.")
    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtener(@Parameter(description = "ID único de la reseña") @PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }

    @Operation(summary = "Filtrar reseñas por Juego", description = "Obtiene todas las opiniones publicadas sobre un juego en particular.")
    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<Resena>> porJuego(@Parameter(description = "ID del juego en el catálogo") @PathVariable Long idJuego) {
        return ResponseEntity.ok(service.porJuego(idJuego));
    }

    @Operation(summary = "Filtrar reseñas por Usuario", description = "Devuelve el historial de opiniones escritas por un usuario específico.")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Resena>> porUsuario(@Parameter(description = "ID del usuario autor") @PathVariable Long idUsuario) {
        return ResponseEntity.ok(service.porUsuario(idUsuario));
    }

    @Operation(summary = "Publicar nueva reseña", description = "Crea una nueva opinión validando plataformas y rango de estrellas. Requiere autenticación.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reseña publicada con éxito"),
            @ApiResponse(responseCode = "400", description = "Error de validación (Ej. estrellas fuera de rango 1-5)")
    })
    @PostMapping
    public ResponseEntity<Resena> crear(@Valid @RequestBody ResenaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Actualizar reseña", description = "Modifica el título, cuerpo, plataforma o calificación de una reseña existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizar(@Parameter(description = "ID de la reseña a modificar") @PathVariable Long id, @Valid @RequestBody ResenaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Ocultar reseña (Borrado lógico)", description = "Desactiva una reseña para que no aparezca públicamente en el juego ni en el perfil del usuario.")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Resena> desactivar(@Parameter(description = "ID de la reseña a ocultar") @PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }
}
