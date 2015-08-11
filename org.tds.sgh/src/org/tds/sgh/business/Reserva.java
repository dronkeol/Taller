package org.tds.sgh.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.tds.sgh.system.Sequence;

@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private int codigo;

	@OneToOne(cascade =CascadeType.ALL)
	private Cliente cliente;
	
	@OneToOne(cascade =CascadeType.ALL)
	private Hotel hotel;

	@OneToOne(cascade =CascadeType.ALL)
	private TipoHabitacion tipoHabitacion;

	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	boolean modificablePorHuesped;

	@Enumerated(EnumType.STRING)
	private EstadoReserva estado;

	@OneToMany(cascade =CascadeType.ALL)
	private Collection<Huesped> huespedes;

	@OneToOne(cascade =CascadeType.ALL)
	private Habitacion habitacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isPendiente() {
		return EstadoReserva.Pendiente.equals(estado);
	}

	public Reserva(Hotel hotel, Cliente cliente, TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) {

		this.codigo = Sequence.getInstance().getCounter();

		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.modificablePorHuesped = modificablePorHuesped;
		this.huespedes = new ArrayList<Huesped>();
		this.cliente = cliente;
		this.hotel = hotel;
		this.tipoHabitacion = tipoHabitacion;
		this.estado = EstadoReserva.Pendiente;
	}

	public boolean entrePeriodo(Date fechaInicio, Date fechaFin) {
		throw new UnsupportedOperationException();
	}

	public Reserva find(int codigoReserva) {
		throw new UnsupportedOperationException();
	}

	public Reserva registrarHuesped(String nombre, String documento) {
		Huesped huesped = new Huesped();
		huesped.setDocumento(documento);
		huesped.setNombre(nombre);
		if (huespedes == null) {
			huespedes = new ArrayList<Huesped>();
		}
		this.huespedes.add(huesped);
		return this;
	}

	public int getCodigo() {
		return codigo;
	}

	public boolean isModificablePorHuesped() {
		return modificablePorHuesped;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public Collection<Huesped> getHuespedes() {
		return huespedes;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setModificablePorHuesped(boolean modificablePorHuesped) {
		this.modificablePorHuesped = modificablePorHuesped;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public void setHuespedes(Collection<Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public void tomarReserva() {

	}

	public Reserva modificarReserva(Hotel hotel, TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		if (this.isModificablePorHuesped()) {
			this.setFechaInicio(fechaInicio);
			this.setFechaFin(fechaFin);
			this.setTipoHabitacion(tipoHabitacion);
			this.setModificablePorHuesped(modificablePorHuesped);

			if (!this.hotel.equals(hotel)) {
				this.getHotel().eliminarReserva(this);
				this.setHotel(hotel);
			}
		} else {
			throw new Exception("Esta reserva no es modificable");
		}

		return this;
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return "Reserva [codigo=" + codigo + ", cliente=" + cliente + ", hotel=" + hotel + ", tipoHabitacion="
				+ tipoHabitacion + ", fechaInicio=" + formatter.format(fechaInicio.getTime()) + ", fechaFin="
				+ formatter.format(fechaFin.getTime()) + ", modificablePorHuesped=" + modificablePorHuesped
				+ ", estado=" + estado + ", huespedes=" + huespedes + ", habitacion=" + habitacion + "]";
	}

	public Reserva cancelarReserva(Reserva reserva) {
		reserva.setEstado(EstadoReserva.Cancelada);
		return this;
	}
}
