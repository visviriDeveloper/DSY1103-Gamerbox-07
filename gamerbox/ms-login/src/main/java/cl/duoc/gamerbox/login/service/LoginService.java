package cl.duoc.gamerbox.login.service;

import cl.duoc.gamerbox.login.dto.LoginDto;
import cl.duoc.gamerbox.login.model.Login;
import cl.duoc.gamerbox.login.repository.LoginRepository;
import cl.duoc.gamerbox.login.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.UUID;

@Service
public class LoginService {
    private final LoginRepository repository;
    private final WebClient.Builder webClientBuilder;

    public LoginService(LoginRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public Login autenticarUsuario(LoginDto dto) {
        // En un entorno real se buscaría el usuario por email en ms-usuarios para validar la pass
        try {
            // Golpeamos el endpoint genérico para asegurar comunicación inter-servicio
            webClientBuilder.build().get().uri("http://ms-usuarios-app:8081/api/v1/usuarios")
                    .retrieve().bodyToMono(Object.class).block();
        } catch (Exception e) {
            throw new BusinessException("No se pudo procesar la verificación de identidad en el servidor.");
        }

        Login l = new Login();
        l.setIdUsuario(1L); // Simulación de vinculación exitosa con la cuenta existente
        l.setTokenJti(UUID.randomUUID().toString()); // Simulamos el identificador del token por ahora
        return repository.save(l);
    }
}