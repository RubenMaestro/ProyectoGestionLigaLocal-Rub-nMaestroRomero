package com.example.demo.services.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.PartidoDTO;
import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.ResultadosService;

import java.util.HashMap;
import java.util.Map;

// Modo avanzado: actualiza equipos + estadísticas de jugadores (deltas).
@Service("resultadosAvanzadosService")
public class ResultadosAvanzadosService implements ResultadosService {

    @Override
    public void procesarResultados(PartidoDTO partido) {
        if (partido == null) return;

        EquipoDTO local = partido.getLocal();
        EquipoDTO visitante = partido.getVisitante();
        if (local == null || visitante == null) return;

        int gl = partido.getGolesLocal();
        int gv = partido.getGolesVisitante();

        // Actualizar goles y puntos (igual que modo normal)
        local.setGolesFavor(local.getGolesFavor() + gl);
        local.setGolesContra(local.getGolesContra() + gv);
        visitante.setGolesFavor(visitante.getGolesFavor() + gv);
        visitante.setGolesContra(visitante.getGolesContra() + gl);

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

        // Construir mapa de deltas por DNI (merge deltas si hay duplicados)
        Map<String, JugadorDTO> deltaMap = new HashMap<>();

        if (partido.getJugadoresLocalPartido() != null) {
            for (JugadorDTO d : partido.getJugadoresLocalPartido()) {
                if (d == null || d.getDni() == null) continue;
                JugadorDTO ex = deltaMap.get(d.getDni());
                if (ex == null) deltaMap.put(d.getDni(), d);
                else {
                    ex.setGoles(ex.getGoles() + d.getGoles());
                    ex.setAsistencias(ex.getAsistencias() + d.getAsistencias());
                    ex.setAmarillas(ex.getAmarillas() + d.getAmarillas());
                    ex.setRojas(ex.getRojas() + d.getRojas());
                }
            }
        }

        if (partido.getJugadoresVisitantePartido() != null) {
            for (JugadorDTO d : partido.getJugadoresVisitantePartido()) {
                if (d == null || d.getDni() == null) continue;
                JugadorDTO ex = deltaMap.get(d.getDni());
                if (ex == null) deltaMap.put(d.getDni(), d);
                else {
                    ex.setGoles(ex.getGoles() + d.getGoles());
                    ex.setAsistencias(ex.getAsistencias() + d.getAsistencias());
                    ex.setAmarillas(ex.getAmarillas() + d.getAmarillas());
                    ex.setRojas(ex.getRojas() + d.getRojas());
                }
            }
        }

        // Aplicar deltas a jugadores reales de ambos equipos
        if (!deltaMap.isEmpty()) {
            for (JugadorDTO real : local.getJugadores()) {
                JugadorDTO delta = deltaMap.get(real.getDni());
                if (delta != null) applyDelta(real, delta);
            }
            for (JugadorDTO real : visitante.getJugadores()) {
                JugadorDTO delta = deltaMap.get(real.getDni());
                if (delta != null) applyDelta(real, delta);
            }
        }
    }

    private void applyDelta(JugadorDTO real, JugadorDTO delta) {
        if (real == null || delta == null) return;
        real.setGoles(real.getGoles() + safe(delta.getGoles()));
        real.setAsistencias(real.getAsistencias() + safe(delta.getAsistencias()));
        real.setAmarillas(real.getAmarillas() + safe(delta.getAmarillas()));
        real.setRojas(real.getRojas() + safe(delta.getRojas()));

        // Ajuste simple del valor de mercado
        double v = real.getValorMercado();
        v += safe(delta.getGoles()) * 5000.0;
        v += safe(delta.getAsistencias()) * 2000.0;
        v -= safe(delta.getAmarillas()) * 2000.0;
        v -= safe(delta.getRojas()) * 5000.0;
        real.setValorMercado(v);
    }

    private int safe(Integer x) { return x == null ? 0 : x; }
}
