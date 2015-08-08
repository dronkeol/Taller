package org.tds.sgh.system;

import org.tds.sgh.dtos.ClienteDTO;

public interface IAltaClienteController
{
	// Operations ---------------------------------------------------------------------------------
	
	ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono, String mail) throws Exception;
	
	
	// Extensions ---------------------------------------------------------------------------------
	
	default ClienteDTO registrarCliente(ClienteDTO cliente) throws Exception
	{
		return registrarCliente(cliente.getRut(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), cliente.getMail());
	}
}
