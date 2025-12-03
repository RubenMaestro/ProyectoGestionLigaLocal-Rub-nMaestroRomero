package com.example.demo.services;

import java.util.List;
import com.example.demo.model.JugadorDTO;

public interface JugadorService {
    List<JugadorDTO> getJugadores();
    void crearJugador(JugadorDTO jugador) throws Exception;
    JugadorDTO getJugadorPorDni(String dni);
    void eliminarJugador(String dni);

    // Nuevo: devuelve jugadores que no tienen equipo asignado
    List<JugadorDTO> getJugadoresSinEquipo();
}
