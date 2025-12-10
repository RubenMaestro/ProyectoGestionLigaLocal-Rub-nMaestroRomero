package com.example.demo.controller;

import com.example.demo.model.EquipoDTO;
import com.example.demo.services.EquipoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClasificacionController {

    @Autowired
    private EquipoService equipoService; 
    @GetMapping("/clasificacion")
    public String clasificacion(Model model) {

        List<EquipoDTO> equipos = equipoService.getEquipos();

        // Ordeno los equipos según:
        // 1. Más puntos
        // 2. Mejor diferencia de goles
        // 3. Más goles a favor
        equipos.sort((a, b) -> {
            int cmpPts = Integer.compare(b.getPuntos(), a.getPuntos());
            if (cmpPts != 0) return cmpPts;

            int dgA = a.getGolesFavor() - a.getGolesContra(); 
            int dgB = b.getGolesFavor() - b.getGolesContra(); 

            int cmpDG = Integer.compare(dgB, dgA); 
            if (cmpDG != 0) return cmpDG;

            return Integer.compare(b.getGolesFavor(), a.getGolesFavor()); 
        });

        model.addAttribute("equipos", equipos);
        return "clasificacion";
    }
}
