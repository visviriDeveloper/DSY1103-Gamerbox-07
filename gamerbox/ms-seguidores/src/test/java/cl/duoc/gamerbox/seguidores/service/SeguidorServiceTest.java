package cl.duoc.gamerbox.seguidores.service;

import cl.duoc.gamerbox.seguidores.seguidoresDTO.SeguidorDTO;
import cl.duoc.gamerbox.seguidores.exceptions.BusinessException;
import cl.duoc.gamerbox.seguidores.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.seguidores.model.Seguidor;
import cl.duoc.gamerbox.seguidores.repository.SeguidorRepository;
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
class SeguidorServiceTest {

    @Mock private SeguidorRepository repository;
    @Mock private WebClient.Builder webClientBuilder;
    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private SeguidorService service;

    // Tests Listar

    @Test
    void Given_IdSeguidor_When_ListarSiguiendo_Then_RetornaLista() {
        when(repository.findByIdSeguidorAndActivoTrue(1L)).thenReturn(List.of(new Seguidor()));
        assertThat(service.listarSiguiendo(1L)).hasSize(1);
    }

    // ── Tests Seguir (y método privado validarUsuarioEcosistema) ───

    @Test
    void Given_SeguidorYSeguidoIguales_When_Seguir_Then_LanzaBusinessException() {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(1L);
        dto.setIdSeguido(1L);

        assertThatThrownBy(() -> service.seguir(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("no puede seguirse a sí mismo");
    }

    @Test
    void Given_SeguidorNoExiste_When_Seguir_Then_LanzaBusinessException() {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(1L);
        dto.setIdSeguido(2L);

        configurarMockWebClient();

        // Falla en la primera validación (Seguidor) con 404
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null)));

        assertThatThrownBy(() -> service.seguir(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El Seguidor con ID 1 no existe");
    }

    @Test
    void Given_SeguidoNoExiste_When_Seguir_Then_LanzaBusinessException() {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(1L);
        dto.setIdSeguido(2L);

        configurarMockWebClient();

        // La primera validación (Seguidor) pasa, la segunda (Seguido) falla con 404
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object()))
                .thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null)));

        assertThatThrownBy(() -> service.seguir(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El Seguido con ID 2 no existe");
    }

    @Test
    void Given_ErrorGenericoAlValidar_When_Seguir_Then_LanzaBusinessException() {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(1L);
        dto.setIdSeguido(2L);

        configurarMockWebClient();

        // Falla por caída de red
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(new RuntimeException("API caida")));

        assertThatThrownBy(() -> service.seguir(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Error al conectar con ms-usuarios");
    }

    @Test
    void Given_YaSiguiendo_When_Seguir_Then_LanzaBusinessException() {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(1L);
        dto.setIdSeguido(2L);

        configurarMockWebClient();

        // Ambas validaciones pasan
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object()))
                .thenReturn(Mono.just(new Object()));

        // El repositorio dice que YA lo sigue
        when(repository.findByIdSeguidorAndIdSeguidoAndActivoTrue(1L, 2L)).thenReturn(Optional.of(new Seguidor()));

        assertThatThrownBy(() -> service.seguir(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Ya sigues a este usuario");
    }

    @Test
    void Given_DatosValidos_When_Seguir_Then_GuardaSeguidor() {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(1L);
        dto.setIdSeguido(2L);

        configurarMockWebClient();

        // Ambas validaciones de WebClient exitosas
        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object()))
                .thenReturn(Mono.just(new Object()));

        // El repo dice que NO lo sigue aún
        when(repository.findByIdSeguidorAndIdSeguidoAndActivoTrue(1L, 2L)).thenReturn(Optional.empty());
        when(repository.save(any(Seguidor.class))).thenAnswer(i -> i.getArguments()[0]);

        Seguidor resultado = service.seguir(dto);

        assertThat(resultado.getIdSeguidor()).isEqualTo(1L);
        assertThat(resultado.getIdSeguido()).isEqualTo(2L);
        verify(repository).save(any(Seguidor.class));
    }

    // Tests Dejar de Seguir

    @Test
    void Given_IdInexistente_When_DejarDeSeguir_Then_LanzaResourceNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.dejarDeSeguir(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Registro de seguimiento no encontrado");
    }

    @Test
    void Given_YaInactivo_When_DejarDeSeguir_Then_LanzaBusinessException() {
        Seguidor s = new Seguidor();
        s.setActivo(false); // Ya no lo sigue
        when(repository.findById(1L)).thenReturn(Optional.of(s));

        assertThatThrownBy(() -> service.dejarDeSeguir(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Ya no sigues a este usuario");
    }

    @Test
    void Given_SeguidorActivo_When_DejarDeSeguir_Then_CambiaAInactivo() {
        Seguidor s = new Seguidor();
        s.setActivo(true);
        when(repository.findById(1L)).thenReturn(Optional.of(s));
        when(repository.save(any(Seguidor.class))).thenAnswer(i -> i.getArguments()[0]);

        Seguidor resultado = service.dejarDeSeguir(1L);

        assertThat(resultado.getActivo()).isFalse();
        verify(repository).save(s);
    }

    // Método Auxiliar

    private void configurarMockWebClient() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    }
}