package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import com.example.demo.model.PartidoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.services.EquipoService;
import com.example.demo.services.PartidoService;
import com.example.demo.services.ResultadosService;

@Controller
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private EquipoService equipoService;

    // ACTIVAMOS SERVICIO AVANZADO
    @Autowired
    @Qualifier("resultadosAvanzadosService")
    private ResultadosService resultadosService;

    // LISTA DE PARTIDOS
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("partidos", partidoService.getPartidos());
        return "lista_partidos";
    }

    // FORMULARIO PARA CREAR PARTIDO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("partido", new PartidoDTO());
        model.addAttribute("equipos", equipoService.getEquipos());
        return "formulario_partidos";
    }

    // GUARDAR PARTIDO PROGRAMADO
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PartidoDTO partido) {
        partidoService.programarPartido(partido);
        return "redirect:/partidos";
    }

    // FORMULARIO PARA JUGAR PARTIDO
    @GetMapping("/{index}/jugar")
    public String jugar(@PathVariable int index, Model model) {

        PartidoDTO partido = partidoService.getPartidos().get(index);

        // Se inicializan las listas de jugadores para el formulario con los jugadores actuales del equipo
        partido.setJugadoresLocalPartido(
            partido.getLocal().getJugadores().stream()
                .map(j -> new JugadorDTO(j.getNombre(), j.getDni(), j.getEdad(), j.getPosicion(), 0, 0, 0, 0, j.getValorMercado()))
                .collect(Collectors.toList())
        );
        partido.setJugadoresVisitantePartido(
            partido.getVisitante().getJugadores().stream()
                .map(j -> new JugadorDTO(j.getNombre(), j.getDni(), j.getEdad(), j.getPosicion(), 0, 0, 0, 0, j.getValorMercado()))
                .collect(Collectors.toList())
        );

        model.addAttribute("partido", partido);
        model.addAttribute("index", index); // Para usar en la acción del formulario
        return "partidos_jugar";
    }

    // PROCESAR RESULTADO DEL PARTIDO
    @PostMapping("/{index}/procesar")
    public String procesar(@PathVariable int index, @ModelAttribute PartidoDTO resultado) {

        PartidoDTO partido = partidoService.getPartidos().get(index);

        // ACTUALIZAR GOLES del Partido DTO
        partido.setGolesLocal(resultado.getGolesLocal());
        partido.setGolesVisitante(resultado.getGolesVisitante());
        partido.setJugado(true);

        // Copiar estadísticas por jugador desde el formulario
        // OJO: Los jugadores vienen con las estadísticas del partido, pero con los datos base (nombre, dni, etc.)
        partido.setJugadoresLocalPartido(resultado.getJugadoresLocalPartido());
        partido.setJugadoresVisitantePartido(resultado.getJugadoresVisitantePartido());

        // PROCESAR RESULTADOS (normal o avanzado)
        resultadosService.procesarResultados(partido);

        return "redirect:/partidos";
    }
}