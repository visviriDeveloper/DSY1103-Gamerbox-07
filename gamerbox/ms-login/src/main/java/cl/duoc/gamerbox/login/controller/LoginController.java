package cl.duoc.gamerbox.login.controller;

import cl.duoc.gamerbox.login.dto.LoginDto;
import cl.duoc.gamerbox.login.model.Login;
import cl.duoc.gamerbox.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final LoginService service;
    public LoginController(LoginService service) { this.service = service; }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@Valid @RequestBody LoginDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.autenticarUsuario(dto)); }
}