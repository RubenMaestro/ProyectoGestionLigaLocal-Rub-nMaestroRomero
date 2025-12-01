package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.model.PartidoDTO;
import com.example.demo.services.ResultadosService;

@Service("resultadosAvanzadosService")
public class ResultadosAvanzadosService implements ResultadosService {

    // Inyectamos el servicio normal para que Spring lo gestione y procese lo básico
    @Autowired
    private ResultadosNormalesService normales;

    @Override
    public void procesarResultados(PartidoDTO partido) {

        // 1. Procesar lo básico (victorias, empates, puntos, goles de equipo)
        normales.procesarResultados(partido);

        // 2. Estadísticas avanzadas por jugador
        // Se espera que partido contenga listas con las contribuciones de jugadores en ese partido,
        // recogidas desde el formulario partidos_jugar.html
        
        if (partido.getLocal() != null && partido.getJugadoresLocalPartido() != null && !partido.getJugadoresLocalPartido().isEmpty()) {
            actualizarEstadisticasJugadores(partido.getLocal(), partido.getJugadoresLocalPartido());
        }
        if (partido.getVisitante() != null && partido.getJugadoresVisitantePartido() != null && !partido.getJugadoresVisitantePartido().isEmpty()) {
            actualizarEstadisticasJugadores(partido.getVisitante(), partido.getJugadoresVisitantePartido());
        }
    }

    private void actualizarEstadisticasJugadores(EquipoDTO equipo, List<JugadorDTO> jugadoresPartido) {
        if (equipo == null || jugadoresPartido == null || jugadoresPartido.isEmpty())
            return;

        // Buscar cada jugador por algún identificador (DNI o nombre) y sumar sus estadísticas
        for (JugadorDTO jugadorPartido : jugadoresPartido) {
            
            // Buscar el jugador en el equipo por DNI o Nombre
            JugadorDTO jugadorEquipo = equipo.getJugadores().stream()
                .filter(j -> {
                    // Usar DNI si está disponible
                    if (jugadorPartido.getDni() != null && !jugadorPartido.getDni().isEmpty() && j.getDni() != null) {
                        return jugadorPartido.getDni().equalsIgnoreCase(j.getDni());
                    }
                    // Si no, usar nombre (menos seguro pero necesario si DNI no se usa)
                    if (jugadorPartido.getNombre() != null && j.getNombre() != null) {
                        return jugadorPartido.getNombre().equalsIgnoreCase(j.getNombre());
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);

            if (jugadorEquipo != null) {
                sumarEstadisticasJugador(jugadorEquipo, jugadorPartido);
            }
        }
    }

    private void sumarEstadisticasJugador(JugadorDTO destino, JugadorDTO añadido) {
        // La información del DNI y Nombre del jugador "añadido" viene del formulario y se usa para la búsqueda.
        // Solo debemos sumar los contadores.
        destino.setGoles(destino.getGoles() + añadido.getGoles());
        destino.setAsistencias(destino.getAsistencias() + añadido.getAsistencias());
        destino.setAmarillas(destino.getAmarillas() + añadido.getAmarillas());
        destino.setRojas(destino.getRojas() + añadido.getRojas());
    }
}