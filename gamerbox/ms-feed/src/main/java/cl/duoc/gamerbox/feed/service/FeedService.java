package cl.duoc.gamerbox.feed.service;


import cl.duoc.gamerbox.feed.dto.FeedDto;
import cl.duoc.gamerbox.feed.model.Feed;
import cl.duoc.gamerbox.feed.repository.FeedRepository;
import cl.duoc.gamerbox.feed.exceptions.BusinessException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedService {
    private final FeedRepository repository;
    private final WebClient.Builder webClientBuilder;

    public FeedService(FeedRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Object> generarTimelineUsuario(Long idUsuario) {
        List<Object> timelineCompleto = new ArrayList<>();
        try {
            // 1. Consultar a quién sigue el usuario (Viene como lista de Objetos/Mapas)
            List<Map<String, Object>> seguidos = webClientBuilder.build().get()
                    .uri("http://ms-seguidores-app:8086/api/v1/seguidores/siguiendo/" + idUsuario)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                    .block();

            if (seguidos != null) {
                for (Map<String, Object> registro : seguidos) {
                    // Extraer el idSeguido de forma segura mapeando el número
                    Object idSeguidoObj = registro.get("idSeguido");
                    if (idSeguidoObj != null) {
                        String idSeguido = idSeguidoObj.toString();

                        // 2. Por cada seguido, traer sus reseñas mediante WebClient
                        List<Object> resenasSeguido = webClientBuilder.build().get()
                                .uri("http://ms-resenas-app:8083/api/v1/resenas/usuario/" + idSeguido)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                                .block();

                        if (resenasSeguido != null) {
                            timelineCompleto.addAll(resenasSeguido);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("Error al consolidar la información del Feed Dinámico: " + e.getMessage());
        }
        return timelineCompleto;
    }

    public Feed registrarActividadMuro(FeedDto dto) {
        Feed f = new Feed(); f.setIdUsuario(dto.getIdUsuario()); f.setFeed(dto.getFeed());
        return repository.save(f);
    }
}