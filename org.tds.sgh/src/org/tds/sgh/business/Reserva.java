package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class Reserva {

	private int codigo;
	private Cliente cliente;
	private Hotel hotel;
	private TipoHabitacion tipoHabitacion;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	boolean modificablePorHuesped;
	private EstadoReserva estado;
	private Collection<Huesped> huespedes;
	private Habitacion habitacion;

	public boolean isPendiente() {
		throw new UnsupportedOperationException();

	}

	public Reserva crearReserva(GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.modificablePorHuesped = modificablePorHuesped;
		return this;
		
	}

	public boolean entrePeriodo(Date fechaInicio, Date fechaFin) {
		throw new UnsupportedOperationException();
	}

	public Reserva find(int codigoReserva) {
		throw new UnsupportedOperationException();
	}

	public void registrarHuesped(String nombre, String documento) {
		throw new UnsupportedOperationException();
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
}
