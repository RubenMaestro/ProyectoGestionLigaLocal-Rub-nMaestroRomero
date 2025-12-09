package com.example.demo.services;

import com.example.demo.model.PartidoDTO;
import java.util.List;

public interface PartidoService {

    List<PartidoDTO> getPartidos();

    PartidoDTO obtenerPartido(int index);

    void crearPartido(PartidoDTO partido, String localNombre, String visitanteNombre);

    void registrarResultado(int index, int golesLocal, int golesVisitante);

    void eliminarPartido(int index);
}
