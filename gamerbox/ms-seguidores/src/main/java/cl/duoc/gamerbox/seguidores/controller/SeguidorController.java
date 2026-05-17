package cl.duoc.gamerbox.seguidores.controller;

import cl.duoc.gamerbox.seguidores.seguidoresDTO.SeguidorDTO;
import cl.duoc.gamerbox.seguidores.model.Seguidor;
import cl.duoc.gamerbox.seguidores.service.SeguidorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seguidores")
public class SeguidorController {
    private final SeguidorService service;
    public SeguidorController(SeguidorService service) { this.service = service; }

    @GetMapping("/siguiendo/{idSeguidor}")
    public ResponseEntity<List<Seguidor>> listarSiguiendo(@PathVariable Long idSeguidor) { return ResponseEntity.ok(service.listarSiguiendo(idSeguidor)); }

    @PostMapping
    public ResponseEntity<Seguidor> seguir(@Valid @RequestBody SeguidorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.seguir(dto)); }

    @PatchMapping("/{id}/dejardeseguir")
    public ResponseEntity<Seguidor> dejarDeSeguir(@PathVariable Long id) { return ResponseEntity.ok(service.dejarDeSeguir(id)); }
}