package com.usuarios.usuarios_vm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.service.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuariosControllerTest {

    // Utilidad de Spring para simular peticiones HTTP
    private MockMvc mockMvc;

    // Servicio que será mockeado
    private UsuariosService usuariosService;

    // Para convertir objetos Java a JSON (y viceversa)
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        // Crear un mock del servicio
        usuariosService = mock(UsuariosService.class);

        // Instancia real del controlador (sin usar Spring Boot directamente)
        UsuariosController controller = new UsuariosController();

        // Inyectamos el servicio mock manualmente usando reflexión
        Field field = UsuariosController.class.getDeclaredField("usuariosService");
        field.setAccessible(true);
        field.set(controller, usuariosService);

        // Configuramos MockMvc para probar solo este controlador
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Inicializamos el ObjectMapper para enviar y recibir JSON
        objectMapper = new ObjectMapper();
    }

    //Método auxiliar para crear un objeto Usuarios de ejemplo

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
        // Simulamos que el servicio devuelve una lista con un usuario
        Usuarios usuario = crearUsuarioEjemplo();
        when(usuariosService.findAll()).thenReturn(List.of(usuario));

        // Ejecutamos un GET a /usuarios y verificamos respuesta 200 + contenido
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre_usuario").value("Juan"));
    }

    @Test
    void testBuscarPorId_Existe() throws Exception {
        // Simulamos que se encuentra un usuario con ID 1
        Usuarios usuario = crearUsuarioEjemplo();
        when(usuariosService.findById(1)).thenReturn(usuario);

        // Hacemos GET a /usuarios/1 y esperamos status 200 + datos correctos
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo_usuario").value("juan@example.com"));
    }

    @Test
    void testBuscarPorId_NoExiste() throws Exception {
        // Simulamos que no se encuentra el usuario y se lanza una excepción
        when(usuariosService.findById(999)).thenThrow(new NoSuchElementException());

        // Esperamos una respuesta 404 Not Found
        mockMvc.perform(get("/api/v1/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarUsuario() throws Exception {
        // Simulamos que se guarda correctamente y se retorna el usuario
        Usuarios usuario = crearUsuarioEjemplo();
        when(usuariosService.save(any(Usuarios.class))).thenReturn(usuario);

        // POST a /usuarios con JSON y esperamos 201 Created
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre_usuario").value("Juan"));
    }

    @Test
    void testActualizarUsuario() throws Exception {
        // Simulamos usuario actualizado con nombre cambiado
        Usuarios usuarioActualizado = crearUsuarioEjemplo();
        usuarioActualizado.setNombre_usuario("Carlos");

        // Simulamos la búsqueda previa y luego el guardado
        when(usuariosService.findById(1)).thenReturn(crearUsuarioEjemplo());
        when(usuariosService.save(any(Usuarios.class))).thenReturn(usuarioActualizado);

        // PUT a /usuarios/1 y verificamos que el nombre ha cambiado
        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre_usuario").value("Carlos"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        // Simulamos eliminación sin error
        doNothing().when(usuariosService).delete(1);

        // DELETE a /usuarios/1 y esperamos 204 No Content
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
