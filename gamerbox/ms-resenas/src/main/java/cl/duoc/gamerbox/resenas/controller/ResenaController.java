package cl.duoc.gamerbox.resenas.controller;
import cl.duoc.gamerbox.resenas.dto.ResenaDTO;
import cl.duoc.gamerbox.resenas.model.Resena;
import cl.duoc.gamerbox.resenas.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {

    private final ResenaService service;

    public ResenaController(ResenaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Resena>> listar() { return ResponseEntity.ok(service.listar()); }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }

    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<Resena>> porJuego(@PathVariable Long idJuego) {
        return ResponseEntity.ok(service.porJuego(idJuego));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Resena>> porUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(service.porUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<Resena> crear(@Valid @RequestBody ResenaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizar(@PathVariable Long id, @Valid @RequestBody ResenaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Resena> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }
}
