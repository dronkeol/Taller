package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class Reserva {

  private int codigo;
  private GregorianCalendar fechaInicio;
  private GregorianCalendar fechaFin;

  boolean modificablePorHuesped;
  private EstadoReserva estado;
  private Collection<Huesped> huespedes;
  private Habitacion habitacion;
  private TipoHabitacion tipoHabitacion;

  public boolean isPendiente() {
    throw new UnsupportedOperationException();

  }

  public Reserva crearReserva(Date fechaInicio, Date fechaFin, boolean modificablePorHuesped) {
    throw new UnsupportedOperationException();
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


}