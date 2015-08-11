package org.tds.sgh.business;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Habitacion {
	// Attributes (private)
	// -----------------------------------------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade =CascadeType.ALL)
	private TipoHabitacion tipoHabitacion;

	private String nombre;

	// Constructors (public)
	// ----------------------------------------------------------------------

	public Habitacion(TipoHabitacion tipoHabitacion, String nombre) {
		this.tipoHabitacion = tipoHabitacion;

		this.nombre = nombre;
	}

	// Properties (public)
	// ------------------------------------------------------------------------

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
}
