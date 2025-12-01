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

    // Crea un equipo o actualiza su entrenador si ya existe
    @Override
    public void CrearEquipo(EquipoDTO equipo) {
        if (equipo != null && equipo.getNombre() != null) {
            EquipoDTO exist = getEquipo(equipo.getNombre());
            if (exist == null) {
                equipos.add(equipo);
            } else {
                exist.setEntrenador(equipo.getEntrenador());
            }
        }
    }

    // Busca un equipo por nombre
    @Override
    public EquipoDTO getEquipo(String nombre) {
        if (nombre == null) return null;
        for (EquipoDTO e : equipos) {
            if (e.getNombre() != null && e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }

    // Añade un jugador a un equipo con las validaciones
    @Override
    public void añadirJugador(String nombreEquipo, JugadorDTO jugador) throws Exception {
        EquipoDTO equipo = getEquipo(nombreEquipo);
        if (equipo == null) {
            throw new Exception("El equipo no existe");
        } else if (equipo.getJugadores().size() >= 23) {
            throw new Exception("El equipo ya tiene el máximo de 23 jugadores");
        } else if (jugador.getEdad() <= 16) { // Validacion de edad
            throw new IllegalArgumentException("El jugador debe ser mayor de 16 años para ser añadido al equipo");
        } else {
            equipo.getJugadores().add(jugador);
        }
    }

    // Elimina un equipo por nombre
    @Override
    public void eliminarEquipo(String nombre) {
        if (nombre == null) return;
        Iterator<EquipoDTO> it = equipos.iterator();
        while (it.hasNext()) {
            EquipoDTO e = it.next();
            if (e.getNombre() != null && e.getNombre().equalsIgnoreCase(nombre)) {
                it.remove();
                break;
            }
        }
    }
}