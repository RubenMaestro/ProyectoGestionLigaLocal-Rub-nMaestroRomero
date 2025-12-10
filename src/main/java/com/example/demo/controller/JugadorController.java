package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.EquipoService;
import com.example.demo.services.JugadorService;

@Controller
@RequestMapping("/jugadores") 
public class JugadorController {

    @Autowired
    private JugadorService jugadorService; 
    
    @Autowired
    private EquipoService equipoService;


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
    public String maximoGoleadores(Model model) {
        model.addAttribute("jugadores", jugadorService.getMaximoGoleadores());
        return "top_goleadores";
    }

    
    @GetMapping("/porteros")
    public String porterosMenosGoleados(Model model) {

        List<JugadorDTO> jugadores = new ArrayList<>(jugadorService.getJugadores());
        
        List<JugadorDTO> porteros = jugadores.stream()
                .filter(j -> j.getPosicion().equalsIgnoreCase("Portero"))
                .collect(Collectors.toList());

        class PorteroInfo {
            public JugadorDTO jugador;
            public int golesContra;

            public PorteroInfo(JugadorDTO jugador, int golesContra) {
                this.jugador = jugador;
                this.golesContra = golesContra;
            }
        }

        List<PorteroInfo> lista = new ArrayList<>();

        for (JugadorDTO j : porteros) {
            EquipoDTO eq = equipoService.getEquipo(j.getEquipo());
            int goles = (eq != null) ? eq.getGolesContra() : Integer.MAX_VALUE;
            lista.add(new PorteroInfo(j, goles));
        }

        lista.sort(Comparator.comparingInt(p -> p.golesContra));

        model.addAttribute("porteros", lista);

        return "top_porteros";
    }
}
