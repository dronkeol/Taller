package org.tds.sgh.dtos;

public class HabitacionDTO
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String hotel;
	
	private String tipoHabitacion;
	
	private String nombre;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public HabitacionDTO(String hotel, String tipoHabitacion, String nombre)
	{
		this.hotel = hotel;
		
		this.tipoHabitacion = tipoHabitacion;
		
		this.nombre = nombre;
	}
	
	
	// Properties (public) ------------------------------------------------------------------------

	public String getHotel()
	{
		return hotel;
	}
	
	public String getTipoHabitacion()
	{
		return tipoHabitacion;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	

	// Operations (public) ------------------------------------------------------------------------
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		
		HabitacionDTO that = (HabitacionDTO)obj;
		
		if (!this.hotel.equals(that.hotel) ||
			!this.tipoHabitacion.equals(that.tipoHabitacion) ||
			!this.nombre.equals(that.nombre))
		{
			return false;
		}
		
		return true;
	}	
}
