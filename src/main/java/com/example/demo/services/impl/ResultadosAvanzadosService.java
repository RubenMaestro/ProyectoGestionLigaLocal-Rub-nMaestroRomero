package com.example.demo.services.impl;

import com.example.demo.model.PartidoDTO;
import com.example.demo.services.ResultadosService;

import org.springframework.stereotype.Service;

@Service
public class ResultadosAvanzadosService implements ResultadosService {

    @Override
    public void calcular(PartidoDTO partido) {

        Integer golesLocal = partido.getGolesLocal();
        Integer golesVisitante = partido.getGolesVisitante();

        if (golesLocal == null || golesVisitante == null) {
            return;
        }

        int diferencia = Math.abs(golesLocal - golesVisitante);

        System.out.println("Diferencia de goles: " + diferencia);

        if (diferencia >= 3) {
            System.out.println("Goleada detectada");
        }
    }
}
