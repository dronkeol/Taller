package org.tds.sgh.dtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class ReservaDTO
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private long codigo;
	
	private String rutCliente;
	
	private String hotel;
	
	private String tipoHabitacion;
	
	private GregorianCalendar fechaInicio;
	
	private GregorianCalendar fechaFin;
	
	private boolean modificablePorHuesped;
	
	private String estado;
	
	private String habitacion;
	
	private List<HuespedDTO> huespedes; 
		
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public ReservaDTO(long codigo, String rutCliente, String hotel, String tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped, String estado, String habitacion, HuespedDTO... huespedes)
	{
		this.codigo = codigo;
		
		this.rutCliente = rutCliente;
		
		this.hotel = hotel;
		
		this.tipoHabitacion = tipoHabitacion;
		
		this.fechaInicio = fechaInicio;
		
		this.fechaFin = fechaFin;
		
		this.modificablePorHuesped = modificablePorHuesped;
		
		this.estado = estado;
		
		this.habitacion = habitacion;
		
		this.huespedes = huespedes == null ? new ArrayList<HuespedDTO>() : new ArrayList<HuespedDTO>(Arrays.asList(huespedes));
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public long getCodigo()
	{
		return codigo;
	}
	
	public String getRutCliente()
	{
		return rutCliente;
	}
	
	public String getHotel()
	{
		return hotel;
	}
	
	public String getTipoHabitacion()
	{
		return tipoHabitacion;
	}
	
	public GregorianCalendar getFechaInicio()
	{
		return fechaInicio;
	}
	
	public GregorianCalendar getFechaFin()
	{
		return fechaFin;
	}
	
	public boolean isModificablePorHuesped()
	{
		return modificablePorHuesped;
	}
	
	public String getEstado()
	{
		return estado;
	}
	
	public String getHabitacion()
	{
		return habitacion;
	}
	
	public List<HuespedDTO> getHuespedes()
	{
		return Collections.unmodifiableList(huespedes);
	}
	
	
	// Operations (public) ------------------------------------------------------------------------
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		
		ReservaDTO that = (ReservaDTO)obj;
		
		if (this.codigo != that.codigo ||
			!this.rutCliente.equals(that.rutCliente) ||
			!this.hotel.equals(that.hotel) ||
			!this.tipoHabitacion.equals(that.tipoHabitacion) ||
			!this.fechaInicio.equals(that.fechaInicio) ||
			!this.fechaFin.equals(that.fechaFin) ||
			this.modificablePorHuesped != that.modificablePorHuesped ||
			!this.estado.equals(that.estado))
		{
			return false;
		}
		
		if ((this.habitacion == null && that.habitacion != null) ||
			(this.habitacion != null && that.habitacion == null) ||
			(this.habitacion != null && that.habitacion != null && !this.habitacion.equals(that.habitacion)))
		{
			return false;
		}
		
		if (this.huespedes.size() != that.huespedes.size())
		{
			return false;
		}
		else if (!this.huespedes.stream().allMatch(h1 -> that.huespedes.stream().anyMatch(h2 -> h1.equals(h2))))
		{
			return false;
		}
		
		return true;
	}	
}
