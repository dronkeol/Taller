package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;

public interface ICadenaController
{
	// Properties ---------------------------------------------------------------------------------

	List<ClienteDTO> getClientes();
	
	List<HabitacionDTO> getHabitaciones(String nombreHotel) throws Exception;

	List<HotelDTO> getHoteles();
	
	List<TipoHabitacionDTO> getTiposHabitacion();

	
	// Operations ---------------------------------------------------------------------------------
	
	ClienteDTO agregarCliente(String rut, String nombre, String direccion, String telefono, String mail) throws Exception;
	
	HabitacionDTO agregarHabitacion(String nombreHotel, String nombreTipoHabitacion, String nombre) throws Exception;
	
	HotelDTO agregarHotel(String nombre, String pais) throws Exception;
	
	TipoHabitacionDTO agregarTipoHabitacion(String nombre) throws Exception;
	
	
	// Extensions ---------------------------------------------------------------------------------
	
	default ClienteDTO agregarCliente(ClienteDTO cliente) throws Exception
	{
		return agregarCliente(cliente.getRut(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), cliente.getMail());
	}
	
	default HabitacionDTO agregarHabitacion(HabitacionDTO habitacion) throws Exception
	{
		return agregarHabitacion(habitacion.getHotel(), habitacion.getTipoHabitacion(), habitacion.getNombre());
	}
	
	default HotelDTO agregarHotel(HotelDTO hotel) throws Exception
	{
		return agregarHotel(hotel.getNombre(), hotel.getPais());
	}
	
	default TipoHabitacionDTO agregarTipoHabitacion(TipoHabitacionDTO tipoHabitacion) throws Exception
	{
		return agregarTipoHabitacion(tipoHabitacion.getNombre());
	}
	
	default List<HabitacionDTO> getHabitaciones(HotelDTO hotel) throws Exception
	{
		return getHabitaciones(hotel.getNombre());
	}
}
