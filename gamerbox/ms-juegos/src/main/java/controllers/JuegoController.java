package controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Juego;
import persistence.repository.JuegoRepository;

@RestController
@RequestMapping("/api/juegos")
public class JuegoController {
    @Autowired
    private JuegoRepository repository;

    @GetMapping
    public ResponseEntity<List<Juego>> obtenerCatalogo(@RequestParam(required = false) String nombre) {
        if (nombre != null) {
            return ResponseEntity.ok(repository.findByNombreJuegoContainingIgnoreCaseAndEstadoTrue(nombre));
        }
        return ResponseEntity.ok(repository.findByEstadoTrue());
    }

    @PostMapping
    public ResponseEntity<Juego> registrarJuego(@RequestBody Juego juego) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(juego));
    }
}


