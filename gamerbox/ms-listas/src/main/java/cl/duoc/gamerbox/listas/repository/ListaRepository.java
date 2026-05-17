package cl.duoc.gamerbox.listas.repository;

import cl.duoc.gamerbox.listas.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListaRepository extends JpaRepository<Lista, Long> {
    List<Lista> findByIdUsuarioAndListaActivaTrue(Long idUsuario);
}
