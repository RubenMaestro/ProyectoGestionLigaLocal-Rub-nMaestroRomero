package com.example.demo.services;

import java.util.List;

import com.example.demo.model.JugadorDTO;

public interface JugadorService {
	void crearJugador(JugadorDTO jugador);
	List<JugadorDTO> getJugadores();
	
}