package org.tds.sgh.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.tds.sgh.infrastructure.Infrastructure;

public class CadenaHotelera {
	// Attributes (private)
	// -----------------------------------------------------------------------

	private String nombre;

	private Map<String, Cliente> clientes;

	private Map<String, Hotel> hoteles;

	private Map<String, TipoHabitacion> tiposHabitacion;

	// Constructors (public)
	// ----------------------------------------------------------------------

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
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {

		Hotel oHotel = null;
		try {
			oHotel = this.buscarHotel(nombreHotel);
		} catch (Exception e) {
			return false;
		}

		Stream<Habitacion> lstHabitaciones = oHotel.listarHabitaciones()
				.filter(h -> h.getTipoHabitacion().getNombre().equals(nombreTipoHabitacion));
		long countHabitaciones = lstHabitaciones.count();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // lowercase
		System.out.println(nombreHotel + " - Confirmando disponibilidad: " + formatter.format(fechaInicio.getTime())
				+ "-" + formatter.format(fechaFin.getTime()));

		long countReservas = 0;
		if (oHotel.listarReservas().count() > 0) {

			Stream<Reserva> lstReservas = oHotel.listarReservas().filter(p -> {

				System.out.println(nombreHotel + " - Evaluando reserva: " + p.toString());
				boolean equalsTipoHabitacion = p.getTipoHabitacion().getNombre().equals(nombreTipoHabitacion);

				boolean isPendiente = EstadoReserva.Pendiente.equals(p.getEstado());

				boolean fechaFinAntesFechaInicio = Infrastructure.getInstance().getCalendario().esAnterior(fechaFin,
						p.getFechaInicio());

				boolean fechaInicioDespuesFechaFin = Infrastructure.getInstance().getCalendario()
						.esPosterior(fechaInicio, p.getFechaFin());

				boolean colisionPeriodo = !(fechaFinAntesFechaInicio || fechaInicioDespuesFechaFin);

				if (equalsTipoHabitacion && isPendiente && colisionPeriodo) {
					System.out.println(nombreHotel + " - Colision!!!");
					return true;
				} else {
					return false;
				}
			}

			);
			countReservas = lstReservas.count();
		}
		return countHabitaciones > countReservas;
	}

	public Stream<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) {
		return this.hoteles.values().stream().filter(h -> h.getPais().equals(pais))
				.filter(h -> this.confirmaDisponibilidad(h.getNombre(), nombreTipoHabitacion, fechaInicio, fechaFin));
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

	public Reserva seleccionarReserva(long codigo) {
		for (Hotel hotel : hoteles.values()) {
			Optional<Reserva> opt = hotel.listarReservas().filter(r -> r.getCodigo() == codigo).findFirst();
			if (opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}

	public Reserva modificarReserva(Reserva reserva, String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		System.out.println(nombreHotel + " - Modificando reserva   (Antes): " + reserva);

		Hotel nuevoHotel = this.hoteles.get(nombreHotel);
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		reserva.getHotel().modificarReserva(reserva, nuevoHotel, tipoHabitacion, fechaInicio, fechaFin,
				modificablePorHuesped);

		System.out.println(nombreHotel + " - Modificando reserva (Despues): " + reserva);
		return reserva;
	}

	public Reserva cancelarReserva(Reserva reserva) {
		return reserva.cancelarReserva(reserva);
	}

}
