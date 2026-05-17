package cl.duoc.gamerbox.login.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

        @Entity
        @Table(name = "logins")
        public class Login {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long idLogin;

            @Column(name = "id_usuario", nullable = false)
            private Long idUsuario;

            @Column(name = "token_jti", nullable = false, length = 255)
            private String tokenJti;

            @Column(name = "fecha_login", nullable = false)
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
