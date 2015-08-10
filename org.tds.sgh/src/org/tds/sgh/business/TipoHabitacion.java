package org.tds.sgh.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TipoHabitacion {
	// Attributes (private)
	// -----------------------------------------------------------------------
	@Id
	@Column(name = "nombre")
	private String nombre;

	// Constructors (public)
	// ----------------------------------------------------------------------

	public TipoHabitacion(String nombre) {
		this.nombre = nombre;
	}

	// Properties (public)
	// ------------------------------------------------------------------------

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "TipoHabitacion [nombre=" + nombre + "]";
	}
	
	
}
