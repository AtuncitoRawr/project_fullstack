package com.perfulandia.envios.controller;

import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.service.EnvioService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public List<Envio> obtenerTodos() {
        return envioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> obtenerPorId(@PathVariable Long id) {
        return envioService.buscarPorId(id)
                .map(envio -> {
                    EntityModel<Envio> recurso = EntityModel.of(envio);
                    recurso.add(linkTo(methodOn(EnvioController.class).obtenerPorId(id)).withSelfRel());
                    recurso.add(linkTo(methodOn(EnvioController.class).obtenerTodos()).withRel("todos-los-envios"));
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<Envio> obtenerPorEstado(@PathVariable String estado) {
        return envioService.listarPorEstado(estado);
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<Envio> obtenerPorPedido(@PathVariable Long pedidoId) {
        return envioService.listarPorPedidoId(pedidoId);
    }

    @PostMapping
    public Envio crear(@RequestBody Envio envio) {
        return envioService.guardar(envio);
    }

    @PutMapping("/{id}")
    public Envio actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        return envioService.actualizar(id, envio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}