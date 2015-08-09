package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class ModificarReservaController extends BaseController implements
    IModificarReservaController {

  public ModificarReservaController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }


  @Override
  public List<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
    return DTO.getInstance().mapHoteles(
        super.getCadenaHotelera().sugerirAlternativas(pais, nombreTipoHabitacion, fechaInicio,
            fechaFin));
  }

  public List<ReservaDTO> buscarReservasDelCliente() {
	  return DTO.getInstance().mapReservas(super.getCadenaHotelera().
			  buscarReservasDelCliente(super.getReserva().getHotel(), super.getCliente()));
    
  }

  @Override
  public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
	 
	  return DTO.getInstance().map(super.getCadenaHotelera().seleccionarReserva(codigoReserva));
		  
  }

  @Override
  public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
    return super.getCadenaHotelera().confirmaDisponibilidad(nombreHotel, nombreTipoHabitacion,
        fechaInicio, fechaFin);
  }

  @Override
  public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }


}
