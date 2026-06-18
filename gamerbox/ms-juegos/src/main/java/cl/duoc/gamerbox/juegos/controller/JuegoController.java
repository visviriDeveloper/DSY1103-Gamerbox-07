package cl.duoc.gamerbox.juegos.controller;

import cl.duoc.gamerbox.juegos.dto.JuegoDto;
import cl.duoc.gamerbox.juegos.model.Juego;
import cl.duoc.gamerbox.juegos.service.JuegoService;
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
@RequestMapping("/api/v1/juegos")
@Tag(name = "Catálogo de Juegos", description = "Endpoints para la gestión del inventario central de videojuegos de la plataforma")
public class JuegoController {

    private final JuegoService service;

    public JuegoController(JuegoService service) { this.service = service; }

    @Operation(summary = "Listar todos los juegos", description = "Retorna una lista completa de todos los videojuegos registrados y activos en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Lista de juegos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Juego>> listar() {
        return ResponseEntity.ok(service.listarActivos());
    }

    @Operation(summary = "Obtener un juego por ID", description = "Busca y retorna los detalles exactos de un juego utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Juego encontrado de manera exitosa"),
            @ApiResponse(responseCode = "404", description = "El juego con el ID proporcionado no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Juego> obtener(
            @Parameter(description = "ID único del juego a buscar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Registrar un nuevo juego", description = "Inserta un nuevo videojuego en el catálogo. Este endpoint valida el formato de la plataforma y datos obligatorios.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Juego creado exitosamente en la base de datos"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados (ej. Plataforma inválida)")
    })
    @PostMapping
    public ResponseEntity<Juego> crear(
            @Parameter(description = "Objeto DTO con los datos del nuevo juego")
            @Valid @RequestBody JuegoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Desactivar un juego (Borrado lógico)", description = "Cambia el estado del juego a inactivo para que no aparezca en las búsquedas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Juego desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "El juego a desactivar no existe")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Juego> desactivar(
            @Parameter(description = "ID del juego a desactivar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }
}

