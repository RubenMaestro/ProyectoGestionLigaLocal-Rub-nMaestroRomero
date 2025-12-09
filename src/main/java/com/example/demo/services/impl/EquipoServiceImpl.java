package com.example.demo.services.impl;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.EquipoService;
import com.example.demo.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

    private List<EquipoDTO> equipos = new ArrayList<>();

    @Autowired
    private JugadorService jugadorService;

    @Override
    public List<EquipoDTO> getEquipos() {
        return equipos;
    }

    @Override
    public void crearEquipo(EquipoDTO equipo) {

        // Inicializar lista de jugadores si viene null
        if (equipo.getJugadores() == null) {
            equipo.setJugadores(new ArrayList<>());
        }

        // Inicializar estadísticas a 0 
        equipo.setPuntos(0);
        equipo.setVictorias(0);
        equipo.setEmpates(0);
        equipo.setDerrotas(0);
        equipo.setGolesFavor(0);
        equipo.setGolesContra(0);

        equipos.add(equipo);
    }

    @Override
    public EquipoDTO getEquipo(String nombre) {
        return equipos.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarEquipo(String nombre) {
        Iterator<EquipoDTO> it = equipos.iterator();

        while (it.hasNext()) {
            EquipoDTO e = it.next();

            if (e.getNombre().equalsIgnoreCase(nombre)) {

                // liberar jugadores
                if (e.getJugadores() != null) {
                    for (JugadorDTO j : e.getJugadores()) {
                        j.setEquipo(null);
                    }
                }

                it.remove();
                return;
            }
        }
    }

    @Override
    public List<JugadorDTO> obtenerJugadoresSinEquipo() {

        List<JugadorDTO> libres = new ArrayList<>();

        // Buscar jugadores sin equipo desde el servicio de jugadores
        for (JugadorDTO j : jugadorService.getJugadores()) {
            if (j.getEquipo() == null) {
                libres.add(j);
            }
        }

        return libres;
    }

    @Override
    public void asignarJugadorAEquipo(String nombreEquipo, String dniJugador) throws Exception {

        EquipoDTO equipo = getEquipo(nombreEquipo);
        if (equipo == null) throw new Exception("Equipo no encontrado");

        // asegurar que la lista existe
        if (equipo.getJugadores() == null) {
            equipo.setJugadores(new ArrayList<>());
        }

        for (JugadorDTO j : jugadorService.getJugadores()) {

            if (j.getDni().equalsIgnoreCase(dniJugador)) {

                if (equipo.getJugadores().size() >= 23)
                    throw new Exception("El equipo ya tiene 23 jugadores");

                j.setEquipo(nombreEquipo);
                equipo.getJugadores().add(j);
                return;
            }
        }

        throw new Exception("Jugador no encontrado");
    }
}
