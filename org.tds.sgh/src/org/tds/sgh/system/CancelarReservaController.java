package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class CancelarReservaController extends TomarReservaController implements
    ICancelarReservaController {

  public CancelarReservaController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }

  @Override
  public ReservaDTO cancelarReservaDelCliente() throws Exception {
	  Reserva reserva = super.getCadenaHotelera().cancelarReserva(getReserva());
	  Infrastructure.getInstance().getSistemaMensajeria().enviarMail(super.getReserva().getCliente().getMail(), "",
				"");
		return DTO.getInstance().map(reserva);
  }

}
