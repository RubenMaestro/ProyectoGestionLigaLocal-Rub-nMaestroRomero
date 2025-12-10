package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.JugadorService;

@Service
public class JugadorServiceImpl implements JugadorService {

    private final List<JugadorDTO> jugadores = new ArrayList<>();

    @Override
    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    @Override
    public void crearJugador(JugadorDTO jugador) throws Exception {
        if (jugador == null) throw new IllegalArgumentException("Jugador nulo");
        if (jugador.getEdad() < 16) throw new Exception("El jugador debe tener al menos 16 años");
        if (jugador.getDni() == null || jugador.getDni().trim().isEmpty()) throw new Exception("DNI obligatorio");
        // evitar duplicado global por DNI
        boolean existe = jugadores.stream().anyMatch(j -> jugador.getDni().equalsIgnoreCase(j.getDni()));
        if (existe) throw new Exception("Ya existe un jugador con ese DNI");
        jugadores.add(jugador);
    }

    @Override
    public JugadorDTO getJugadorPorDni(String dni) {
        if (dni == null) return null;
        for (JugadorDTO j : jugadores) {
            if (j.getDni() != null && j.getDni().equalsIgnoreCase(dni)) return j;
        }
        return null;
    }

    @Override
    public List<JugadorDTO> getJugadoresSinEquipo() {
        List<JugadorDTO> libres = new ArrayList<>();
        for (JugadorDTO j : jugadores) {
            if (j.getEquipo() == null || j.getEquipo().trim().isEmpty()) {
                libres.add(j);
            }
        }
        return libres;
    }
    
    @Override
    public List<JugadorDTO> getMaximoGoleadores() {
        List<JugadorDTO> jugadores = getJugadores();
        jugadores.sort(Comparator.comparingInt(JugadorDTO::getGoles).reversed());
        return jugadores;
    }

}
