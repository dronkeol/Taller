package org.tds.sgh.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

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
    return EstadoReserva.Pendiente.equals(estado);
  }

  public Reserva(Hotel hotel, Cliente cliente, TipoHabitacion tipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {

    AtomicInteger atomicInteger = new AtomicInteger();
    this.codigo = atomicInteger.incrementAndGet();

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
  
  public void tomarReserva(){
	  
  }
}
