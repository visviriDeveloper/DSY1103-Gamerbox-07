package com.melisoft.ms_resenias.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// El nombre debe coincidir con el spring.application.name de ms-juegos
@FeignClient(name = "ms-juegos", url = "http://localhost:8081/api/juegos")
public interface JuegoClient {

    @GetMapping("/{id}")
    Object obtenerJuegoPorId(@PathVariable("id") Long id);
}