package cl.duoc.gamerbox.juegos.service;

import cl.duoc.gamerbox.juegos.dto.JuegoDto;
import cl.duoc.gamerbox.juegos.exceptions.BusinessException;
import cl.duoc.gamerbox.juegos.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.juegos.model.Juego;
import cl.duoc.gamerbox.juegos.repository.JuegoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JuegoServiceTest {

    @Mock
    private JuegoRepository repository;

    @InjectMocks
    private JuegoService service;

    @Test
    void Given_JuegosActivos_When_ListarActivos_Then_RetornaLista() {
        when(repository.findByEstadoTrue()).thenReturn(List.of(new Juego()));
        assertThat(service.listarActivos()).hasSize(1);
    }

    @Test
    void Given_IdExistente_When_Obtener_Then_RetornaJuego() {
        Juego j = new Juego();
        when(repository.findById(1L)).thenReturn(Optional.of(j));
        assertThat(service.obtener(1L)).isEqualTo(j);
    }

    @Test
    void Given_IdInexistente_When_Obtener_Then_LanzaResourceNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void Given_Nombre_When_Buscar_Then_RetornaLista() {
        when(repository.findByNombreJuegoContainingIgnoreCaseAndEstadoTrue("Elden")).thenReturn(List.of(new Juego()));
        assertThat(service.buscarPorNombre("Elden")).hasSize(1);
    }

    @Test
    void Given_Dto_When_Crear_Then_RetornaJuegoGuardado() {
        JuegoDto dto = new JuegoDto();
        dto.setNombreJuego("Elden Ring");
        when(repository.save(any(Juego.class))).thenAnswer(i -> i.getArguments()[0]);

        Juego j = service.crear(dto);
        assertThat(j.getNombreJuego()).isEqualTo("Elden Ring");
    }

    @Test
    void Given_JuegoActivo_When_Desactivar_Then_GuardaComoInactivo() {
        Juego j = new Juego();
        j.setEstado(true);
        when(repository.findById(1L)).thenReturn(Optional.of(j));
        when(repository.save(any(Juego.class))).thenAnswer(i -> i.getArguments()[0]);

        service.desactivar(1L);

        assertThat(j.getEstado()).isFalse();
        verify(repository).save(j);
    }

    @Test
    void Given_JuegoYaInactivo_When_Desactivar_Then_LanzaBusinessException() {
        Juego j = new Juego();
        j.setEstado(false);
        when(repository.findById(1L)).thenReturn(Optional.of(j));

        assertThatThrownBy(() -> service.desactivar(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("ya está desactivado");
    }
}