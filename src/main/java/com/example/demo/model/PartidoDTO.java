package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

public class PartidoDTO {

    private LocalDate fecha;
    private EquipoDTO local;
    private EquipoDTO visitante;

    private int golesLocal;
    private int golesVisitante;

    private boolean jugado;

    private List<JugadorDTO> jugadoresLocalPartido;
    private List<JugadorDTO> jugadoresVisitantePartido;

    public PartidoDTO() {}

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EquipoDTO getLocal() {
        return local;
    }
    public void setLocal(EquipoDTO local) {
        this.local = local;
    }

    public EquipoDTO getVisitante() {
        return visitante;
    }
    public void setVisitante(EquipoDTO visitante) {
        this.visitante = visitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }
    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }
    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public boolean isJugado() {
        return jugado;
    }
    public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }

    public List<JugadorDTO> getJugadoresLocalPartido() {
        return jugadoresLocalPartido;
    }
    public void setJugadoresLocalPartido(List<JugadorDTO> jugadoresLocalPartido) {
        this.jugadoresLocalPartido = jugadoresLocalPartido;
    }

    public List<JugadorDTO> getJugadoresVisitantePartido() {
        return jugadoresVisitantePartido;
    }
    public void setJugadoresVisitantePartido(List<JugadorDTO> jugadoresVisitantePartido) {
        this.jugadoresVisitantePartido = jugadoresVisitantePartido;
    }
}
