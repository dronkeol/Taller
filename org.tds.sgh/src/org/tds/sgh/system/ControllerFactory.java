package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.infrastructure.NotImplementedException;

public class ControllerFactory implements IControllerFactory {
  // Attributes (private) -----------------------------------------------------------------------

  private CadenaHotelera cadenaHotelera;


  // Constructors (public) ----------------------------------------------------------------------

  public ControllerFactory(CadenaHotelera cadenaHotelera) {
    this.cadenaHotelera = cadenaHotelera;
  }


  // Operations (public) ------------------------------------------------------------------------

  @Override
  public ICadenaController createCadenaController() {
    return new CadenaController(cadenaHotelera);
  }

  @Override
  public IHacerReservaController createHacerReservaController() {
    return new HacerReservaController(cadenaHotelera);
  }

  @Override
  public IModificarReservaController createModificarReservaController() {
    // TODO
    throw new NotImplementedException();
  }

  @Override
  public ICancelarReservaController createCancelarReservaController() {
    // TODO
    throw new NotImplementedException();
  }

  public ITomarReservaController createTomarReservaController() {
    // TODO
	  //return new TomarReservaController();
	  return new TomarReservaController(cadenaHotelera);
    //throw new NotImplementedException();
  }
}
