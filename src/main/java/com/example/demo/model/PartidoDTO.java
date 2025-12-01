package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartidoDTO {
    
    private EquipoDTO local;
    private EquipoDTO visitante;
    
    private LocalDate fecha;
    private String lugar;
    
    private boolean jugado;
    private int golesLocal;
    private int golesVisitante;

    // Se usa para recoger las estadísticas por jugador del formulario de "jugar partido"
    private List<JugadorDTO> jugadoresLocalPartido = new ArrayList<>();
    private List<JugadorDTO> jugadoresVisitantePartido = new ArrayList<>();

    public PartidoDTO() {
        super();
    }

    public PartidoDTO(EquipoDTO local, EquipoDTO visitante, LocalDate fecha, String lugar, boolean jugado,
            int golesLocal, int golesVisitante) {
        super();
        this.local = local;
        this.visitante = visitante;
        this.fecha = fecha;
        this.lugar = lugar;
        this.jugado = jugado;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public boolean isJugado() {
        return jugado;
    }

    public void setJugado(boolean jugado) {
        this.jugado = jugado;
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