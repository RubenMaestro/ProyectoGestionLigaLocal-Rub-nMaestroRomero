package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.PartidoDTO;
import com.example.demo.services.PartidoService;

@Service
public class PartidoServiceImpl implements PartidoService {
	
	private final List<PartidoDTO> partidos = new ArrayList<>();
	
	// Programar un partido
	@Override
	public void programarPartido(PartidoDTO partido) {
		// Asegura que el partido se crea como "pendiente de jugarse"
		partido.setJugado(false); 
		partidos.add(partido);
	}
	
	// Devuelve la lista de partidos
	@Override
	public List<PartidoDTO> getPartidos() {
		return partidos;
	}
	

}