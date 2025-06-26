package com.usuarios.usuarios_vm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuarios.usuarios_vm.assemblers.UsuariosAssembler;
import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.service.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuariosControllerTest {

    private MockMvc mockMvc;
    private UsuariosService usuariosService;
    private UsuariosAssembler assembler;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        usuariosService = mock(UsuariosService.class);
        assembler = mock(UsuariosAssembler.class);

        UsuariosController controller = new UsuariosController();

        Field serviceField = UsuariosController.class.getDeclaredField("usuariosService");
        serviceField.setAccessible(true);
        serviceField.set(controller, usuariosService);

        Field assemblerField = UsuariosController.class.getDeclaredField("assembler");
        assemblerField.setAccessible(true);
        assemblerField.set(controller, assembler);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();

        // Mockear assembler.toModel para que devuelva un EntityModel válido
        when(assembler.toModel(any(Usuarios.class))).thenAnswer(invocation -> {
            Usuarios u = invocation.getArgument(0);
            return EntityModel.of(u);
        });
    }

    private Usuarios crearUsuarioEjemplo() {
        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(1);
        usuario.setNombre_usuario("Juan");
        usuario.setApellido_usuario("Pérez");
        usuario.setCorreo_usuario("juan@example.com");
        usuario.setTelefono_usuario("123456789");
        usuario.setDireccion_usuario("Calle Falsa 123");
        usuario.setContraseña_usuario("secreta");
        return usuario;
    }

    @Test
    void testListarUsuarios() throws Exception {
        Usuarios usuario = crearUsuarioEjemplo();
        when(usuariosService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nombre_usuario").value("Juan"));
    }

    @Test
    void testBuscarPorId_Existe() throws Exception {
        Usuarios usuario = crearUsuarioEjemplo();
        when(usuariosService.findById(1)).thenReturn(usuario);
        when(assembler.toModel(any(Usuarios.class))).thenAnswer(invocation -> {
            Usuarios u = invocation.getArgument(0);
            return EntityModel.of(u);
        });

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre_usuario").value("Juan"))
                .andExpect(jsonPath("$.correo_usuario").value("juan@example.com"));
    }

    @Test
    void testBuscarPorId_NoExiste() throws Exception {
        when(usuariosService.findById(999)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/v1/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarUsuario() throws Exception {
        Usuarios usuario = crearUsuarioEjemplo();
        when(usuariosService.save(any(Usuarios.class))).thenReturn(usuario);
        when(assembler.toModel(any(Usuarios.class))).thenAnswer(invocation -> {
            Usuarios u = invocation.getArgument(0);
            return EntityModel.of(u);
        });

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre_usuario").value("Juan"));
    }

    @Test
    void testActualizarUsuario() throws Exception {
        Usuarios usuarioActualizado = crearUsuarioEjemplo();
        usuarioActualizado.setNombre_usuario("Carlos");

        when(usuariosService.findById(1)).thenReturn(crearUsuarioEjemplo());
        when(usuariosService.save(any(Usuarios.class))).thenReturn(usuarioActualizado);
        when(assembler.toModel(any(Usuarios.class))).thenAnswer(invocation -> {
            Usuarios u = invocation.getArgument(0);
            return EntityModel.of(u);
        });

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre_usuario").value("Carlos"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuariosService).delete(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
