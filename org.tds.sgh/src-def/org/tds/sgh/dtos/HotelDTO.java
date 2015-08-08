package org.tds.sgh.dtos;

public class HotelDTO
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String nombre;
	
	private String pais;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public HotelDTO(String nombre, String pais)
	{
		this.nombre = nombre;
		
		this.pais = pais;
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
		
		HotelDTO that = (HotelDTO)obj;
		
		if (!this.nombre.equals(that.nombre) ||
			!this.pais.equals(that.pais))
		{
			return false;
		}
		
		return true;
	}	
}
