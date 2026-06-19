package cl.duoc.gamerbox.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

        @Entity
        @Table(name = "logins")
        @Schema(description = "Entidad que registra el historial de inicios de sesión y el token emitido para un usuario")
        public class Login {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Schema(description = "Identificador único del registro de sesión", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
            private Long idLogin;

            @Column(name = "id_usuario", nullable = false)
            @Schema(description = "ID del usuario que inició sesión", example = "42")
            private Long idUsuario;

            @Column(name = "token_jti", nullable = false, length = 255)
            @Schema(description = "Identificador único del token (JTI) asignado en esta sesión", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            private String tokenJti;

            @Column(name = "fecha_login", nullable = false)
            @Schema(description = "Fecha y hora exacta en la que se realizó la autenticación", accessMode = Schema.AccessMode.READ_ONLY)
            private LocalDateTime fechaLogin;

            public Login() {}

            @PrePersist
            public void onPrePersist() {
                if (fechaLogin == null) fechaLogin = LocalDateTime.now();
            }

            public Long getIdLogin() { return idLogin; } public void setIdLogin(Long id) { this.idLogin = id; }
            public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long id) { this.idUsuario = id; }
            public String getTokenJti() { return tokenJti; } public void setTokenJti(String t) { this.tokenJti = t; }
            public LocalDateTime getFechaLogin() { return fechaLogin; } public void setFechaLogin(LocalDateTime f) { this.fechaLogin = f; }
        }
