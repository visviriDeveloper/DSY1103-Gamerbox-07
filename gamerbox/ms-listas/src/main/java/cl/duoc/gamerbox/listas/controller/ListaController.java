package cl.duoc.gamerbox.listas.controller;

import cl.duoc.gamerbox.listas.dto.ListaDto;
import cl.duoc.gamerbox.listas.model.Lista;
import cl.duoc.gamerbox.listas.service.ListaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/listas")
public class ListaController {
    private final ListaService service;
    public ListaController(ListaService service) { this.service = service; }

    @GetMapping("/{id}")
    public ResponseEntity<Lista> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Lista>> porUsuario(@PathVariable Long idUsuario) { return ResponseEntity.ok(service.porUsuario(idUsuario)); }

    @PostMapping
    public ResponseEntity<Lista> crear(@Valid @RequestBody ListaDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Lista> desactivar(@PathVariable Long id) { return ResponseEntity.ok(service.desactivar(id)); }
}