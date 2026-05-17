package cl.duoc.gamerbox.feed.controller;

import cl.duoc.gamerbox.feed.dto.FeedDto;
import cl.duoc.gamerbox.feed.model.Feed;
import cl.duoc.gamerbox.feed.service.FeedService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {
    private final FeedService service;
    public FeedController(FeedService service) { this.service = service; }

    @GetMapping("/timeline/{idUsuario}")
    public ResponseEntity<List<Object>> obtenerFeedDinamico(@PathVariable Long idUsuario) { return ResponseEntity.ok(service.generarTimelineUsuario(idUsuario)); }

    @PostMapping
    public ResponseEntity<Feed> registrar(@Valid @RequestBody FeedDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarActividadMuro(dto)); }
}