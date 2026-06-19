package cl.duoc.gamerbox.login.controller;

import cl.duoc.gamerbox.login.dto.LoginDto;
import cl.duoc.gamerbox.login.model.Login;
import cl.duoc.gamerbox.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoint central para el ingreso de usuarios y la validación de credenciales")
public class LoginController {
    private final LoginService service;
    public LoginController(LoginService service) { this.service = service; }

    @Operation(summary = "Iniciar sesión en Gamerbox", description = "Valida el correo y la contraseña del usuario. Si las credenciales son correctas, registra el login en la base de datos y retorna el evento junto con el identificador del token.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autenticación exitosa y sesión registrada"),
            @ApiResponse(responseCode = "400", description = "Formato de correo inválido o campos faltantes"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<Login> login(@Parameter(description = "Credenciales de acceso del usuario")
                                           @Valid @RequestBody LoginDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.autenticarUsuario(dto)); }
}