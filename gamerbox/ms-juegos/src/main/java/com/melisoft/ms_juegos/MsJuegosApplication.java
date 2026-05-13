package com.melisoft.ms_juegos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ms_juegos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuego; //

    @Column(nullable = false)
    private String nombreJuego; //

    @Enumerated(EnumType.STRING)
    private MsJuegosApplication plataforma; //

    @Column(name = "fecha_lanzamiento")
    private LocalDateTime fechaLanzamiento; //

    private Boolean estado; //
}

public enum MsJuegosApplication {
    PC, PS5, XBOX, NINTENDO_SWITCH //
}