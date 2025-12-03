package com.example.demo.services;

import java.util.List;

import com.example.demo.model.PartidoDTO;

public interface PartidoService {
    List<PartidoDTO> getPartidos();
    void programarPartido(PartidoDTO partido);
    void borrarPartido(PartidoDTO partido);
}
