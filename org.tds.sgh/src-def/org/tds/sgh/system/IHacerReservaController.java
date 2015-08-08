package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.List;

import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;

public interface IHacerReservaController extends IIdentificarClienteEnRecepcionController,
									   IAltaClienteController
{
	// Operations ---------------------------------------------------------------------------------
	
	boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception;

	List<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception;
	
	ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception;
	
		
	// Extensions ---------------------------------------------------------------------------------
	
	default boolean confirmarDisponibilidad(HotelDTO hotel, TipoHabitacionDTO tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception
	{
		return confirmarDisponibilidad(hotel.getNombre(), tipoHabitacion.getNombre(), fechaInicio, fechaFin);
	}
	
	default List<HotelDTO> sugerirAlternativas(String pais, TipoHabitacionDTO tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception
	{
		return sugerirAlternativas(pais, tipoHabitacion.getNombre(), fechaInicio, fechaFin);
	}
	
	default ReservaDTO registrarReserva(HotelDTO hotel, TipoHabitacionDTO tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception
	{
		return registrarReserva(hotel.getNombre(), tipoHabitacion.getNombre(), fechaInicio, fechaFin, modificablePorHuesped);
	}
}
