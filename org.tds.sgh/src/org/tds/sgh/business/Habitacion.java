package org.tds.sgh.business;

public class Habitacion
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private TipoHabitacion tipoHabitacion;
	
	private String nombre;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public Habitacion(TipoHabitacion tipoHabitacion, String nombre)
	{
		this.tipoHabitacion = tipoHabitacion;
		
		this.nombre = nombre;
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public TipoHabitacion getTipoHabitacion()
	{
		return tipoHabitacion;
	}
	
	public String getNombre()
	{
		return nombre;
	}
}
