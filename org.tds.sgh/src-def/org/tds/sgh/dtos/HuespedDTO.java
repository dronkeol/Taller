package org.tds.sgh.dtos;

public class HuespedDTO
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String nombre;
	
	private String documento;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public HuespedDTO(String nombre, String documento)
	{
		this.nombre = nombre;
		
		this.documento = documento;
	}

	
	// Properties (public) ------------------------------------------------------------------------
	
	public String getNombre()
	{
		return nombre;
	}
	
	public String getDocumento()
	{
		return documento;
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
		
		HuespedDTO that = (HuespedDTO)obj;
		
		if (!this.nombre.equals(that.nombre) ||
			!this.documento.equals(that.documento))
		{
			return false;
		}
		
		return true;
	}	
}
