package cl.duoc.gamerbox.resenas.service;

import cl.duoc.gamerbox.resenas.dto.ResenaDTO;
import cl.duoc.gamerbox.resenas.exceptions.BusinessException;
import cl.duoc.gamerbox.resenas.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.resenas.model.Resena;
import cl.duoc.gamerbox.resenas.repository.ResenaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class ResenaService {
    private static final Logger log = LoggerFactory.getLogger(ResenaService.class);

    private final ResenaRepository repository;
    private final WebClient.Builder webClientBuilder; // <-- 1. Inyectamos WebClient

    public ResenaService(ResenaRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Resena> listar() { return repository.findAll(); }

    public List<Resena> porUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    public Resena obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada id=" + id));
    }

    public List<Resena> porJuego(Long idJuego) { return repository.findByIdJuegoAndEstadoTrue(idJuego); }

    public Resena crear(ResenaDTO dto) {
        log.info("Validando si el juego y el usuario existen en el ecosistema...");

        // 2. Validar existencia del Juego llamando a ms-juegos
        try {
            webClientBuilder.build().get()
                    .uri("http://ms-juegos-app:8082/api/v1/juegos/" + dto.getIdJuego())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block(); // Espera la respuesta (Síncrono)
        } catch (WebClientResponseException.NotFound ex) {
            throw new BusinessException("No se puede crear la reseña: El juego con ID " + dto.getIdJuego() + " no existe.");
        } catch (Exception ex) {
            throw new BusinessException("Error interno: No se pudo comunicar con el catálogo de juegos.");
        }

        // 3. Validar existencia del Usuario llamando a ms-usuarios
        try {
            webClientBuilder.build().get()
                    .uri("http://ms-usuarios-app:8081/api/v1/usuarios/" + dto.getIdUsuario())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new BusinessException("No se puede crear la reseña: El usuario con ID " + dto.getIdUsuario() + " no existe.");
        } catch (Exception ex) {
            throw new BusinessException("Error interno: No se pudo comunicar con la base de usuarios.");
        }

        // 4. Si ambas llamadas fueron exitosas (no lanzaron error), procedemos a guardar
        log.info("Validaciones superadas. Generando reseña para juego={} usuario={}", dto.getIdJuego(), dto.getIdUsuario());
        Resena r = new Resena(null, dto.getIdJuego(), dto.getIdUsuario(), dto.getTitulo(),
                dto.getCuerpo(), dto.getPlataforma(), dto.getCalificacion(), null, null);
        return repository.save(r);
    }

    public Resena actualizar(Long id, ResenaDTO dto) {
        Resena r = obtener(id);
        if (!r.getEstado()) throw new BusinessException("No se puede editar una reseña desactivada");
        r.setTitulo(dto.getTitulo()); r.setCuerpo(dto.getCuerpo());
        r.setPlataforma(dto.getPlataforma()); r.setCalificacion(dto.getCalificacion());
        return repository.save(r);
    }

    public Resena desactivar(Long id) {
        Resena r = obtener(id);
        if (!r.getEstado()) throw new BusinessException("La reseña " + id + " ya se encuentra desactivada");
        r.setEstado(false);
        log.info("Reseña id={} desactivada (borrado lógico)", id);
        return repository.save(r);
    }
}