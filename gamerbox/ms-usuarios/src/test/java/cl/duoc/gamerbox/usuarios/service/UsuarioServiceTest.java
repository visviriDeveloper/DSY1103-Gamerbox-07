package cl.duoc.gamerbox.usuarios.service;

import cl.duoc.gamerbox.usuarios.exceptions.BusinessException;
import cl.duoc.gamerbox.usuarios.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.usuarios.model.Usuario;
import cl.duoc.gamerbox.usuarios.repository.UsuarioRepository;
import cl.duoc.gamerbox.usuarios.usuarioDTO.UsuarioDTO;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService service;

    // Tests Listar Todos

    @Test
    void Given_UsuariosExistentes_When_ListarTodos_Then_RetornaListaDTO() {
        Usuario u = new Usuario();
        u.setIdUsuario(1L);
        u.setNombreUsuario("Test");

        when(usuarioRepository.findAll()).thenReturn(List.of(u));

        List<UsuarioDTO> resultado = service.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).idUsuario()).isEqualTo(1L);
    }

    //Tests Buscar por ID

    @Test
    void Given_IdExistente_When_BuscarPorId_Then_RetornaUsuarioDTO() {
        Usuario u = new Usuario();
        u.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));

        UsuarioDTO resultado = service.buscarPorId(1L);

        assertThat(resultado.idUsuario()).isEqualTo(1L);
    }

    @Test
    void Given_IdInexistente_When_BuscarPorId_Then_LanzaResourceNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("no encontrado");
    }

    // Tests Crear

    @Test
    void Given_CorreoExistente_When_Crear_Then_LanzaBusinessException() {
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@mail.com", "123", null, null);
        when(usuarioRepository.existsByCorreoUsuario("juan@mail.com")).thenReturn(true);

        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("El correo electrónico ya está registrado");
    }

    @Test
    void Given_DatosValidos_When_Crear_Then_GuardaYRetornaDTO() {
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@mail.com", "123", null, null);
        when(usuarioRepository.existsByCorreoUsuario("juan@mail.com")).thenReturn(false);

        // Configuramos el mock para que al guardar asigne un ID simulando la BD
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            u.setIdUsuario(10L);
            return u;
        });

        UsuarioDTO resultado = service.crear(dto);

        assertThat(resultado.idUsuario()).isEqualTo(10L);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    // Tests Actualizar

    @Test
    void Given_IdInexistente_When_Actualizar_Then_LanzaResourceNotFound() {
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@mail.com", "123", null, null);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.actualizar(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void Given_DatosValidosConPassword_When_Actualizar_Then_ActualizaPassword() {
        Usuario u = new Usuario();
        u.setPasswordUsuario("vieja_password");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@mail.com", "nueva_password", null, null);

        service.actualizar(1L, dto);

        // Verificamos internamente que la entidad cambió la contraseña antes de guardarse
        assertThat(u.getPasswordUsuario()).isEqualTo("nueva_password");
        verify(usuarioRepository).save(u);
    }

    @Test
    void Given_DatosValidosSinPassword_When_Actualizar_Then_MantienePasswordAnterior() {
        Usuario u = new Usuario();
        u.setPasswordUsuario("vieja_password");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        // Pasamos un password null (o en blanco "")
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@mail.com", null, null, null);

        service.actualizar(1L, dto);

        // Verificamos que la contraseña se mantuvo intacta
        assertThat(u.getPasswordUsuario()).isEqualTo("vieja_password");
        verify(usuarioRepository).save(u);
    }

    // Tests Eliminar
    @Test
    void Given_IdInexistente_When_Eliminar_Then_LanzaResourceNotFound() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.eliminar(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void Given_IdExistente_When_Eliminar_Then_LlamaDeleteById() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(usuarioRepository).deleteById(1L);
    }
}