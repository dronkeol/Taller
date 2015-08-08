package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;

public class Reserva {

  private int codigo;
  private Date fechaInicio;
  private Date fechaFin;
  boolean modificablePorHuesped;
  private EstadoReserva estado;
  private Collection<Huesped> huespedes;

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

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
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

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
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


}
