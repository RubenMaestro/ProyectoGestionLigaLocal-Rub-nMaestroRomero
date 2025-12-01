package com.example.demo.services;

import java.util.List;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;

public interface EquipoService {
    List<EquipoDTO> getEquipos();
    void CrearEquipo(EquipoDTO equipo);
    EquipoDTO getEquipo(String nombre);
    void añadirJugador(String nombreEquipo, JugadorDTO jugador) throws Exception;
    void eliminarEquipo(String nombre);
}