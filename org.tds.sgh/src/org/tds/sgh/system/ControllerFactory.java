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
    return new ModificarReservaController(cadenaHotelera);
  }

  @Override
  public ICancelarReservaController createCancelarReservaController() {
	  return new CancelarReservaController(cadenaHotelera);
  }

  public ITomarReservaController createTomarReservaController() {
	  return new TomarReservaController(cadenaHotelera);
  }
}
