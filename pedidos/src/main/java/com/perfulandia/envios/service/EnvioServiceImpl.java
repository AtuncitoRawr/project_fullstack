package com.perfulandia.envios.service;

import com.perfulandia.envios.model.Envio;
import com.perfulandia.envios.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnvioServiceImpl implements EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioServiceImpl(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    @Override
    public List<Envio> getAllEnvios() {
        return envioRepository.findAll();
    }

    @Override
    public Envio getEnvioById(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Envio no encontrado con id " + id));
    }

    @Override
    public Envio saveEnvio(Envio envio) {
        return envioRepository.save(envio);
    }

    @Override
    public Envio updateEnvio(Long id, Envio envio) {
        Envio existing = getEnvioById(id);
        existing.setDireccionDestino(envio.getDireccionDestino());
        existing.setEstado(envio.getEstado());
        existing.setFechaEnvio(envio.getFechaEnvio());
        existing.setFechaEntregaEstimada(envio.getFechaEntregaEstimada());
        existing.setPedidoId(envio.getPedidoId());
        return envioRepository.save(existing);
    }

    @Override
    public void deleteEnvio(Long id) {
        envioRepository.deleteById(id);
    }
}