package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class EquipoDTO {
    private String nombre;
    private String entrenador;
    private List<JugadorDTO> jugadores = new ArrayList<>();

    private int victorias;
    private int derrotas;
    private int empates;

    private int golesFavor;
    private int golesContra;
    private int puntos;

    public EquipoDTO() {
        super();
    }

    public EquipoDTO(String nombre, String entrenador, List<JugadorDTO> jugadores, int victorias, int derrotas,
                     int empates, int golesFavor, int golesContra, int puntos) {
        super();
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.jugadores = jugadores != null ? jugadores : new ArrayList<>();
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.empates = empates;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}