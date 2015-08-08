package org.tds.sgh.business;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Hotel
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String nombre;
	
	private String pais;
	
	private Map<String,Habitacion> habitaciones;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public Hotel(String nombre, String pais)
	{
		this.nombre = nombre;
		
		this.pais = pais;
		
		this.habitaciones = new HashMap<String,Habitacion>();
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public String getNombre()
	{
		return nombre;
	}
	
	public String getPais()
	{
		return pais;
	}
	
	
	// Operations (public) ------------------------------------------------------------------------
	
	public Habitacion agregarHabitacion(TipoHabitacion tipoHabitacion, String nombre) throws Exception
	{
		if (habitaciones.containsKey(nombre)) throw new Exception("El hotel ya tiene una habitación con el nombre indicado.");
		
		Habitacion habitacion = new Habitacion(tipoHabitacion, nombre);
		
		habitaciones.put(habitacion.getNombre(), habitacion);
		
		return habitacion;
	}
	
	public Stream<Habitacion> listarHabitaciones()
	{
		return habitaciones.values().stream();
	}
}
