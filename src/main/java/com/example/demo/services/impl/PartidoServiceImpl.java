package com.example.demo.services.impl;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.PartidoDTO;
import com.example.demo.services.EquipoService;
import com.example.demo.services.PartidoService;
import com.example.demo.services.ResultadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartidoServiceImpl implements PartidoService {

    private final EquipoService equipoService;
    private final ResultadosService resultadosNormalesService;
    private final ResultadosService resultadosAvanzadosService;

    private List<PartidoDTO> partidos = new ArrayList<>();

    @Autowired
    public PartidoServiceImpl(
            EquipoService equipoService,
            ResultadosNormalesService normalesSrv,
            ResultadosAvanzadosService avanzadosSrv) {

        this.equipoService = equipoService;
        this.resultadosNormalesService = normalesSrv;
        this.resultadosAvanzadosService = avanzadosSrv;
    }


    // Devuelve la lista actual de partidos
    @Override
    public List<PartidoDTO> getPartidos() {
        return partidos;
    }

    // Obtener un partido por índice
    @Override
    public PartidoDTO obtenerPartido(int index) {
        if (index < 0 || index >= partidos.size()) return null;
        return partidos.get(index);
    }

    // Crear partido
    @Override
    public void crearPartido(PartidoDTO partido, String localNombre, String visitanteNombre) {
        EquipoDTO local = equipoService.getEquipo(localNombre);
        EquipoDTO visitante = equipoService.getEquipo(visitanteNombre);

        if (local == null || visitante == null) {
            return;
        }

        partido.setLocal(local);
        partido.setVisitante(visitante);
        if (partido.getGolesLocal() == null) partido.setGolesLocal(0);
        if (partido.getGolesVisitante() == null) partido.setGolesVisitante(0);
        partido.setJugado(false);

        partidos.add(partido);
    }

    // Registrar resultado y delegar en servicios de resultados
    @Override
    public void registrarResultado(int index, int golesLocal, int golesVisitante) {
        PartidoDTO partido = obtenerPartido(index);
        if (partido == null) return;

        partido.setGolesLocal(golesLocal);
        partido.setGolesVisitante(golesVisitante);
        partido.setJugado(true);

        resultadosNormalesService.calcular(partido);
        resultadosAvanzadosService.calcular(partido);
    }
}
