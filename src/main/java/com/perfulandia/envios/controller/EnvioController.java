package com.perfulandia.envios.controller;

import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.service.EnvioService;
import org.springframework.hateoas.CollectionModel;
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
    public CollectionModel<EntityModel<Envio>> obtenerTodos() {
        List<Envio> lista = envioService.listarTodos();

        List<EntityModel<Envio>> recursos = lista.stream()
                .map(envio -> EntityModel.of(envio,
                        linkTo(methodOn(EnvioController.class).obtenerPorId(envio.getId())).withSelfRel()))
                .toList();

        return CollectionModel.of(recursos,
                linkTo(methodOn(EnvioController.class).obtenerTodos()).withSelfRel());
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
    public CollectionModel<EntityModel<Envio>> obtenerPorEstado(@PathVariable String estado) {
        List<Envio> lista = envioService.listarPorEstado(estado);

        List<EntityModel<Envio>> recursos = lista.stream()
                .map(envio -> EntityModel.of(envio,
                        linkTo(methodOn(EnvioController.class).obtenerPorId(envio.getId())).withSelfRel()))
                .toList();

        return CollectionModel.of(recursos,
                linkTo(methodOn(EnvioController.class).obtenerPorEstado(estado)).withSelfRel());
    }

    @GetMapping("/pedido/{pedidoId}")
    public CollectionModel<EntityModel<Envio>> obtenerPorPedido(@PathVariable Long pedidoId) {
        List<Envio> lista = envioService.listarPorPedidoId(pedidoId);

        List<EntityModel<Envio>> recursos = lista.stream()
                .map(envio -> EntityModel.of(envio,
                        linkTo(methodOn(EnvioController.class).obtenerPorId(envio.getId())).withSelfRel()))
                .toList();

        return CollectionModel.of(recursos,
                linkTo(methodOn(EnvioController.class).obtenerPorPedido(pedidoId)).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Envio>> crear(@RequestBody Envio envio) {
        Envio creado = envioService.guardar(envio);
        EntityModel<Envio> recurso = EntityModel.of(creado,
                linkTo(methodOn(EnvioController.class).obtenerPorId(creado.getId())).withSelfRel(),
                linkTo(methodOn(EnvioController.class).obtenerTodos()).withRel("todos-los-envios"));
        return ResponseEntity.created(linkTo(methodOn(EnvioController.class).obtenerPorId(creado.getId())).toUri()).body(recurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizar(id, envio);
        EntityModel<Envio> recurso = EntityModel.of(actualizado,
                linkTo(methodOn(EnvioController.class).obtenerPorId(actualizado.getId())).withSelfRel(),
                linkTo(methodOn(EnvioController.class).obtenerTodos()).withRel("todos-los-envios"));
        return ResponseEntity.ok(recurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = envioService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
