package com.example.demo.services;

import java.util.List;

import com.example.demo.model.PartidoDTO;

public interface PartidoService {
	void programarPartido(PartidoDTO partido);
	List<PartidoDTO> getPartidos();
}