package cl.duoc.gamerbox.resenas.service;

import cl.duoc.gamerbox.resenas.dto.ResenaDTO;
import cl.duoc.gamerbox.resenas.exceptions.BusinessException;
import cl.duoc.gamerbox.resenas.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.resenas.model.Resena;
import cl.duoc.gamerbox.resenas.repository.ResenaRepository;
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
class ResenaServiceTest {

    @Mock private ResenaRepository repository;
    @Mock private WebClient.Builder webClientBuilder;
    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ResenaService service;

    // ── Tests Consultas Básicas ──────────────────────────────────────────

    @Test
    void Given_ResenasExistentes_When_Listar_Then_RetornaLista() {
        when(repository.findAll()).thenReturn(List.of(new Resena()));
        assertThat(service.listar()).hasSize(1);
    }

    @Test
    void Given_IdUsuario_When_PorUsuario_Then_RetornaLista() {
        when(repository.findByIdUsuario(1L)).thenReturn(List.of(new Resena()));
        assertThat(service.porUsuario(1L)).hasSize(1);
    }

    @Test
    void Given_IdJuego_When_PorJuego_Then_RetornaLista() {
        when(repository.findByIdJuegoAndEstadoTrue(1L)).thenReturn(List.of(new Resena()));
        assertThat(service.porJuego(1L)).hasSize(1);
    }

    // ── Tests Obtener ───────────────────────────────────────────────────

    @Test
    void Given_IdExistente_When_Obtener_Then_RetornaResena() {
        Resena r = new Resena();
        when(repository.findById(1L)).thenReturn(Optional.of(r));
        assertThat(service.obtener(1L)).isEqualTo(r);
    }

    @Test
    void Given_IdInexistente_When_Obtener_Then_LanzaResourceNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    // ── Tests Crear (Validaciones Dobles WebClient) ─────────────────────

    @Test
    void Given_DatosValidos_When_Crear_Then_RetornaResena() {
        ResenaDTO dto = new ResenaDTO();
        dto.setIdJuego(1L);
        dto.setIdUsuario(2L);
        dto.setTitulo("Excelente");

        configurarMockWebClient();

        // Simular ambas llamadas exitosas (primero juegos, luego usuarios)
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object())) // Respuesta ms-juegos
                .thenReturn(Mono.just(new Object())); // Respuesta ms-usuarios

        when(repository.save(any(Resena.class))).thenAnswer(i -> i.getArguments()[0]);

        Resena resultado = service.crear(dto);

        assertThat(resultado.getTitulo()).isEqualTo("Excelente");
    }

    @Test
    void Given_JuegoNoExiste_When_Crear_Then_LanzaBusinessException() {
        ResenaDTO dto = new ResenaDTO();
        dto.setIdJuego(99L);

        configurarMockWebClient();

        // Falla en la primera llamada (ms-juegos) con 404
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null)));

        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El juego con ID");
    }

    @Test
    void Given_ErrorGenericoJuego_When_Crear_Then_LanzaBusinessException() {
        ResenaDTO dto = new ResenaDTO();
        configurarMockWebClient();
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(new RuntimeException("Error Red")));

        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("comunicar con el catálogo de juegos");
    }

    @Test
    void Given_UsuarioNoExiste_When_Crear_Then_LanzaBusinessException() {
        ResenaDTO dto = new ResenaDTO();
        configurarMockWebClient();

        // La primera llamada (juegos) pasa, la segunda (usuarios) falla con 404
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object())) // ms-juegos OK
                .thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null))); // ms-usuarios 404

        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El usuario con ID");
    }

    @Test
    void Given_ErrorGenericoUsuario_When_Crear_Then_LanzaBusinessException() {
        ResenaDTO dto = new ResenaDTO();
        configurarMockWebClient();

        // La primera llamada pasa, la segunda falla genéricamente
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object()))
                .thenReturn(Mono.error(new RuntimeException("Error Red")));

        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("comunicar con la base de usuarios");
    }

    // ── Tests Actualizar ────────────────────────────────────────────────

    @Test
    void Given_ResenaActiva_When_Actualizar_Then_GuardaCambios() {
        Resena r = new Resena();
        r.setEstado(true);
        ResenaDTO dto = new ResenaDTO();
        dto.setTitulo("Nuevo Título");

        when(repository.findById(1L)).thenReturn(Optional.of(r));
        when(repository.save(any(Resena.class))).thenAnswer(i -> i.getArguments()[0]);

        Resena actualizada = service.actualizar(1L, dto);

        assertThat(actualizada.getTitulo()).isEqualTo("Nuevo Título");
    }

    @Test
    void Given_ResenaInactiva_When_Actualizar_Then_LanzaBusinessException() {
        Resena r = new Resena();
        r.setEstado(false); // Inactiva

        when(repository.findById(1L)).thenReturn(Optional.of(r));

        assertThatThrownBy(() -> service.actualizar(1L, new ResenaDTO()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("No se puede editar una reseña desactivada");
    }

    // ── Tests Desactivar ────────────────────────────────────────────────

    @Test
    void Given_ResenaActiva_When_Desactivar_Then_CambiaEstadoAFalso() {
        Resena r = new Resena();
        r.setEstado(true);
        when(repository.findById(1L)).thenReturn(Optional.of(r));
        when(repository.save(any(Resena.class))).thenAnswer(i -> i.getArguments()[0]);

        service.desactivar(1L);

        assertThat(r.getEstado()).isFalse();
    }

    @Test
    void Given_ResenaYaInactiva_When_Desactivar_Then_LanzaBusinessException() {
        Resena r = new Resena();
        r.setEstado(false);
        when(repository.findById(1L)).thenReturn(Optional.of(r));

        assertThatThrownBy(() -> service.desactivar(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("ya se encuentra desactivada");
    }

    // ── Método Auxiliar ─────────────────────────────────────────────────

    private void configurarMockWebClient() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    }
}