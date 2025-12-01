package com.example.demo.model;

public class JugadorDTO {
	private String nombre;
	private String dni;
	private int edad;
	private String posicion;
	
	private int goles;
	private int asistencias;
	private int amarillas;
	private int rojas;
	
	private double valorMercado;

	public JugadorDTO() {
		super();
	}

	public JugadorDTO(String nombre, String dni, int edad, String posicion, int goles, int asistencias, int amarillas,
			int rojas, double valorMercado) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.edad = edad;
		this.posicion = posicion;
		this.goles = goles;
		this.asistencias = asistencias;
		this.amarillas = amarillas;
		this.rojas = rojas;
		this.valorMercado = valorMercado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public int getGoles() {
		return goles;
	}

	public void setGoles(int goles) {
		this.goles = goles;
	}

	public int getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}

	public int getAmarillas() {
		return amarillas;
	}

	public void setAmarillas(int amarillas) {
		this.amarillas = amarillas;
	}

	public int getRojas() {
		return rojas;
	}

	public void setRojas(int rojas) {
		this.rojas = rojas;
	}

	public double getValorMercado() {
		return valorMercado;
	}

	public void setValorMercado(double valorMercado) {
		this.valorMercado = valorMercado;
	}
}