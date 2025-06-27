package com.perfulandia.envios;

import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.repository.EnvioRepository;
import com.perfulandia.envios.service.EnvioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testListarTodos() {
        Envio envio = new Envio();
        envio.setEstado("En camino");

        when(envioRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> envios = envioService.listarTodos();

        assertEquals(1, envios.size());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        Envio envio = new Envio();
        envio.setId(1L);

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        Optional<Envio> resultado = envioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testGuardar() {
        Envio envio = new Envio();
        envio.setEstado("Pendiente");

        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.guardar(envio);

        assertEquals("Pendiente", resultado.getEstado());
    }

    @Test
    void testEliminar() {
        when(envioRepository.existsById(1L)).thenReturn(true);
        envioService.eliminar(1L);
        verify(envioRepository).deleteById(1L);
    }

    @Test
    void testEliminarNoExiste() {
        when(envioRepository.existsById(2L)).thenReturn(false);
        boolean resultado = envioService.eliminar(2L);
        assertFalse(resultado);
        verify(envioRepository, never()).deleteById(anyLong());
    }
}