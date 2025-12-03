package com.example.demo.controller;

import com.example.demo.model.EquipoDTO;
import com.example.demo.services.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    // Mostrar lista de equipos
    @GetMapping
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.getEquipos());
        return "lista_equipos";
    }

    // Formulario nuevo equipo
    @GetMapping("/nuevo")
    public String nuevoEquipo(Model model) {
        model.addAttribute("equipo", new EquipoDTO());
        return "formulario_equipos";  
    }

    // Crear equipo
    @PostMapping("/crear")
    public String crearEquipo(@ModelAttribute EquipoDTO equipo) {
        equipoService.crearEquipo(equipo);
        return "redirect:/equipos";
    }

    // Borrar equipo por nombre
    @GetMapping("/{nombre}/borrar")
    public String borrarEquipo(@PathVariable String nombre) {
        equipoService.eliminarEquipo(nombre);
        return "redirect:/equipos";
    }
}
