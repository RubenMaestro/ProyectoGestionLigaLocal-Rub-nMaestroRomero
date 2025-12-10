package com.example.demo.services.impl;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.model.PartidoDTO;
import com.example.demo.services.ResultadosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultadosAvanzadosService implements ResultadosService {

    @Override
    public void calcular(PartidoDTO partido) {

        if (partido.getLocal() == null || partido.getVisitante() == null) {
            return;
        }
    }

    // Aplica estadísticas avanzadas a todos los jugadores
    public void aplicarEstadisticasJugadores(
            PartidoDTO partido,
            List<String> idLocal,
            List<Integer> golesLocal,
            List<Integer> asistLocal,
            List<Integer> amarillasLocal,
            List<Integer> rojasLocal,

            List<String> idVisit,
            List<Integer> golesVisit,
            List<Integer> asistVisit,
            List<Integer> amarillasVisit,
            List<Integer> rojasVisit
    ) {

        // --- LOCAL ---
        if (idLocal != null) {
            EquipoDTO eq = partido.getLocal();

            for (int i = 0; i < idLocal.size(); i++) {
                String nombre = idLocal.get(i);

                for (JugadorDTO j : eq.getJugadores()) {
                    if (nombre.equals(j.getNombre())) {

                        j.setGoles(j.getGoles() + safeInt(golesLocal, i));
                        j.setAsistencias(j.getAsistencias() + safeInt(asistLocal, i));
                        j.setAmarillas(j.getAmarillas() + safeInt(amarillasLocal, i));
                        j.setRojas(j.getRojas() + safeInt(rojasLocal, i));

                        break;
                    }
                }
            }
        }

        // --- VISITANTE ---
        if (idVisit != null) {
            EquipoDTO eq = partido.getVisitante();

            for (int i = 0; i < idVisit.size(); i++) {
                String nombre = idVisit.get(i);

                for (JugadorDTO j : eq.getJugadores()) {
                    if (nombre.equals(j.getNombre())) {

                        j.setGoles(j.getGoles() + safeInt(golesVisit, i));
                        j.setAsistencias(j.getAsistencias() + safeInt(asistVisit, i));
                        j.setAmarillas(j.getAmarillas() + safeInt(amarillasVisit, i));
                        j.setRojas(j.getRojas() + safeInt(rojasVisit, i));

                        break;
                    }
                }
            }
        }
    }

    // Devuelve 0 si el número no existe para evitar errores
    private int safeInt(List<Integer> list, int index) {
        if (list == null || index >= list.size() || list.get(index) == null) {
            return 0;
        }
        return list.get(index);
    }
}
