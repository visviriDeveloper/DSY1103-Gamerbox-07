package com.melisoft.ms_resenias.Controller;

import com.melisoft.ms_resenias.Model.Resenia;
import com.melisoft.ms_resenias.Service.ReseniaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenias")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @PostMapping
    public ResponseEntity<Resenia> crearResenia(@Valid @RequestBody Resenia resenia) {
        Resenia nuevaResenia = reseniaService.crearResenia(resenia);
        return new ResponseEntity<>(nuevaResenia, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resenia>> listarResenias() {
        return ResponseEntity.ok(reseniaService.obtenerTodasActivas());
    }

    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<Resenia>> listarPorJuego(@PathVariable Long idJuego) {
        return ResponseEntity.ok(reseniaService.obtenerPorJuego(idJuego));
    }

    @DeleteMapping("/{idResenia}")
    public ResponseEntity<Void> eliminarResenia(@PathVariable Long idResenia) {
        reseniaService.eliminarLogica(idResenia);
        return ResponseEntity.noContent().build();
    }
}