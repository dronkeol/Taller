package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;



public class HacerReservaController extends TomarReservaController implements
    IHacerReservaController {

  private CadenaHotelera cadenaHotelera;

  public HacerReservaController(CadenaHotelera cadenaHotelera) {
    this.cadenaHotelera = cadenaHotelera;
  }
}
