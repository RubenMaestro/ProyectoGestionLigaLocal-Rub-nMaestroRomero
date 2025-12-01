package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.services.EquipoService;

@Controller
public class ClasificacionController {

    private final EquipoService equipoService;

    public ClasificacionController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping("/clasificacion")
    public String clasificacion(Model model) {

        model.addAttribute("equipos",
                equipoService.getEquipos().stream()
                        .sorted((a, b) -> b.getPuntos() - a.getPuntos()) // Ordena por puntos
                        .toList()
        );

        return "clasificacion";
    }
}