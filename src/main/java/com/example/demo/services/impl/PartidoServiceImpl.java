package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.PartidoDTO;
import com.example.demo.services.PartidoService;

@Service
public class PartidoServiceImpl implements PartidoService {

    private final List<PartidoDTO> partidos = new ArrayList<>();

    @Override
    public List<PartidoDTO> getPartidos() {
        return partidos;
    }

    @Override
    public void programarPartido(PartidoDTO partido) {
        if (partido == null) return;
        partido.setJugado(false);
        partidos.add(partido);
    }

    @Override
    public void borrarPartido(PartidoDTO partido) {
        partidasRemove(partido);
    }

    private void partidasRemove(PartidoDTO partido) {
        partidos.removeIf(p -> p == partido || (p.getFecha() != null && partido.getFecha() != null && p.getFecha().equals(partido.getFecha()) && p.getLocal() == partido.getLocal() && p.getVisitante() == partido.getVisitante()));
    }
}
