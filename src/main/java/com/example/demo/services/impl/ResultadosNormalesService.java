package com.example.demo.services.impl;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.PartidoDTO;
import com.example.demo.services.ResultadosService;
import org.springframework.stereotype.Service;

@Service
public class ResultadosNormalesService implements ResultadosService {

    @Override
    public void calcular(PartidoDTO partido) {

        EquipoDTO local = partido.getLocal();
        EquipoDTO visitante = partido.getVisitante();

        int golesLocal = partido.getGolesLocal();
        int golesVisitante = partido.getGolesVisitante();

        // Actualizar goles a favor y en contra
        local.setGolesFavor(local.getGolesFavor() + golesLocal);
        local.setGolesContra(local.getGolesContra() + golesVisitante);

        visitante.setGolesFavor(visitante.getGolesFavor() + golesVisitante);
        visitante.setGolesContra(visitante.getGolesContra() + golesLocal);

        // Resultado
        if (golesLocal > golesVisitante) {
            // Victoria local
            local.setVictorias(local.getVictorias() + 1);
            local.setPuntos(local.getPuntos() + 3);

            visitante.setDerrotas(visitante.getDerrotas() + 1);

        } else if (golesLocal < golesVisitante) {
            // Victoria visitante
            visitante.setVictorias(visitante.getVictorias() + 1);
            visitante.setPuntos(visitante.getPuntos() + 3);

            local.setDerrotas(local.getDerrotas() + 1);

        } else {
            // Empate
            local.setEmpates(local.getEmpates() + 1);
            visitante.setEmpates(visitante.getEmpates() + 1);

            local.setPuntos(local.getPuntos() + 1);
            visitante.setPuntos(visitante.getPuntos() + 1);
        }
    }
}
