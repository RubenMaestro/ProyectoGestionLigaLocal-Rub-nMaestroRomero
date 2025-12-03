package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.EquipoService;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final List<EquipoDTO> equipos = new ArrayList<>();

    @Override
    public List<EquipoDTO> getEquipos() {
        return equipos;
    }

    @Override
    public void crearEquipo(EquipoDTO equipo) {
        if (equipo == null || equipo.getNombre() == null) return;
        // Si ya existe un equipo con ese nombre, lo ignoramos
        if (getEquipo(equipo.getNombre()) != null) return;
        equipos.add(equipo);
    }

    @Override
    public EquipoDTO getEquipo(String nombre) {
        if (nombre == null) return null;
        for (EquipoDTO e : equipos) {
            if (e.getNombre() != null && e.getNombre().equalsIgnoreCase(nombre)) return e;
        }
        return null;
    }

    @Override
    public void añadirJugador(String nombreEquipo, JugadorDTO jugador) throws Exception {
        if (jugador == null || nombreEquipo == null) throw new IllegalArgumentException("Datos inválidos");
        EquipoDTO e = getEquipo(nombreEquipo);
        if (e == null) throw new Exception("Equipo no encontrado: " + nombreEquipo);

        if (jugador.getEdad() < 16) throw new Exception("El jugador debe tener al menos 16 años");

        // si ya tiene equipo asignado, no se permite (evitar duplicidad)
        if (jugador.getEquipo() != null && !jugador.getEquipo().trim().isEmpty()) {
            throw new Exception("El jugador ya pertenece al equipo: " + jugador.getEquipo());
        }

        if (e.getJugadores().size() >= 23) throw new Exception("El equipo ya tiene 23 jugadores");

        // Evitar duplicados por DNI dentro del equipo
        boolean existe = e.getJugadores().stream()
                .anyMatch(j -> j.getDni() != null && j.getDni().equalsIgnoreCase(jugador.getDni()));
        if (existe) throw new Exception("Jugador ya existe en el equipo");

        // Añadir al equipo
        e.agregarJugador(jugador);

        // Marcar equipo en el jugador
        jugador.setEquipo(nombreEquipo);
    }

    @Override
    public void eliminarEquipo(String nombre) {
        if (nombre == null) return;
        Iterator<EquipoDTO> it = equipos.iterator();
        while (it.hasNext()) {
            EquipoDTO e = it.next();
            if (e.getNombre() != null && e.getNombre().equalsIgnoreCase(nombre)) {
                // antes de borrar equipo, liberar equipo en jugadores
                for (JugadorDTO j : e.getJugadores()) {
                    j.setEquipo(null);
                }
                it.remove();
                break;
            }
        }
    }
}
