package com.perfulandia.envios.service;

import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    public Optional<Envio> buscarPorId(Long id) {
        return envioRepository.findById(id);
    }

    public List<Envio> listarPorEstado(String estado) {
        return envioRepository.findByEstado(estado);
    }

    public List<Envio> listarPorPedidoId(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId);
    }

    public Envio guardar(Envio envio) {
        return envioRepository.save(envio);
    }

    public Envio actualizar(Long id, Envio envioActualizado) {
        return envioRepository.findById(id).map(envio -> {
            envio.setDireccionDestino(envioActualizado.getDireccionDestino());
            envio.setEstado(envioActualizado.getEstado());
            envio.setFechaEnvio(envioActualizado.getFechaEnvio());
            envio.setFechaEntregaEstimada(envioActualizado.getFechaEntregaEstimada());
            envio.setPedidoId(envioActualizado.getPedidoId());
            return envioRepository.save(envio);
        }).orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    public boolean eliminar(Long id) {
        if(envioRepository.existsById(id)) {
            envioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
