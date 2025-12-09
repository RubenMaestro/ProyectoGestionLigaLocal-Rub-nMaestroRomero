package com.example.demo.model;

public class PartidoDTO {

    private String fecha;
    private String lugar;

    // EquipoDTO no se define aquí, se asume que existe en tu paquete model.
    private EquipoDTO local;
    private EquipoDTO visitante;

    private Integer golesLocal;
    private Integer golesVisitante;

    private boolean jugado;

    public PartidoDTO() {
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.jugado = false;
    }

    // Fecha / Lugar
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // Equipos
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

    // Goles
    public Integer getGolesLocal() {
        return golesLocal;
    }
    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }
    public Integer getGolesVisitante() {
        return golesVisitante;
    }
    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    // Jugado
    public boolean isJugado() {
        return jugado;
    }
    public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }
}
