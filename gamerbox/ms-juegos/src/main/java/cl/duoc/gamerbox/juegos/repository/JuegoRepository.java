package cl.duoc.gamerbox.juegos.repository;

import cl.duoc.gamerbox.juegos.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findByEstadoTrue();
    List<Juego> findByNombreJuegoContainingIgnoreCaseAndEstadoTrue(String nombre);
}

