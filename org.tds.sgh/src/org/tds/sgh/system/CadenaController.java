package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Habitacion;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.TipoHabitacion;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;

public class CadenaController implements ICadenaController
{
	// Attributes (dependencies) ------------------------------------------------------------------
	
	private final DTO DTO = org.tds.sgh.dtos.DTO.getInstance();
	
	
	// Attributes (private) -----------------------------------------------------------------------
	
	private CadenaHotelera cadenaHotelera;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public CadenaController(CadenaHotelera cadenaHotelera)
	{
		this.cadenaHotelera = cadenaHotelera;
	}
	

	// Operations (public) ------------------------------------------------------------------------

	@Override
	public ClienteDTO agregarCliente(String rut, String nombre, String direccion, String telefono, String mail) throws Exception
	{
		Cliente cliente = cadenaHotelera.agregarCliente(rut, nombre, direccion, telefono, mail);
		
		return DTO.map(cliente);
	}

	@Override
	public HabitacionDTO agregarHabitacion(String nombreHotel, String nombreTipoHabitacion, String nombre) throws Exception
	{
		Hotel hotel = cadenaHotelera.buscarHotel(nombreHotel);
		
		TipoHabitacion tipoHabitacion = cadenaHotelera.buscarTipoHabitacion(nombreTipoHabitacion);
		
		Habitacion habitacion = hotel.agregarHabitacion(tipoHabitacion, nombre);
		
		return DTO.map(hotel, habitacion);
	}

	@Override
	public HotelDTO agregarHotel(String nombre, String pais) throws Exception
	{
		Hotel hotel = cadenaHotelera.agregarHotel(nombre, pais);
		
		return DTO.map(hotel);
	}

	@Override
	public TipoHabitacionDTO agregarTipoHabitacion(String nombre) throws Exception
	{
		TipoHabitacion tipoHabitacion = cadenaHotelera.agregarTipoHabitacion(nombre);
		
		return DTO.map(tipoHabitacion);
	}

	@Override
	public List<ClienteDTO> getClientes()
	{
		return DTO.mapClientes(cadenaHotelera.listarClientes());
	}
	
	@Override
	public List<HabitacionDTO> getHabitaciones(String nombreHotel) throws Exception
	{
		Hotel hotel = cadenaHotelera.buscarHotel(nombreHotel);
		
		return DTO.mapHabitaciones(hotel, hotel.listarHabitaciones());
	}
	
	@Override
	public List<HotelDTO> getHoteles()
	{
		return DTO.mapHoteles(cadenaHotelera.listarHoteles());
	}
	
	@Override
	public List<TipoHabitacionDTO> getTiposHabitacion()
	{
		return DTO.mapTiposHabitacion(cadenaHotelera.listarTiposHabitacion());
	}
}
