package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.EquipoService;

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
    public String nuevoEquipo(Model model) {
        model.addAttribute("equipo", new EquipoDTO());
        return "formulario_equipos";
    }

    @PostMapping("/guardar")
    public String guardarEquipo(@ModelAttribute EquipoDTO equipo) {
        equipoService.CrearEquipo(equipo);
        return "redirect:/equipos";
    }

    @GetMapping("/{nombre}")
    public String verEquipo(@PathVariable String nombre, Model model) {
        EquipoDTO equipo = equipoService.getEquipo(nombre);
        model.addAttribute("equipo", equipo);
        return "ver_equipo";
    }

    @GetMapping("/{nombre}/agregarJugador")
    public String agregarJugadorForm(@PathVariable String nombre, Model model) {
        model.addAttribute("equipo", nombre);
        model.addAttribute("jugador", new JugadorDTO());
        return "añadir_jugador_equipo";
    }

    @PostMapping("/{nombre}/guardarJugador")
    public String guardarJugador(@PathVariable String nombre, @ModelAttribute JugadorDTO jugador) throws Exception {
        // Se lanza una excepción si el jugador es menor de 16 años o si el equipo tiene 23 jugadores.
        try {
            equipoService.añadirJugador(nombre, jugador);
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción de edad
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // Manejo de la excepción de máximo de jugadores
            System.err.println(e.getMessage());
        }
        return "redirect:/equipos/" + nombre;
    }
    
    @GetMapping("/{nombre}/eliminar")
    public String eliminarEquipo(@PathVariable String nombre) {
        equipoService.eliminarEquipo(nombre);
        return "redirect:/equipos";
    }
}