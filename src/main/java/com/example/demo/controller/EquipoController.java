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

    @GetMapping
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.getEquipos()); 
        return "lista_equipos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoEquipo(Model model) {
        model.addAttribute("equipo", new EquipoDTO()); 
        return "formulario_equipos"; 
    }

    @PostMapping("/crear")
    public String crearEquipo(@ModelAttribute EquipoDTO equipo) {
        equipoService.crearEquipo(equipo); 
        return "redirect:/equipos"; 
    }

    @GetMapping("/{nombre}")
    public String verEquipo(@PathVariable String nombre, Model model) {

        EquipoDTO equipo = equipoService.getEquipo(nombre); 

        if (equipo == null) {
            return "redirect:/equipos";
        }

        model.addAttribute("equipo", equipo);
        return "ver_equipo"; 
    }

    @GetMapping("/{nombre}/anadir")
    public String mostrarSelectJugadores(@PathVariable String nombre, Model model) {

        model.addAttribute("nombreEquipo", nombre); 
        model.addAttribute("jugadoresLibres", equipoService.obtenerJugadoresSinEquipo()); 

        return "añadir_jugador_equipo"; 
    }

    @PostMapping("/{nombre}/anadir")
    public String anadirJugador(
            @PathVariable String nombre,
            @RequestParam String dniJugador) throws Exception {

        equipoService.asignarJugadorAEquipo(nombre, dniJugador); 
        return "redirect:/equipos/" + nombre; 
    }

    @GetMapping("/{nombre}/borrar")
    public String borrarEquipo(@PathVariable String nombre) {
        equipoService.eliminarEquipo(nombre); 
        return "redirect:/equipos"; 
    }
}
