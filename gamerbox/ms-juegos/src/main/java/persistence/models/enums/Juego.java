 package persistence.models.enums;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.gamerbox.juegos.models.enums.Plataforma;
import com.melisoft.ms_juegos.AllArgsConstructor;
import com.melisoft.ms_juegos.NoArgsConstructor;

import persistence.Plataforma;

@Entity
@Table(name = "ms_juegos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuego; 

    @Column(nullable = false)
    private String nombreJuego; 

    @Enumerated(EnumType.STRING)
    private Plataforma plataforma;

    private LocalDateTime fechaLanzamiento;

    private Boolean estado; 
}