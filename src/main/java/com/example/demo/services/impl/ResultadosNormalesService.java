package com.example.demo.services.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.PartidoDTO;
import com.example.demo.services.ResultadosService;

@Service("resultadosNormalesService")
public class ResultadosNormalesService implements ResultadosService {

    @Override
    public void procesarResultados(PartidoDTO partido) {

        int gl = partido.getGolesLocal();
        int gv = partido.getGolesVisitante();

        // actualizar goles a favor / en contra del equipo
        if (partido.getLocal() != null) {
            partido.getLocal().setGolesFavor(partido.getLocal().getGolesFavor() + gl);
            partido.getLocal().setGolesContra(partido.getLocal().getGolesContra() + gv);
        }
        if (partido.getVisitante() != null) {
            partido.getVisitante().setGolesFavor(partido.getVisitante().getGolesFavor() + gv);
            partido.getVisitante().setGolesContra(partido.getVisitante().getGolesContra() + gl);
        }

        if (gl > gv) {
            // Gana el equipo local
            partido.getLocal().setPuntos(partido.getLocal().getPuntos() + 3);
            partido.getLocal().setVictorias(partido.getLocal().getVictorias() + 1);
            
            if (partido.getVisitante() != null) {
                 partido.getVisitante().setDerrotas(partido.getVisitante().getDerrotas() + 1);
            }
        } else if (gl < gv) {
            // Gana el equipo visitante
            partido.getVisitante().setPuntos(partido.getVisitante().getPuntos() + 3);
            partido.getVisitante().setVictorias(partido.getVisitante().getVictorias() + 1);
            
            if (partido.getLocal() != null) {
                partido.getLocal().setDerrotas(partido.getLocal().getDerrotas() + 1);
            }
        } else {
            // Empate
            partido.getLocal().setPuntos(partido.getLocal().getPuntos() + 1);
            partido.getLocal().setEmpates(partido.getLocal().getEmpates() + 1);
            
            if (partido.getVisitante() != null) {
                partido.getVisitante().setPuntos(partido.getVisitante().getPuntos() + 1);
                partido.getVisitante().setEmpates(partido.getVisitante().getEmpates() + 1);
            }
        }
    }
}