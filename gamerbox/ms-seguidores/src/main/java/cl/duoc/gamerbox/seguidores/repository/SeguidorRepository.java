package cl.duoc.gamerbox.seguidores.repository;

import cl.duoc.gamerbox.seguidores.model.Seguidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {
    List<Seguidor> findByIdSeguidorAndActivoTrue(Long idSeguidor);
    List<Seguidor> findByIdSeguidoAndActivoTrue(Long idSeguido);
    Optional<Seguidor> findByIdSeguidorAndIdSeguidoAndActivoTrue(Long idSeguidor, Long idSeguido);

}
