package cl.duoc.gamerbox.comentarios.controller;

import cl.duoc.gamerbox.comentarios.dto.ComentarioDto;
import cl.duoc.gamerbox.comentarios.model.Comentario;
import cl.duoc.gamerbox.comentarios.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {
    private final ComentarioService service;
    public ComentarioController(ComentarioService service) { this.service = service; }

    @GetMapping("/resena/{idResena}")
    public ResponseEntity<List<Comentario>> porResena(@PathVariable Long idResena) { return ResponseEntity.ok(service.porResena(idResena)); }

    @PostMapping
    public ResponseEntity<Comentario> crear(@Valid @RequestBody ComentarioDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Comentario> desactivar(@PathVariable Long id) { return ResponseEntity.ok(service.desactivar(id)); }
}