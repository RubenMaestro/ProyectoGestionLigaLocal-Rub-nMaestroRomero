package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.JugadorService;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("jugadores", jugadorService.getJugadores());
        return "lista_jugadores";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("jugador", new JugadorDTO());
        return "formulario_jugadores";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute JugadorDTO jugador, Model model) {
        try {
            jugadorService.crearJugador(jugador);
            return "redirect:/jugadores";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("jugador", jugador);
            return "formulario_jugadores";
        }
    }

    @GetMapping("/{dni}/eliminar")
    public String eliminar(@PathVariable String dni) {
        jugadorService.eliminarJugador(dni);
        return "redirect:/jugadores";
    }
}
