package org.tds.sgh.dtos;

public class ClienteDTO
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private String rut;
	
	private String nombre;
	
	private String direccion;
	
	private String telefono;
	
	private String mail;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public ClienteDTO(String rut, String nombre, String direccion, String telefono, String mail)
	{
		this.rut = rut;
		
		this.nombre = nombre;
		
		this.direccion = direccion;
		
		this.telefono = telefono;
		
		this.mail = mail;
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public String getRut()
	{
		return rut;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public String getDireccion()
	{
		return direccion;
	}
	
	public String getTelefono()
	{
		return telefono;
	}
	
	public String getMail()
	{
		return mail;
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
		
		ClienteDTO that = (ClienteDTO)obj;
		
		if (!this.rut.equals(that.rut) ||
			!this.nombre.equals(that.nombre) ||
			!this.direccion.equals(that.direccion) ||
			!this.telefono.equals(that.telefono)||
			!this.mail.equals(that.mail))
		{
			return false;
		}
		
		return true;
	}	
}
