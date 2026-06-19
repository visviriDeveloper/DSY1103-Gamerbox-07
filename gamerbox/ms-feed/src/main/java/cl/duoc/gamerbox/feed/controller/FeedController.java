package cl.duoc.gamerbox.feed.controller;

import cl.duoc.gamerbox.feed.dto.FeedDto;
import cl.duoc.gamerbox.feed.model.Feed;
import cl.duoc.gamerbox.feed.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
@Tag(name = "Sistema de Feed", description = "Endpoints para la construcción y consulta de los muros de actividad de los jugadores")
public class FeedController {
    private final FeedService service;
    public FeedController(FeedService service) { this.service = service; }

    @Operation(summary = "Obtener Timeline de Usuario", description = "Genera y retorna la línea de tiempo dinámica combinando la actividad del usuario y de las personas a las que sigue.")
    @GetMapping("/timeline/{idUsuario}")
    public ResponseEntity<List<Object>> obtenerFeedDinamico(@Parameter(description = "ID del usuario para generar su muro", example = "42")
                                                                @PathVariable Long idUsuario) { return ResponseEntity.ok(service.generarTimelineUsuario(idUsuario)); }

    @Operation(summary = "Registrar actividad en el muro", description = "Guarda un nuevo evento en el feed de un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Actividad registrada con éxito"),
            @ApiResponse(responseCode = "400", description = "Error de validación, IDs no proporcionados o inválidos")
    })
    @PostMapping
    public ResponseEntity<Feed> registrar(@Parameter(description = "Datos necesarios para identificar el evento")
                                              @Valid @RequestBody FeedDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarActividadMuro(dto)); }
}