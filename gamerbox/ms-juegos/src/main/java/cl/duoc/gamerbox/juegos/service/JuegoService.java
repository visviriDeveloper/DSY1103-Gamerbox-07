package cl.duoc.gamerbox.juegos.service;

import cl.duoc.gamerbox.juegos.dto.JuegoDto;
import cl.duoc.gamerbox.juegos.exceptions.BusinessException;
import cl.duoc.gamerbox.juegos.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.juegos.model.Juego;
import cl.duoc.gamerbox.juegos.repository.JuegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuegoService {

    private final JuegoRepository repository;

    public JuegoService(JuegoRepository repository) { this.repository = repository; }

    public List<Juego> listarActivos() { return repository.findByEstadoTrue(); }

    public Juego obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Juego no encontrado id=" + id));
    }

    public List<Juego> buscarPorNombre(String nombre) {
        return repository.findByNombreJuegoContainingIgnoreCaseAndEstadoTrue(nombre);
    }

    public Juego crear(JuegoDto dto) {
        Juego j = new Juego();
        j.setNombreJuego(dto.getNombreJuego());
        j.setPlataforma(dto.getPlataforma());
        j.setFechaLanzamiento(dto.getFechaLanzamiento());
        return repository.save(j);
    }

    public Juego desactivar(Long id) {
        Juego j = obtener(id);
        if (!j.getEstado()) throw new BusinessException("El juego ya está desactivado");
        j.setEstado(false);
        return repository.save(j);
    }
}
