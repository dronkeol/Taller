package org.tds.sgh.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.tds.sgh.infrastructure.Infrastructure;

@Entity
public class Hotel {
	// Attributes (private)
	// -----------------------------------------------------------------------

	@Id
	@Column(name = "nombre")
	private String nombre;

	private String pais;

	private Map<String, Habitacion> habitaciones;

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

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // lowercase
		System.out.println(this.nombre + " - Registrando reserva: " + formatter.format(fechaInicio.getTime()) + "-"
				+ formatter.format(fechaFin.getTime()));

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
			{
				// SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM
				// dd"); // lowercase "dd"

				boolean equalsTipoHabitacion = r.getTipoHabitacion().getNombre()
						.equals(reserva.getTipoHabitacion().getNombre());

				// boolean isPendiente =
				// EstadoReserva.Pendiente.equals(p.getEstado());

				// System.out.println(formatter.format(fechaFin.getTime()));
				// System.out.println(formatter.format(p.getFechaInicio().getTime()));
				boolean fechaFinAnterior = Infrastructure.getInstance().getCalendario()
						.esAnterior(reserva.getFechaFin(), r.getFechaInicio());

				// System.out.println("**");
				// System.out.println(formatter.format(fechaInicio.getTime()));
				// System.out.println(formatter.format(p.getFechaFin().getTime()));
				boolean fechaInicioPosterior = Infrastructure.getInstance().getCalendario()
						.esPosterior(reserva.getFechaInicio(), r.getFechaFin());

				if (equalsTipoHabitacion && !(fechaFinAnterior && fechaInicioPosterior)) {
					if (r.getHabitacion() != null)
						lstHabitaciones.remove(r.getHabitacion());
				}

			}

		}

		if (lstHabitaciones.size() > 0) {
			reserva.setHabitacion(lstHabitaciones.get(0));
			reserva.setEstado(EstadoReserva.Tomada);
			// reserva.setModificablePorHuesped(false);
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

	public boolean confirmaDisponibilidad(TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		Stream<Habitacion> lstHabitaciones = this.listarHabitaciones()
				.filter(h -> h.getTipoHabitacion().equals(tipoHabitacion));
		long countHabitaciones = lstHabitaciones.count();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // lowercase
		System.out.println(this.getNombre() + " - Confirmando disponibilidad: " + formatter.format(fechaInicio.getTime())
				+ "-" + formatter.format(fechaFin.getTime()));

		long countReservas = 0;
		
		if (this.listarReservas().count() > 0) {

			Stream<Reserva> lstReservas = this.listarReservas().filter(p -> {

				System.out.println(this.getNombre() + " - Evaluando reserva: " + p.toString());
				boolean equalsTipoHabitacion = p.getTipoHabitacion().equals(tipoHabitacion);

				boolean isPendiente = EstadoReserva.Pendiente.equals(p.getEstado());

				boolean fechaFinAntesFechaInicio = Infrastructure.getInstance().getCalendario().esAnterior(fechaFin,
						p.getFechaInicio());

				boolean fechaInicioDespuesFechaFin = Infrastructure.getInstance().getCalendario()
						.esPosterior(fechaInicio, p.getFechaFin());

				
				boolean isConsecutiva = Infrastructure.getInstance().getCalendario().esMismoDia(fechaInicio, p.getFechaFin());
				
				boolean colisionPeriodo = !(fechaFinAntesFechaInicio || fechaInicioDespuesFechaFin) && !isConsecutiva;

				if (equalsTipoHabitacion && isPendiente && colisionPeriodo) {
					System.out.println(this.getNombre() + " - Colision!!!");
					return true;
				} else {
					return false;
				}
			}

			);
			countReservas = lstReservas.count();
		}
		return countHabitaciones>countReservas;
	}

	public boolean confirmaDisponibilidadPorModificacion(TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) {
		Stream<Habitacion> lstHabitaciones = this.listarHabitaciones()
				.filter(h -> h.getTipoHabitacion().equals(tipoHabitacion));
		long countHabitaciones = lstHabitaciones.count();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // lowercase
		System.out.println(this.getNombre() + " - Confirmando disponibilidad: " + formatter.format(fechaInicio.getTime())
				+ "-" + formatter.format(fechaFin.getTime()));

		long countReservas = 0;
		
		if (this.listarReservas().count() > 0) {

			Stream<Reserva> lstReservas = this.listarReservas().filter(p -> {

				System.out.println(this.getNombre() + " - Evaluando reserva: " + p.toString());
				boolean equalsTipoHabitacion = p.getTipoHabitacion().equals(tipoHabitacion);

				boolean isPendiente = EstadoReserva.Pendiente.equals(p.getEstado());

				boolean fechaFinAntesFechaInicio = Infrastructure.getInstance().getCalendario().esAnterior(fechaFin,
						p.getFechaInicio());

				boolean fechaInicioDespuesFechaFin = Infrastructure.getInstance().getCalendario()
						.esPosterior(fechaInicio, p.getFechaFin());

				
				boolean isConsecutiva = Infrastructure.getInstance().getCalendario().esMismoDia(fechaInicio, p.getFechaFin());
				
				boolean colisionPeriodo = !(fechaFinAntesFechaInicio || fechaInicioDespuesFechaFin) && !isConsecutiva;

				if (equalsTipoHabitacion && isPendiente && colisionPeriodo) {
					System.out.println(this.getNombre() + " - Colision!!!");
					return true;
				} else {
					return false;
				}
			}

			);
			countReservas = lstReservas.count();
			
			if (countReservas==1 && countHabitaciones==1){
				return true;
			}
		}
		return countHabitaciones>countReservas;
	}

}
