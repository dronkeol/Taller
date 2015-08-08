package org.tds.sgh.test.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.ISistemaFacturacion;

public class SistemaFacturacionStub implements ISistemaFacturacion
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private List<ReservaDTO> reservas;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public SistemaFacturacionStub()
	{
		reservas = new ArrayList<ReservaDTO>();
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public List<ReservaDTO> getReservas()
	{
		return Collections.unmodifiableList(reservas);
	}

	
	// Operations (public) ------------------------------------------------------------------------
	
	@Override
	public void iniciarEstadia(ReservaDTO reserva)
	{
		reservas.add(reserva);
	}
}
