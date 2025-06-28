package com.perfulandia.envios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.envios.controller.EnvioController;
import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.service.EnvioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    private Envio envio;

    @BeforeEach
    void setup() {
        envio = new Envio();
        envio.setId(1L);
        envio.setDireccionDestino("Calle 123");
        envio.setEstado("Pendiente");
        envio.setFechaEnvio(LocalDate.now());
        envio.setFechaEntregaEstimada(LocalDate.now().plusDays(3));
        envio.setPedidoId(100L);
    }

    @Test
    void obtenerPorId() throws Exception {
        when(envioService.buscarPorId(1L)).thenReturn(Optional.of(envio));

        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerTodos() throws Exception {
        when(envioService.listarTodos()).thenReturn(List.of(envio));

        mockMvc.perform(get("/api/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("Pendiente"));
    }

    @Test
    void crearEnvio() throws Exception {
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);

        mockMvc.perform(post("/api/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }

    @Test
    void eliminarEnvio() throws Exception {
        doNothing().when(envioService).eliminar(1L);

        mockMvc.perform(delete("/api/envios/1"))
                .andExpect(status().isOk());
    }
}