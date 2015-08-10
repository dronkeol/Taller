package org.tds.sgh.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Habitacion
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private TipoHabitacion tipoHabitacion;
	
	@Id
	@Column(name = "nombre")
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
