package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
    
    @GetMapping("/top")
    public String topGoleadores(Model model) {
        List<JugadorDTO> jugadores = new ArrayList<>(jugadorService.getJugadores());

        jugadores.sort((a, b) -> Integer.compare(b.getGoles(), a.getGoles()));

        model.addAttribute("jugadores", jugadores);
        model.addAttribute("titulo", "Máximos goleadores"); 

        return "top_goleadores"; 
    }

}
