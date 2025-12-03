package com.example.demo.controller;

import com.example.demo.model.PartidoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.PartidoService;
import com.example.demo.services.EquipoService;
import com.example.demo.services.ResultadosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    @Qualifier("resultadosNormalesService")
    private ResultadosService resultadosService;

    @Autowired
    @Qualifier("resultadosAvanzadosService")
    private ResultadosService resultadosAvanzadosService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("partidos", partidoService.getPartidos());
        return "lista_partidos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        PartidoDTO p = new PartidoDTO();
        p.setFecha(LocalDate.now());
        model.addAttribute("partido", p);
        model.addAttribute("equipos", equipoService.getEquipos());
        return "formulario_partidos";
    }

    @PostMapping("/programar")
    public String programar(@ModelAttribute PartidoDTO partido,
                            @RequestParam String nombreLocal,
                            @RequestParam String nombreVisitante) {

        partido.setLocal(equipoService.getEquipo(nombreLocal));
        partido.setVisitante(equipoService.getEquipo(nombreVisitante));
        partidoService.programarPartido(partido);
        return "redirect:/partidos";
    }

    @GetMapping("/{index}/jugar")
    public String jugar(@PathVariable int index, Model model) {
        if (index < 0 || index >= partidoService.getPartidos().size()) return "redirect:/partidos";
        PartidoDTO p = partidoService.getPartidos().get(index);
        model.addAttribute("index", index);
        model.addAttribute("partido", p);
        model.addAttribute("jugadoresLocal", p.getLocal() != null ? p.getLocal().getJugadores() : new ArrayList<>());
        model.addAttribute("jugadoresVisitante", p.getVisitante() != null ? p.getVisitante().getJugadores() : new ArrayList<>());
        return "partidos_jugar";
    }

    @PostMapping("/{index}/registrar")
    public String registrar(@PathVariable int index,
                            @RequestParam int golesLocal,
                            @RequestParam int golesVisitante,
                            @RequestParam String modo,
                            @RequestParam(required = false) String[] jugadorDni,
                            @RequestParam(required = false) Integer[] goles,
                            @RequestParam(required = false) Integer[] asistencias,
                            @RequestParam(required = false) Integer[] amarillas,
                            @RequestParam(required = false) Integer[] rojas) {

        if (index < 0 || index >= partidoService.getPartidos().size()) return "redirect:/partidos";

        PartidoDTO p = partidoService.getPartidos().get(index);
        p.setGolesLocal(golesLocal);
        p.setGolesVisitante(golesVisitante);

        // Preparar listas defensivas
        List<JugadorDTO> deltasLocal = new ArrayList<>();
        List<JugadorDTO> deltasVisitante = new ArrayList<>();

        if ("avanzado".equalsIgnoreCase(modo) && jugadorDni != null && jugadorDni.length > 0) {
            int len = jugadorDni.length;
            for (int i = 0; i < len; i++) {
                String dni = jugadorDni[i];
                if (dni == null) continue;

                JugadorDTO delta = new JugadorDTO();
                delta.setDni(dni);

                delta.setGoles((goles != null && goles.length > i && goles[i] != null) ? goles[i] : 0);
                delta.setAsistencias((asistencias != null && asistencias.length > i && asistencias[i] != null) ? asistencias[i] : 0);
                delta.setAmarillas((amarillas != null && amarillas.length > i && amarillas[i] != null) ? amarillas[i] : 0);
                delta.setRojas((rojas != null && rojas.length > i && rojas[i] != null) ? rojas[i] : 0);

                boolean esLocal = false;
                if (p.getLocal() != null && p.getLocal().getJugadores() != null) {
                    esLocal = p.getLocal().getJugadores().stream()
                            .anyMatch(j -> j.getDni() != null && j.getDni().equalsIgnoreCase(dni));
                }
                if (esLocal) deltasLocal.add(delta);
                else deltasVisitante.add(delta);
            }

            p.setJugadoresLocalPartido(deltasLocal);
            p.setJugadoresVisitantePartido(deltasVisitante);

            resultadosAvanzadosService.procesarResultados(p);

        } else {
            resultadosService.procesarResultados(p);
        }

        return "redirect:/partidos";
    }

    @GetMapping("/{index}/borrar")
    public String borrar(@PathVariable int index) {
        if (index < 0 || index >= partidoService.getPartidos().size()) return "redirect:/partidos";
        PartidoDTO p = partidoService.getPartidos().get(index);
        partidoService.borrarPartido(p);
        return "redirect:/partidos";
    }
    
    @GetMapping("/test-thymeleaf")
    public String testThymeleaf(Model model) {
        model.addAttribute("msg", "Thymeleaf funciona");
        return "_test_ok";
    }

}
