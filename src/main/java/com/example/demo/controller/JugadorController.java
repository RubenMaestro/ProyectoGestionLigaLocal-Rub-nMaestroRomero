package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.JugadorDTO;
import com.example.demo.services.JugadorService;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("jugadores", jugadorService.getJugadores());
        return "lista_jugadores";
    }

    @GetMapping("/nuevo")
    public String nuevoJugador(Model model) {
        model.addAttribute("jugador", new JugadorDTO());
        return "formulario_jugadores";
    }
    
    
    @PostMapping("/guardar")
    public String guardar(JugadorDTO jugador) {
        try {
            jugadorService.crearJugador(jugador);
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción de edad
            System.err.println(e.getMessage());
        }
        return "redirect:/jugadores";
    }
}