package cl.duoc.gamerbox.listas.service;

import cl.duoc.gamerbox.listas.dto.ListaDto;
import cl.duoc.gamerbox.listas.exceptions.BusinessException;
import cl.duoc.gamerbox.listas.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.listas.model.Lista;
import cl.duoc.gamerbox.listas.repository.ListaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

@Service
public class ListaService {
    private final ListaRepository repository;
    private final WebClient.Builder webClientBuilder;

    public ListaService(ListaRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public Lista obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lista no encontrada id=" + id));
    }

    public List<Lista> porUsuario(Long idUsuario) { return repository.findByIdUsuarioAndListaActivaTrue(idUsuario); }

    public Lista crear(ListaDto dto) {
        try {
            webClientBuilder.build().get()
                    .uri("http://ms-usuarios-app:8081/api/v1/usuarios/" + dto.getIdUsuario())
                    .retrieve().bodyToMono(Object.class).block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new BusinessException("No se puede crear la lista: El usuario con ID " + dto.getIdUsuario() + " no existe.");
        } catch (Exception ex) {
            throw new BusinessException("Error de comunicación con el microservicio de usuarios.");
        }

        Lista l = new Lista();
        l.setIdUsuario(dto.getIdUsuario());
        l.setNombreLista(dto.getNombreLista());
        return repository.save(l);
    }

    public Lista desactivar(Long id) {
        Lista l = obtener(id);
        if (!l.getListaActiva()) throw new BusinessException("La lista ya se encuentra desactivada");
        l.setListaActiva(false);
        return repository.save(l);
    }
}