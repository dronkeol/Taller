package org.tds.sgh.dtos;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Habitacion;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.TipoHabitacion;

public class DTO
{
	// Attributes (static private) ----------------------------------------------------------------
	
	private static final DTO Instance = new DTO();
	
	
	// Operations (static public) -----------------------------------------------------------------
	
	public static DTO getInstance()
	{
		return Instance;
	}
	
	
	// Constructors (private) ---------------------------------------------------------------------
	
	private DTO()
	{
	}

	
	// Operations (public) ------------------------------------------------------------------------
	
	public ClienteDTO map(Cliente cliente)
	{
		return new ClienteDTO(
			cliente.getRut(),
			cliente.getNombre(),
			cliente.getDireccion(),
			cliente.getTelefono(),
			cliente.getMail()
		);
	}
	
	public List<ClienteDTO> mapClientes(Stream<Cliente> clientes)
	{
		return clientes.map(cliente -> map(cliente)).collect(Collectors.toList());
	}
	
	public HotelDTO map(Hotel hotel)
	{
		return new HotelDTO(hotel.getNombre(), hotel.getPais());
	}

	public List<HotelDTO> mapHoteles(Stream<Hotel> hoteles)
	{
		return hoteles.map(hotel -> map(hotel)).collect(Collectors.toList());
	}

	public HabitacionDTO map(Hotel hotel, Habitacion habitacion)
	{
		return new HabitacionDTO(hotel.getNombre(), habitacion.getTipoHabitacion().getNombre(), habitacion.getNombre());
	}
	
	public List<HabitacionDTO> mapHabitaciones(Hotel hotel, Stream<Habitacion> habitaciones)
	{
		return habitaciones.map(habitacion -> map(hotel, habitacion)).collect(Collectors.toList());
	}
	
	public TipoHabitacionDTO map(TipoHabitacion tipoHabitation)
	{
		return new TipoHabitacionDTO(tipoHabitation.getNombre());
	}
	
	public List<TipoHabitacionDTO> mapTiposHabitacion(Stream<TipoHabitacion> tiposHabitacion)
	{
		return tiposHabitacion.map(tipoHabitacion -> map(tipoHabitacion)).collect(Collectors.toList());
	}
}
