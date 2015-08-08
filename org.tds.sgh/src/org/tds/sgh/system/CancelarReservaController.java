package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.dtos.ReservaDTO;

public class CancelarReservaController extends TomarReservaController implements
    ICancelarReservaController {

  public CancelarReservaController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }

  @Override
  public ReservaDTO cancelarReservaDelCliente() throws Exception {
    return null;
  }
}
