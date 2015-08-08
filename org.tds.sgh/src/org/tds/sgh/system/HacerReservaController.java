package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;



public class HacerReservaController extends TomarReservaController implements
    IAltaClienteController, IIdentificarClienteEnRecepcionController, IHacerReservaController {


  public HacerReservaController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }
}
