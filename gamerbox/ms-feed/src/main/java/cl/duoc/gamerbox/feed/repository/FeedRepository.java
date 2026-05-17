package cl.duoc.gamerbox.feed.repository;

import cl.duoc.gamerbox.feed.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByIdUsuarioOrderByFechaActividadDesc(Long idUsuario);
}
