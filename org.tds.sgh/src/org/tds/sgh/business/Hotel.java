package org.tds.sgh.business;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.tds.sgh.infrastructure.Infrastructure;

@Entity
public class Hotel {
	// Attributes (private)
	// -----------------------------------------------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String nombre;

	private String pais;

	@OneToMany(cascade = CascadeType.ALL)
	@MapKey(name = "nombre")
	private Map<String, Habitacion> habitaciones;

	@OneToMany(cascade = CascadeType.ALL)
	@MapKey(name = "codigo")
	private Map<Integer, Reserva> reservas;

	// Constructors (public)
	// ----------------------------------------------------------------------

	public Hotel(String nombre, String pais) {
		this.nombre = nombre;

		this.pais = pais;

		this.habitaciones = new HashMap<String, Habitacion>();
	}

	// Properties (public)
	// ------------------------------------------------------------------------

	public String getNombre() {
		return nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPais() {
		return pais;
	}

	// Operations (public)
	// ------------------------------------------------------------------------

	public Habitacion agregarHabitacion(TipoHabitacion tipoHabitacion, String nombre) throws Exception {
		if (habitaciones.containsKey(nombre))
			throw new Exception("El hotel ya tiene una habitaci�n con el nombre indicado.");

		Habitacion habitacion = new Habitacion(tipoHabitacion, nombre);

		habitaciones.put(habitacion.getNombre(), habitacion);

		return habitacion;
	}

	public Stream<Habitacion> listarHabitaciones() {
		return habitaciones.values().stream();
	}

	public Stream<Reserva> listarReservas() {
		return reservas == null ? Stream.of(new Reserva[] {}) : reservas.values().stream();
	}

	public Map<Integer, Reserva> getReservasMap() {
		return reservas;
	}

	public Reserva registrarReserva(Cliente cliente, TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		Reserva r = new Reserva(this, cliente, tipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
		if (this.reservas == null) {
			this.reservas = new HashMap<Integer, Reserva>();
		}
		this.reservas.put(r.getCodigo(), r);
		return r;
	}

	public void tomarReserva(Reserva reserva) {
		List<Habitacion> lstHabitaciones = new ArrayList<Habitacion>();

		for (Habitacion hab : habitaciones.values()) {
			if (hab.getTipoHabitacion().getNombre().equals(reserva.getTipoHabitacion().getNombre()))
				lstHabitaciones.add(hab);
		}

		for (Reserva r : reservas.values()) {
			boolean equalsTipoHabitacion = r.getTipoHabitacion().getNombre()
					.equals(reserva.getTipoHabitacion().getNombre());

			boolean fechaFinAnterior = Infrastructure.getInstance().getCalendario().esAnterior(reserva.getFechaFin(),
					r.getFechaInicio());

			boolean fechaInicioPosterior = Infrastructure.getInstance().getCalendario()
					.esPosterior(reserva.getFechaInicio(), r.getFechaFin());

			if (equalsTipoHabitacion && !(fechaFinAnterior && fechaInicioPosterior)) {
				if (r.getHabitacion() != null)
					lstHabitaciones.remove(r.getHabitacion());
			}
		}

		if (lstHabitaciones.size() > 0) {
			reserva.setHabitacion(lstHabitaciones.get(0));
			reserva.setEstado(EstadoReserva.Tomada);
		}
	}

	public void eliminarReserva(Reserva reserva) {
		if (this.reservas != null) {
			this.reservas.remove(reserva.getCodigo());
		}
	}

	public void modificarReserva(Reserva reserva, Hotel nuevoHotel, TipoHabitacion tipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		reserva.modificarReserva(nuevoHotel, tipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
	}

	@Override
	public String toString() {
		return "Hotel [nombre=" + nombre + "]";
	}

	public boolean confirmaDisponibilidad(TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, Reserva reservaSeleccionada) {
		Stream<Habitacion> lstHabitaciones = this.listarHabitaciones()
				.filter(h -> h.getTipoHabitacion().equals(tipoHabitacion));
		long countHabitaciones = lstHabitaciones.count();

		long countReservas = 0;

		if (this.listarReservas().count() > 0) {

			Stream<Reserva> lstReservas = this.listarReservas().filter(p -> {

				if (p.equals(reservaSeleccionada)) {
					return false;
				}

				boolean equalsTipoHabitacion = p.getTipoHabitacion().equals(tipoHabitacion);

				boolean isPendiente = EstadoReserva.Pendiente.equals(p.getEstado());

				boolean fechaFinAntesFechaInicio = Infrastructure.getInstance().getCalendario().esAnterior(fechaFin,
						p.getFechaInicio());

				boolean fechaInicioDespuesFechaFin = Infrastructure.getInstance().getCalendario()
						.esPosterior(fechaInicio, p.getFechaFin());

				boolean isConsecutiva = Infrastructure.getInstance().getCalendario().esMismoDia(fechaInicio,
						p.getFechaFin());

				boolean colisionPeriodo = !(fechaFinAntesFechaInicio || fechaInicioDespuesFechaFin) && !isConsecutiva;

				return equalsTipoHabitacion && isPendiente && colisionPeriodo;

			}

			);
			countReservas = lstReservas.count();
		}
		return countHabitaciones > countReservas;
	}
}
