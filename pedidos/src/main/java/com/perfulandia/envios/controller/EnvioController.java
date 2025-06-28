package com.perfulandia.envios.controller;

import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/envios")
@Tag(name = "Envios", description = "Operaciones relacionadas con envíos")
public class EnvioController {

    private final EnvioService envioService;
    private final EnvioModelAssembler assembler;

    public EnvioController(EnvioService envioService, EnvioModelAssembler assembler) {
        this.envioService = envioService;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos los envíos")
    public CollectionModel<EntityModel<Envio>> getAllEnvios() {
        List<EntityModel<Envio>> envios = envioService.getAllEnvios()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(envios,
                linkTo(methodOn(EnvioController.class).getAllEnvios()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un envío por ID")
    public EntityModel<Envio> getEnvioById(@PathVariable Long id) {
        Envio envio = envioService.getEnvioById(id);
        return assembler.toModel(envio);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo envío")
    public ResponseEntity<EntityModel<Envio>> createEnvio(@RequestBody Envio envio) {
        Envio savedEnvio = envioService.saveEnvio(envio);
        EntityModel<Envio> envioModel = assembler.toModel(savedEnvio);

        return ResponseEntity
                .created(envioModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(envioModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un envío existente")
    public ResponseEntity<EntityModel<Envio>> updateEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        Envio updated = envioService.updateEnvio(id, envio);
        EntityModel<Envio> envioModel = assembler.toModel(updated);
        return ResponseEntity.ok(envioModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un envío por ID")
    public ResponseEntity<?> deleteEnvio(@PathVariable Long id) {
        envioService.deleteEnvio(id);
        return ResponseEntity.noContent().build();
    }
}