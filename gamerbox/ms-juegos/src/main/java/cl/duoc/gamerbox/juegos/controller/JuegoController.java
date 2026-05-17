package cl.duoc.gamerbox.juegos.controller;

import cl.duoc.gamerbox.juegos.dto.JuegoDto;
import cl.duoc.gamerbox.juegos.model.Juego;
import cl.duoc.gamerbox.juegos.service.JuegoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/juegos")
public class JuegoController {

    private final JuegoService service;

    public JuegoController(JuegoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Juego>> listar() { return ResponseEntity.ok(service.listarActivos()); }

    @GetMapping("/{id}")
    public ResponseEntity<Juego> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }

    @GetMapping("/buscar")
    public ResponseEntity<List<Juego>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<Juego> crear(@Valid @RequestBody JuegoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Juego> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }
}

