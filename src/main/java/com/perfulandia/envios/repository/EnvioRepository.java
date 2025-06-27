package com.perfulandia.envios.repository;

import com.perfulandia.envios.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstado(String estado);
    List<Envio> findByPedidoId(Long pedidoId);
}
