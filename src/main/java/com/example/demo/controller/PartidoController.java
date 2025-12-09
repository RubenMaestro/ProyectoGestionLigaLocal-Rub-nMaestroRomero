package com.example.demo.controller;

import com.example.demo.model.EquipoDTO;
import com.example.demo.model.JugadorDTO;
import com.example.demo.model.PartidoDTO;
import com.example.demo.services.EquipoService;
import com.example.demo.services.PartidoService;
import com.example.demo.services.impl.ResultadosAvanzadosService;
import com.example.demo.services.impl.ResultadosNormalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/partidos")
public class PartidoController {

    private final PartidoService partidoService;
    private final EquipoService equipoService;
    private final ResultadosNormalesService resultadosNormalesService;
    private final ResultadosAvanzadosService resultadosAvanzadosService;

    public PartidoController(PartidoService partidoService,
                             EquipoService equipoService,
                             ResultadosNormalesService resultadosNormalesService,
                             ResultadosAvanzadosService resultadosAvanzadosService) {
        this.partidoService = partidoService;
        this.equipoService = equipoService;
        this.resultadosNormalesService = resultadosNormalesService;
        this.resultadosAvanzadosService = resultadosAvanzadosService;
    }

    // LISTAR PARTIDOS
    @GetMapping
    public String listarPartidos(Model model) {
        model.addAttribute("partidos", partidoService.getPartidos());
        return "lista_partidos";
    }

    // FORMULARIO NUEVO PARTIDO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoPartido(Model model) {
        model.addAttribute("partido", new PartidoDTO());

        try {
            model.addAttribute("equipos", equipoService.getEquipos());
        } catch (Exception ex) {
            model.addAttribute("equipos", List.of());
        }

        return "formulario_partidos";
    }

    @PostMapping("/crear")
    public String crearPartido(PartidoDTO partido,
                               @RequestParam String localNombre,
                               @RequestParam String visitanteNombre) {

        partidoService.crearPartido(partido, localNombre, visitanteNombre);
        return "redirect:/partidos";
    }

    // GET para abrir pantalla de jugar
    @GetMapping("/{idx}/jugar")
    public String jugarPartido(@PathVariable int idx, Model model) {

        PartidoDTO partido = partidoService.obtenerPartido(idx);
        if (partido == null) return "redirect:/partidos";

        List<JugadorDTO> jugadoresLocal =
                (partido.getLocal() != null && partido.getLocal().getJugadores() != null)
                        ? partido.getLocal().getJugadores()
                        : List.of();

        List<JugadorDTO> jugadoresVisitante =
                (partido.getVisitante() != null && partido.getVisitante().getJugadores() != null)
                        ? partido.getVisitante().getJugadores()
                        : List.of();

        model.addAttribute("partido", partido);
        model.addAttribute("jugadoresLocal", jugadoresLocal);
        model.addAttribute("jugadoresVisitante", jugadoresVisitante);
        model.addAttribute("index", idx);

        return "partidos_jugar";
    }

    // =============================
    //        REGISTRAR RESULTADO
    // =============================
    @PostMapping("/{idx}/registrar")
    public String registrarResultado(
            @PathVariable int idx,
            @RequestParam int golesLocal,
            @RequestParam int golesVisitante,
            @RequestParam(required = false, defaultValue = "normal") String modo,

            @RequestParam(required = false) List<String> idLocalJugadores,
            @RequestParam(required = false) List<Integer> golesLocalJugadores,
            @RequestParam(required = false) List<Integer> asistenciasLocalJugadores,
            @RequestParam(required = false) List<Integer> amarillasLocalJugadores,
            @RequestParam(required = false) List<Integer> rojasLocalJugadores,

            @RequestParam(required = false) List<String> idVisitanteJugadores,
            @RequestParam(required = false) List<Integer> golesVisitanteJugadores,
            @RequestParam(required = false) List<Integer> asistenciasVisitanteJugadores,
            @RequestParam(required = false) List<Integer> amarillasVisitanteJugadores,
            @RequestParam(required = false) List<Integer> rojasVisitanteJugadores
    ) {

        PartidoDTO partido = partidoService.obtenerPartido(idx);
        if (partido == null) return "redirect:/partidos";

        // Actualizar marcador
        partido.setGolesLocal(golesLocal);
        partido.setGolesVisitante(golesVisitante);
        partido.setJugado(true);

        // ======================
        //     MODO NORMAL
        // ======================
        if ("normal".equalsIgnoreCase(modo)) {
            resultadosNormalesService.calcular(partido);
            return "redirect:/partidos";
        }

        // ======================
        //     MODO AVANZADAS
        // ======================
        if ("avanzadas".equalsIgnoreCase(modo)) {

            // ----------- JUGADORES LOCAL ------------
            if (idLocalJugadores != null) {
                EquipoDTO equipoLocal = partido.getLocal();
                for (int i = 0; i < idLocalJugadores.size(); i++) {

                    String id = idLocalJugadores.get(i);

                    for (JugadorDTO j : equipoLocal.getJugadores()) {
                        if (id.equals(j.getNombre())) {

                            j.setGoles(j.getGoles() + safeInt(golesLocalJugadores, i));
                            j.setAsistencias(j.getAsistencias() + safeInt(asistenciasLocalJugadores, i));
                            j.setAmarillas(j.getAmarillas() + safeInt(amarillasLocalJugadores, i));
                            j.setRojas(j.getRojas() + safeInt(rojasLocalJugadores, i));
                            break;
                        }
                    }
                }
            }

            // ----------- JUGADORES VISITANTE ------------
            if (idVisitanteJugadores != null) {
                EquipoDTO equipoVisit = partido.getVisitante();
                for (int i = 0; i < idVisitanteJugadores.size(); i++) {

                    String id = idVisitanteJugadores.get(i);

                    for (JugadorDTO j : equipoVisit.getJugadores()) {
                        if (id.equals(j.getNombre())) {

                            j.setGoles(j.getGoles() + safeInt(golesVisitanteJugadores, i));
                            j.setAsistencias(j.getAsistencias() + safeInt(asistenciasVisitanteJugadores, i));
                            j.setAmarillas(j.getAmarillas() + safeInt(amarillasVisitanteJugadores, i));
                            j.setRojas(j.getRojas() + safeInt(rojasVisitanteJugadores, i));
                            break;
                        }
                    }
                }
            }

            // cálculos especiales
            resultadosAvanzadosService.calcular(partido);

            // 🔥🔥🔥 MUY IMPORTANTE:
            // ACTUALIZAR TAMBIÉN LA CLASIFICACIÓN
            resultadosNormalesService.calcular(partido);
        }

        return "redirect:/partidos";
    }

    @GetMapping("/{idx}/eliminar")
    public String eliminar(@PathVariable int idx) {
        partidoService.eliminarPartido(idx);
        return "redirect:/partidos";
    }

    // Evitar null al sumar estadísticas
    private int safeInt(List<Integer> lista, int index) {
        if (lista == null || index >= lista.size() || lista.get(index) == null)
            return 0;
        return lista.get(index);
    }
}
