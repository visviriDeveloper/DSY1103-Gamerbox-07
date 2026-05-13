package com.melisoft.ms_usuarios.controller;

import com.melisoft.ms_usuarios.model.Usuario;
import com.melisoft.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    // METODOS GET

    //Retorno de la lista completa de usuarios.
    @GetMapping
    public ResponseEntity<?> listarUsuario() {
        try{
            List<Usuario> usuarios = usuarioService.listarTodos();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar a todos los usuarios");
        }
    }

    // Retorno del usuario buscado por su id.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id){
        try{
            Usuario usuarioBuscado = usuarioService.buscarPorId(id)
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
            return ResponseEntity.ok(usuarioBuscado);
        } catch (NoSuchElementException e ) {
            // Capturamos específicamente el 404 antes de la excepcion generica
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el usuario por ID: " + id);
          }
        }


    //METODO Post
    // Agregar un nuevo usuario

    @PostMapping
    public ResponseEntity<?> agregarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioNuevo = usuarioService.guardar(usuario);
            // Respuesta 201
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar al usuario");

        }
    }
        // METODO PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id,@Valid @RequestBody Usuario usuarioActualizado) {
        try{
           Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado)
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado por la id: " + id));
            return ResponseEntity.ok(usuario);
        }catch (NoSuchElementException e){
            // Capturo el codigo 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Usuario no encontrado por la id: " + id);
        }catch(Exception e){
            //Envio codigo 500 (generico)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario ");
        }
        }
        // METODO DELETE
        // Eliminar usuario por id
        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
            try{
                if(usuarioService.eliminarUsuario(id)){
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar usuario: " + id);
            }
        }


}
