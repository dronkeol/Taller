package org.tds.sgh.business;

public class TipoHabitacion
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String nombre;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public TipoHabitacion(String nombre)
	{
		this.nombre = nombre;
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public String getNombre()
	{
		return nombre;
	}
}
