package cl.duoc.gamerbox.login.service;

import cl.duoc.gamerbox.login.dto.LoginDto;
import cl.duoc.gamerbox.login.exceptions.BusinessException;
import cl.duoc.gamerbox.login.model.Login;
import cl.duoc.gamerbox.login.repository.LoginRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock private LoginRepository repository;
    @Mock private WebClient.Builder webClientBuilder;
    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private LoginService service;

    @Test
    void Given_CredencialesValidas_When_AutenticarUsuario_Then_RetornaLoginConToken() {
        // Given
        LoginDto dto = new LoginDto();
        dto.setCorreoUsuario("test@gamerbox.com");
        dto.setPasswordUsuario("12345");

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        // Simulamos que ms-usuarios responde correctamente
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));

        // Simula el guardado en base de datos retornando el mismo objeto modificado
        when(repository.save(any(Login.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Login resultado = service.autenticarUsuario(dto);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdUsuario()).isEqualTo(1L); // Verifica que asignó el ID simulado
        assertThat(resultado.getTokenJti()).isNotBlank();   // Verifica que se generó un UUID válido

        verify(repository).save(any(Login.class)); // Verifica que llama el repositorio
    }

    @Test
    void Given_ServidorUsuariosCaido_When_AutenticarUsuario_Then_LanzaBusinessException() {
        // Given
        LoginDto dto = new LoginDto();

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        // Simulamos un error de red o que el microservicio está caído
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(new RuntimeException("Connection refused")));

        // When & Then
        assertThatThrownBy(() -> service.autenticarUsuario(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("No se pudo procesar la verificación de identidad");

        // Verifica que no se guarda un token
        verify(repository, never()).save(any(Login.class));
    }
}