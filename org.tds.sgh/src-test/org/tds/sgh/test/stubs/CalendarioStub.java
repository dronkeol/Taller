package org.tds.sgh.test.stubs;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Consumer;

import org.tds.sgh.infrastructure.ICalendario;

public class CalendarioStub implements ICalendario
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private GregorianCalendar hoy;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public CalendarioStub(Consumer<Consumer<GregorianCalendar>> ajustador)
	{
		hoy = new GregorianCalendar();
		
		ajustador.accept(this::setHoy);
	}

	
	// Properties (public) ------------------------------------------------------------------------

	@Override
	public GregorianCalendar getHoy()
	{
		return (GregorianCalendar)hoy.clone();
	}
	

	// Properties (private) -----------------------------------------------------------------------

	private void setHoy(GregorianCalendar hoy)
	{
		this.hoy = (GregorianCalendar)hoy.clone();
	}
	

	// Operations (public) ------------------------------------------------------------------------

	@Override
	public boolean esAnterior(GregorianCalendar fecha1, GregorianCalendar fecha2)
	{
		return fecha1.get(Calendar.YEAR) < fecha2.get(Calendar.YEAR) ||
			   (fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
			    fecha1.get(Calendar.DAY_OF_YEAR) < fecha2.get(Calendar.DAY_OF_YEAR));
	}

	@Override
	public boolean esMismoDia(GregorianCalendar fecha1, GregorianCalendar fecha2)
	{
		return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
			   fecha1.get(Calendar.DAY_OF_YEAR) == fecha2.get(Calendar.DAY_OF_YEAR);		
	}

	@Override
	public boolean esPosterior(GregorianCalendar fecha1, GregorianCalendar fecha2)
	{
		return !esAnterior(fecha1, fecha2) && !esMismoDia(fecha1, fecha2);
	}
	
	@Override
	public boolean esPasada(GregorianCalendar fecha)
	{
		return esAnterior(fecha, getHoy());
	}
	
	@Override
	public boolean esHoy(GregorianCalendar fecha)
	{
		return esMismoDia(fecha, getHoy());
	}
	
	@Override
	public boolean esFutura(GregorianCalendar fecha)
	{
		return esPosterior(fecha, getHoy());
	}
}
