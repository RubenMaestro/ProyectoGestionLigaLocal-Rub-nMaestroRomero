package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.JugadorDTO;
import com.example.demo.services.JugadorService;

@Service
public class JugadorServiceImpl implements JugadorService {
	
	private final List<JugadorDTO> jugadores = new ArrayList<>();

	// Crear un jugador solo si tiene al menos 16 años
	@Override
	public void crearJugador(JugadorDTO jugador) {
	    if (jugador.getEdad() <= 16) {
	        throw new IllegalArgumentException("El jugador debe ser mayor de 16 años");
	    }
	    jugadores.add(jugador);
	}

	
	// Devuelve la lista de jugadores
	@Override
	public List<JugadorDTO> getJugadores() {
		return jugadores;
	}
}