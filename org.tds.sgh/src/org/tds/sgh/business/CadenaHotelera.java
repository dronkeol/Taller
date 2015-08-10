package org.tds.sgh.business;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.tds.sgh.infrastructure.Infrastructure;

@Entity
public class CadenaHotelera {
	// Attributes (private)
	// -----------------------------------------------------------------------

	@Id
	@Column(name = "nombre")
	private String nombre;

	private Map<String, Cliente> clientes;

	private Map<String, Hotel> hoteles;

	private Map<String, TipoHabitacion> tiposHabitacion;

	public CadenaHotelera(String nombre) {
		this.nombre = nombre;

		this.clientes = new HashMap<String, Cliente>();

		this.hoteles = new HashMap<String, Hotel>();

		this.tiposHabitacion = new HashMap<String, TipoHabitacion>();
	}

	// Properties (public)
	// ------------------------------------------------------------------------

	public String getNombre() {
		return nombre;
	}

	// Operations (public)
	// ------------------------------------------------------------------------

	public Cliente agregarCliente(String rut, String nombre, String direccion, String telefono, String mail)
			throws Exception {
		if (clientes.containsKey(rut))
			throw new Exception("Ya existe un cliente con el RUT indicado.");

		Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);

		clientes.put(cliente.getRut(), cliente);

		return cliente;
	}

	public Cliente buscarCliente(String rut) throws Exception {
		Cliente cliente = clientes.get(rut);
		return cliente;
	}

	public Stream<Cliente> buscarClientes(String patronNombreCliente) {
		return clientes.values().stream().filter(cliente -> cliente.getNombre().matches(patronNombreCliente));
	}

	public Stream<Cliente> listarClientes() {
		return clientes.values().stream();
	}

	public Hotel agregarHotel(String nombre, String pais) throws Exception {
		if (hoteles.containsKey(nombre))
			throw new Exception("Ya existe un hotel con el nombre indicado.");

		Hotel hotel = new Hotel(nombre, pais);

		hoteles.put(hotel.getNombre(), hotel);

		return hotel;
	}

	public Hotel buscarHotel(String nombre) throws Exception {
		Hotel hotel = hoteles.get(nombre);

		if (hotel == null)
			throw new Exception("No existe un hotel con el nombre indicado.");

		return hotel;
	}

	public Stream<Hotel> listarHoteles() {
		return hoteles.values().stream();
	}

	public TipoHabitacion agregarTipoHabitacion(String nombre) throws Exception {
		if (tiposHabitacion.containsKey(nombre))
			throw new Exception("Ya existe un tipo de habitaci�n con el nombre indicado.");

		TipoHabitacion tipoHabitacion = new TipoHabitacion(nombre);

		tiposHabitacion.put(tipoHabitacion.getNombre(), tipoHabitacion);

		return tipoHabitacion;
	}

	public TipoHabitacion buscarTipoHabitacion(String nombre) throws Exception {
		TipoHabitacion tipoHabitacion = tiposHabitacion.get(nombre);

		if (tipoHabitacion == null)
			throw new Exception("No existe un tipo de habitaci�n con el nombre indicado.");

		return tipoHabitacion;
	}

	public Stream<TipoHabitacion> listarTiposHabitacion() {
		return tiposHabitacion.values().stream();
	}

	public boolean confirmaDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {

		Hotel oHotel = null;
		oHotel = this.buscarHotel(nombreHotel);

		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		if (tipoHabitacion == null) {
			throw new Exception("No existe el tipo de habitacion solicitada!!!");
		}

		GregorianCalendar hoy = Infrastructure.getInstance().getCalendario().getHoy();
		if (Infrastructure.getInstance().getCalendario().esAnterior(fechaInicio, hoy)) {
			throw new Exception("Fecha de inicio esta en el pasado!!!");
		}

		if (Infrastructure.getInstance().getCalendario().esPosterior(fechaInicio, fechaFin)) {
			throw new Exception("Fecha de inicio no puede ser posterior a Fecha de fin!!!");
		}
		return oHotel.confirmaDisponibilidad(tipoHabitacion,fechaInicio,fechaFin);
	}

	public Stream<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		if (tipoHabitacion == null) {
			throw new Exception("No existe el tipo de habitacion solicitada!!!");
		}
		
		GregorianCalendar hoy = Infrastructure.getInstance().getCalendario().getHoy();
		if (Infrastructure.getInstance().getCalendario().esAnterior(fechaInicio, hoy)) {
			throw new Exception("Fecha de inicio esta en el pasado!!!");
		}

		if (Infrastructure.getInstance().getCalendario().esPosterior(fechaInicio, fechaFin)) {
			throw new Exception("Fecha de inicio no puede ser posterior a Fecha de fin!!!");
		}
		
		List<Hotel> alternativas = new ArrayList<Hotel>();
		for (Hotel hotel : this.hoteles.values()) {
			if (hotel.getPais().equals(pais)) {
				if (this.confirmaDisponibilidad(hotel.getNombre(), nombreTipoHabitacion, fechaInicio, fechaFin)) {
					alternativas.add(hotel);
				}
			}
		}
		return alternativas.stream();
	}

	public Stream<Reserva> buscarReservasDelCliente(Cliente cliente) throws Exception {
		if (cliente == null) {
			throw new Exception("El cliente no ha sido seleccionado!!!");
		}
		GregorianCalendar hoy = Infrastructure.getInstance().getCalendario().getHoy();
		List<Reserva> reservasDelCliente = new ArrayList<Reserva>();
		for (Hotel hotel : this.hoteles.values()) {
			List<Reserva> reservas = hotel.listarReservas().collect(Collectors.toList());
			for (Reserva reserva : reservas) {
				if (reserva.getCliente().equals(cliente)
						&& Infrastructure.getInstance().getCalendario().esPosterior(reserva.getFechaFin(), hoy)
						&& EstadoReserva.Pendiente.equals(reserva.getEstado())) {
					reservasDelCliente.add(reserva);
				}
			}
		}
		return reservasDelCliente.stream();
	}

	public Reserva seleccionarReserva(Cliente cliente, long codigo) throws Exception {
		Reserva reserva = null;
		for (Hotel hotel : hoteles.values()) {
			Optional<Reserva> opt = hotel.listarReservas().filter(r -> r.getCodigo() == codigo).findFirst();
			if (opt.isPresent()) {
				reserva = opt.get();
			}
		}
		if (reserva == null) {
			throw new Exception("No se encuentra reserva para el codigo solicitado!!!");
		}
		if (cliente == null) {
			throw new Exception("No se ha seleccionado Cliente!!!");
		}
		if (!reserva.getCliente().equals(cliente)) {
			throw new Exception(
					"El cliente asignado a la reserva no concuerda con el cliente actualmente seleccionado!!!");
		}
		return reserva;
	}

	public Reserva seleccionarReserva( long codigo) throws Exception {
		Reserva reserva = null;
		for (Hotel hotel : hoteles.values()) {
			Optional<Reserva> opt = hotel.listarReservas().filter(r -> r.getCodigo() == codigo).findFirst();
			if (opt.isPresent()) {
				reserva = opt.get();
			}
		}
		if (reserva == null) {
			throw new Exception("No se encuentra reserva para el codigo solicitado!!!");
		}
		
		return reserva;
	}
	
	public Reserva modificarReserva(Reserva reserva, String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		System.out.println(nombreHotel + " - Modificando reserva   (Antes): " + reserva);

		Hotel nuevoHotel = this.hoteles.get(nombreHotel);
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		reserva.getHotel().modificarReserva(reserva, nuevoHotel, tipoHabitacion, fechaInicio, fechaFin,
				modificablePorHuesped);

		System.out.println(nombreHotel + " - Modificando reserva (Despues): " + reserva);
		return reserva;
	}

	public Reserva cancelarReserva(Reserva reserva) {
		Reserva reservaMod = reserva.cancelarReserva(reserva);

		for (String key : hoteles.keySet()) {
			if (reservaMod.getHotel().getNombre().equals(key)) {
				Hotel h = hoteles.get(key);
				Map<Integer, Reserva> r = h.getReservasMap();
				r.remove(reserva.getCodigo());
			}
		}

		return reservaMod;
	}

}
