package cl.duoc.gamerbox.comentarios.service;

import cl.duoc.gamerbox.comentarios.dto.ComentarioDto;
import cl.duoc.gamerbox.comentarios.exceptions.BusinessException;
import cl.duoc.gamerbox.comentarios.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.comentarios.model.Comentario;
import cl.duoc.gamerbox.comentarios.repository.ComentarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository repository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ComentarioService service;

    // ── Test porResena ──────────────────────────────────────────

    @Test
    void Given_IdResenaExistente_When_PorResena_Then_RetornaLista() {
        // Given
        Comentario c = new Comentario();
        c.setIdResena(1L);
        when(repository.findByIdResenaAndComentarioActivoTrue(1L)).thenReturn(List.of(c));

        // When
        List<Comentario> resultado = service.porResena(1L);

        // Then
        assertThat(resultado).hasSize(1);
        verify(repository).findByIdResenaAndComentarioActivoTrue(1L);
    }

    // ── Tests crear ─────────────────────────────────────────────

    @Test
    void Given_DatosValidos_When_Crear_Then_RetornaComentario() {
        // Given
        ComentarioDto dto = new ComentarioDto();
        dto.setIdUsuario(1L);
        dto.setIdResena(1L);
        dto.setTextoComentario("Hola");

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));

        Comentario guardado = new Comentario();
        when(repository.save(any(Comentario.class))).thenReturn(guardado);

        // When
        Comentario resultado = service.crear(dto);

        // Then
        assertThat(resultado).isNotNull();
        verify(repository).save(any(Comentario.class));
    }

    @Test
    void Given_UsuarioNoExiste_When_Crear_Then_LanzaBusinessException() {
        // Given
        ComentarioDto dto = new ComentarioDto();
        dto.setIdUsuario(99L);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null)));

        // When & Then
        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("no existe");
    }

    // ── Tests desactivar ────────────────────────────────────────

    @Test
    void Given_ComentarioActivo_When_Desactivar_Then_GuardaInactivo() {
        // Given
        Comentario c = new Comentario();
        c.setComentarioActivo(true);
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        when(repository.save(any(Comentario.class))).thenReturn(c);

        // When
        service.desactivar(1L);

        // Then
        assertThat(c.getComentarioActivo()).isFalse();
        verify(repository).save(c);
    }

    @Test
    void Given_ComentarioInexistente_When_Desactivar_Then_LanzaResourceNotFound() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.desactivar(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
    @Test
    void Given_ResenaNoExiste_When_Crear_Then_LanzaBusinessException() {
        // Given
        ComentarioDto dto = new ComentarioDto();
        dto.setIdUsuario(1L); // Usuario existe (mockeado abajo)
        dto.setIdResena(99L); // Reseña no existe

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        // Mockeamos el usuario OK, pero la reseña falla (404)
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object())) // Usuario OK
                .thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null))); // Reseña falla

        // Then
        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("La reseña con ID 99 no existe");
    }
    @Test
    void Given_ComentarioYaInactivo_When_Desactivar_Then_LanzaBusinessException() {
        // Given
        Comentario c = new Comentario();
        c.setComentarioActivo(false); // YA ESTÁ INACTIVO
        when(repository.findById(1L)).thenReturn(Optional.of(c));

        // Then
        assertThatThrownBy(() -> service.desactivar(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El comentario ya se encuentra inactivo");
    }
}