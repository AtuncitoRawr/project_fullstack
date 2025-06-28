package com.perfulandia.envios.service;

import com.perfulandia.envios.model.Envio;
import java.util.List;

public interface EnvioService {

    List<Envio> getAllEnvios();

    Envio getEnvioById(Long id);

    Envio saveEnvio(Envio envio);

    Envio updateEnvio(Long id, Envio envio);

    void deleteEnvio(Long id);
}