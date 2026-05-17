package cl.duoc.gamerbox.resenas.repository;
import cl.duoc.gamerbox.resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByIdJuegoAndEstadoTrue(Long idJuego);
    List<Resena> findByIdUsuario(Long idUsuario);
}

