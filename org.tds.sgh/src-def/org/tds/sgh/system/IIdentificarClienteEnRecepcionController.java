package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.dtos.ClienteDTO;

public interface IIdentificarClienteEnRecepcionController
{
	// Operations ---------------------------------------------------------------------------------
	
	List<ClienteDTO> buscarCliente(String patronNombreCliente);
	
	ClienteDTO seleccionarCliente(String rut) throws Exception;
	
	
	// Extensions ---------------------------------------------------------------------------------
	
	default ClienteDTO seleccionarCliente(ClienteDTO cliente) throws Exception
	{
		return seleccionarCliente(cliente.getRut());
	}
}
