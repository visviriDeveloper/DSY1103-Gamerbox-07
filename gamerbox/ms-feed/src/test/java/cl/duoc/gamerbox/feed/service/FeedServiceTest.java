package cl.duoc.gamerbox.feed.service;

import cl.duoc.gamerbox.feed.repository.FeedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Mock private FeedRepository repository;
    @Mock private WebClient.Builder webClientBuilder;
    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private FeedService feedService;

    @Test
    void Given_UsuarioConSeguidores_When_GenerarTimeline_Then_RetornaListaConResenas() {
        // Given
        Long idUsuario = 1L;
        // Simulamos que el usuario sigue a alguien con id "50"
        List<Map<String, Object>> listaSeguidos = List.of(Map.of("idSeguido", 50));
        List<Object> listaResenas = List.of(new Object()); // Una reseña encontrada

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        // Simulamos las dos llamadas encadenadas en tu servicio
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(listaSeguidos)) // 1ra llamada: quién sigue
                .thenReturn(Mono.just(listaResenas));  // 2da llamada: reseñas del seguido

        // When
        List<Object> resultado = feedService.generarTimelineUsuario(idUsuario);

        // Then
        assertThat(resultado).isNotEmpty(); // Ahora sí contendrá el objeto que mockeamos
        verify(webClientBuilder, times(2)).build(); // Se llamó 2 veces: 1 para seguidores, 1 para reseñas
    }
}