package cl.duoc.gamerbox.comentarios.repository;

import cl.duoc.gamerbox.comentarios.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByIdResenaAndComentarioActivoTrue(Long idResena);
}
