package com.example.demo.services.impl;

import org.springframework.stereotype.Service;
import com.example.demo.model.PartidoDTO;
import com.example.demo.model.EquipoDTO;
import com.example.demo.services.ResultadosService;

// Modo normal: actualiza solo estadísticas de equipo y puntos.
@Service("resultadosNormalesService")
public class ResultadosNormalesService implements ResultadosService {

    @Override
    public void procesarResultados(PartidoDTO partido) {
        if (partido == null) return;

        EquipoDTO local = partido.getLocal();
        EquipoDTO visitante = partido.getVisitante();
        if (local == null || visitante == null) return;

        int gl = partido.getGolesLocal();
        int gv = partido.getGolesVisitante();

        // Goles
        local.setGolesFavor(local.getGolesFavor() + gl);
        local.setGolesContra(local.getGolesContra() + gv);
        visitante.setGolesFavor(visitante.getGolesFavor() + gv);
        visitante.setGolesContra(visitante.getGolesContra() + gl);

        // Resultado y puntos
        if (gl > gv) {
            local.setVictorias(local.getVictorias() + 1);
            local.setPuntos(local.getPuntos() + 3);
            visitante.setDerrotas(visitante.getDerrotas() + 1);
        } else if (gl < gv) {
            visitante.setVictorias(visitante.getVictorias() + 1);
            visitante.setPuntos(visitante.getPuntos() + 3);
            local.setDerrotas(local.getDerrotas() + 1);
        } else {
            local.setEmpates(local.getEmpates() + 1);
            visitante.setEmpates(visitante.getEmpates() + 1);
            local.setPuntos(local.getPuntos() + 1);
            visitante.setPuntos(visitante.getPuntos() + 1);
        }

        partido.setJugado(true);
    }
}
