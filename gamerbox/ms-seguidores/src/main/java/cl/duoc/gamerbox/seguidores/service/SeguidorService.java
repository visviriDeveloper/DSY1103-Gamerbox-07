package cl.duoc.gamerbox.seguidores.service;

import cl.duoc.gamerbox.seguidores.seguidoresDTO.SeguidorDTO;
import cl.duoc.gamerbox.seguidores.exceptions.BusinessException;
import cl.duoc.gamerbox.seguidores.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.seguidores.model.Seguidor;
import cl.duoc.gamerbox.seguidores.repository.SeguidorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

@Service
public class SeguidorService {
    private final SeguidorRepository repository;
    private final WebClient.Builder webClientBuilder;

    public SeguidorService(SeguidorRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Seguidor> listarSiguiendo(Long idSeguidor) { return repository.findByIdSeguidorAndActivoTrue(idSeguidor); }

    public Seguidor seguir(SeguidorDTO dto) {
        if (dto.getIdSeguidor().equals(dto.getIdSeguido())) throw new BusinessException("Un usuario no puede seguirse a sí mismo.");

        // Validar que el seguidor existe
        validarUsuarioEcosistema(dto.getIdSeguidor(), "Seguidor");
        // Validar que el seguido existe
        validarUsuarioEcosistema(dto.getIdSeguido(), "Seguido");

        if (repository.findByIdSeguidorAndIdSeguidoAndActivoTrue(dto.getIdSeguidor(), dto.getIdSeguido()).isPresent()) {
            throw new BusinessException("Ya sigues a este usuario.");
        }

        Seguidor s = new Seguidor();
        s.setIdSeguidor(dto.getIdSeguidor());
        s.setIdSeguido(dto.getIdSeguido());
        return repository.save(s);
    }

    public Seguidor dejarDeSeguir(Long id) {
        Seguidor s = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro de seguimiento no encontrado"));
        if (!s.getActivo()) throw new BusinessException("Ya no sigues a este usuario.");
        s.setActivo(false);
        return repository.save(s);
    }

    private void validarUsuarioEcosistema(Long id, String rol) {
        try {
            webClientBuilder.build().get().uri("http://ms-usuarios-app:8081/api/v1/usuarios/" + id)
                    .retrieve().bodyToMono(Object.class).block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new BusinessException("El " + rol + " con ID " + id + " no existe.");
        } catch (Exception e) { throw new BusinessException("Error al conectar con ms-usuarios."); }
    }
}