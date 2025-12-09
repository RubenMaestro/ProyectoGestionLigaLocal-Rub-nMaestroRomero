package com.example.demo.services;

import java.util.List;
import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;

public interface EquipoService {
    List<EquipoDTO> getEquipos();
    void crearEquipo(EquipoDTO equipo);
    EquipoDTO getEquipo(String nombre);
    void eliminarEquipo(String nombre);
    List<JugadorDTO> obtenerJugadoresSinEquipo();
    void asignarJugadorAEquipo(String nombreEquipo, String dniJugador) throws Exception;
}
