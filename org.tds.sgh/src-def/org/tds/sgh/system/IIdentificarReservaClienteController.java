package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.dtos.ReservaDTO;

public interface IIdentificarReservaClienteController extends IIdentificarClienteEnRecepcionController
{
	// Operations ---------------------------------------------------------------------------------
	
	List<ReservaDTO> buscarReservasDelCliente() throws Exception;
	
	ReservaDTO seleccionarReserva(long codigoReserva) throws Exception;
	
	
	// Extensions ---------------------------------------------------------------------------------
	
	default ReservaDTO seleccionarReserva(ReservaDTO reserva) throws Exception
	{
		return seleccionarReserva(reserva.getCodigo());
	}
}
