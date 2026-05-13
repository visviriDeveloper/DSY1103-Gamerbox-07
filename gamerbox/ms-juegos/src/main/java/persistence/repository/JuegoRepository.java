package persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import persistence.models.enums.Juego;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    
    List<Juego> findByEstadoTrue();

    
    List<Juego> findByNombreJuegoContainingIgnoreCaseAndEstadoTrue(String nombre);
}