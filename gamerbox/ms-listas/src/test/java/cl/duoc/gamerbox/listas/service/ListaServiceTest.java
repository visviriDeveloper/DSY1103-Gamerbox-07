package cl.duoc.gamerbox.listas.service;

import cl.duoc.gamerbox.listas.dto.ListaDto;
import cl.duoc.gamerbox.listas.exceptions.BusinessException;
import cl.duoc.gamerbox.listas.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.listas.model.Lista;
import cl.duoc.gamerbox.listas.repository.ListaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListaServiceTest {

    @Mock private ListaRepository repository;
    @Mock private WebClient.Builder webClientBuilder;
    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ListaService service;

    // ── Tests Obtener ──────────────────────────────────────────

    @Test
    void Given_IdExistente_When_Obtener_Then_RetornaLista() {
        Lista l = new Lista();
        when(repository.findById(1L)).thenReturn(Optional.of(l));
        assertThat(service.obtener(1L)).isEqualTo(l);
    }

    @Test
    void Given_IdInexistente_When_Obtener_Then_LanzaResourceNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    // ── Tests Crear ─────────────────────────────────────────────

    @Test
    void Given_DatosValidos_When_Crear_Then_RetornaLista() {
        ListaDto dto = new ListaDto();
        dto.setIdUsuario(1L);
        dto.setNombreLista("Mis juegos");

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));
        when(repository.save(any(Lista.class))).thenAnswer(i -> i.getArguments()[0]);

        Lista resultado = service.crear(dto);

        assertThat(resultado.getNombreLista()).isEqualTo("Mis juegos");
    }

    @Test
    void Given_UsuarioNoExiste_When_Crear_Then_LanzaBusinessException() {
        ListaDto dto = new ListaDto();
        dto.setIdUsuario(99L);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        // Simular error 404
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(WebClientResponseException.create(404, "Not Found", null, null, null)));

        assertThatThrownBy(() -> service.crear(dto)).isInstanceOf(BusinessException.class);
    }

    @Test
    void Given_ErrorGenerico_When_Crear_Then_LanzaBusinessException() {
        ListaDto dto = new ListaDto();
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        // Simular error genérico
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(new RuntimeException("Error")));

        assertThatThrownBy(() -> service.crear(dto)).isInstanceOf(BusinessException.class);
    }

    // ── Tests Desactivar ────────────────────────────────────────

    @Test
    void Given_ListaActiva_When_Desactivar_Then_GuardaInactiva() {
        Lista l = new Lista();
        l.setListaActiva(true);
        when(repository.findById(1L)).thenReturn(Optional.of(l));
        when(repository.save(any(Lista.class))).thenAnswer(i -> i.getArguments()[0]);

        service.desactivar(1L);

        assertThat(l.getListaActiva()).isFalse();
    }

    @Test
    void Given_ListaYaInactiva_When_Desactivar_Then_LanzaBusinessException() {
        Lista l = new Lista();
        l.setListaActiva(false);
        when(repository.findById(1L)).thenReturn(Optional.of(l));

        assertThatThrownBy(() -> service.desactivar(1L)).isInstanceOf(BusinessException.class);
    }
}