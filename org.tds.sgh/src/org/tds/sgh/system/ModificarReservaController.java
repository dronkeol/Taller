package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class ModificarReservaController extends BaseController implements IModificarReservaController {

	public ModificarReservaController(CadenaHotelera cadenaHotelera) {
		super(cadenaHotelera);
	}

	@Override
	public List<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		return DTO.getInstance().mapHoteles(
				super.getCadenaHotelera().sugerirAlternativas(pais, nombreTipoHabitacion, fechaInicio, fechaFin));
	}

	public List<ReservaDTO> buscarReservasDelCliente() throws Exception{
		return DTO.getInstance().mapReservas(super.getCadenaHotelera().buscarReservasDelCliente(super.getCliente()));

	}

	@Override
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
		Reserva reserva = super.getCadenaHotelera().seleccionarReserva(super.getCliente(),codigoReserva);
		super.setReserva(reserva);
		return DTO.getInstance().map(reserva);
	}

	@Override
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		return super.getCadenaHotelera().confirmaDisponibilidad(nombreHotel, nombreTipoHabitacion, fechaInicio,
				fechaFin);
	}

	@Override
	public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		Reserva reserva = super.getCadenaHotelera().modificarReserva(super.getReserva(), nombreHotel,
				nombreTipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(super.getReserva().getCliente().getMail(), "",
				"");
		return DTO.getInstance().map(reserva);
	}

}
