package com.usuarios.usuarios_vm.service;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuariosServiceTest {

    // Crea un mock del repositorio (simula la capa de acceso a datos)
    @Mock
    private UsuariosRepository usuariosRepository;

    // Inyecta el mock en la instancia real del servicio a probar
    @InjectMocks
    private UsuariosService usuariosService;

    // Objeto de prueba reutilizable
    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        // Inicializa las anotaciones de Mockito
        MockitoAnnotations.openMocks(this);

        // Crea un usuario de ejemplo para reutilizar en los tests
        usuario = new Usuarios();
        usuario.setId_usuario(1);
        usuario.setNombre_usuario("Juan");
        usuario.setApellido_usuario("Pérez");
        usuario.setCorreo_usuario("juan@example.com");
        usuario.setTelefono_usuario("123456789");
        usuario.setDireccion_usuario("Calle Falsa 123");
        usuario.setContraseña_usuario("secreta");
    }

    @Test
    void testFindAll() {
        // Simula que el repositorio devuelve una lista con el usuario de ejemplo
        List<Usuarios> lista = Arrays.asList(usuario);
        when(usuariosRepository.findAll()).thenReturn(lista);

        // Llama al método real del servicio
        List<Usuarios> resultado = usuariosService.findAll();

        // Verifica que la lista tiene un solo usuario y que se llamó al método
        assertEquals(1, resultado.size());
        verify(usuariosRepository).findAll();
    }

    @Test
    void testFindById() {
        // Simula que el repositorio devuelve un usuario envuelto en Optional
        when(usuariosRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Llama al método real del servicio
        Usuarios resultado = usuariosService.findById(1);

        // Verifica que el usuario se encuentra y tiene el nombre correcto
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre_usuario());
        verify(usuariosRepository).findById(1);
    }

    @Test
    void testSave() {
        // Simula que el usuario se guarda correctamente
        when(usuariosRepository.save(usuario)).thenReturn(usuario);

        // Llama al método real del servicio
        Usuarios resultado = usuariosService.save(usuario);

        // Verifica que el usuario devuelto tiene los datos correctos
        assertEquals("Juan", resultado.getNombre_usuario());
        verify(usuariosRepository).save(usuario);
    }

    @Test
    void testDelete() {
        // Ejecuta el método de borrado
        usuariosService.delete(1);

        // Verifica que se llamó a deleteById() con el ID correcto
        verify(usuariosRepository).deleteById(1);
    }
}