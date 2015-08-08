package org.tds.sgh.dtos;

public class TipoHabitacionDTO
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String nombre;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public TipoHabitacionDTO(String nombre)
	{
		this.nombre = nombre;
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public String getNombre()
	{
		return nombre;
	}
	
	
	// Operations (public) -----------------------------------------------------------------------
	
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
		
		TipoHabitacionDTO that = (TipoHabitacionDTO)obj;
		
		if (!this.nombre.equals(that.nombre))
		{
			return false;
		}
		
		return true;
	}	
}
