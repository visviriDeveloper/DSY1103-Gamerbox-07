package com.melisoft.ms_resenias.Repository;

import com.melisoft.ms_resenias.Model.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long> {
    List<Resenia> findByEstadoTrue();
    List<Resenia> findByIdJuegoAndEstadoTrue(Long idJuego);
}