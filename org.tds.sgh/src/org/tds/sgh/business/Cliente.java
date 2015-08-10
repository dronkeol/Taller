package org.tds.sgh.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente
{
	// Attributes (private) -----------------------------------------------------------------------
	@Id
	@Column(name = "rut")
	private String rut;
	
	private String nombre;
	
	private String direccion;
	
	private String telefono;
	
	private String mail;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public Cliente(String rut, String nombre, String direccion, String telefono, String mail)
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
	
	public String getTelefono() {
		return telefono;
	}

	public String getMail() {
		return mail;
	}

	@Override
	public String toString() {
		return "Cliente [rut=" + rut + ", nombre=" + nombre + "]";
	}
	
}
