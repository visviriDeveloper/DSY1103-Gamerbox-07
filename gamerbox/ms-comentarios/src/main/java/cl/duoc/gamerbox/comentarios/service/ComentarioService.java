package cl.duoc.gamerbox.comentarios.service;

import cl.duoc.gamerbox.comentarios.dto.ComentarioDto;
import cl.duoc.gamerbox.comentarios.exceptions.BusinessException;
import cl.duoc.gamerbox.comentarios.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.comentarios.model.Comentario;
import cl.duoc.gamerbox.comentarios.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

@Service
public class ComentarioService {
    private final ComentarioRepository repository;
    private final WebClient.Builder webClientBuilder;

    public ComentarioService(ComentarioRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Comentario> porResena(Long idResena) { return repository.findByIdResenaAndComentarioActivoTrue(idResena); }

    public Comentario crear(ComentarioDto dto) {
        // 1. Validar Usuario
        try {
            webClientBuilder.build().get().uri("http://ms-usuarios-app:8081/api/v1/usuarios/" + dto.getIdUsuario())
                    .retrieve().bodyToMono(Object.class).block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new BusinessException("El usuario con ID " + dto.getIdUsuario() + " no existe.");
        } catch (Exception e) { throw new BusinessException("Error al conectar con ms-usuarios."); }

        // 2. Validar Reseña
        try {
            webClientBuilder.build().get().uri("http://ms-resenas-app:8083/api/v1/resenas/" + dto.getIdResena())
                    .retrieve().bodyToMono(Object.class).block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new BusinessException("La reseña con ID " + dto.getIdResena() + " no existe.");
        } catch (Exception e) { throw new BusinessException("Error al conectar con ms-resenas."); }

        Comentario c = new Comentario();
        c.setIdUsuario(dto.getIdUsuario());
        c.setIdResena(dto.getIdResena());
        c.setTextoComentario(dto.getTextoComentario());
        return repository.save(c);
    }

    public Comentario desactivar(Long id) {
        Comentario c = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));
        if (!c.getComentarioActivo()) throw new BusinessException("El comentario ya se encuentra inactivo");
        c.setComentarioActivo(false);
        return repository.save(c);
    }
}