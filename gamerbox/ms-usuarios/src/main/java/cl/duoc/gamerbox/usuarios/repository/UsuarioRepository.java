package cl.duoc.gamerbox.usuarios.repository;

import cl.duoc.gamerbox.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreoUsuario(String correoUsuario);

    Boolean existsByCorreoUsuario(String correoUsuario);
}
