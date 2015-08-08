package org.tds.sgh.infrastructure;

import java.util.GregorianCalendar;

public interface ICalendario
{
	// Properties ---------------------------------------------------------------------------------

	GregorianCalendar getHoy();

	
	// Operations ---------------------------------------------------------------------------------
	
	boolean esAnterior(GregorianCalendar fecha1, GregorianCalendar fecha2);

	boolean esMismoDia(GregorianCalendar fecha1, GregorianCalendar fecha2);

	boolean esPosterior(GregorianCalendar fecha1, GregorianCalendar fecha2);
	
	boolean esPasada(GregorianCalendar fecha);
	
	boolean esHoy(GregorianCalendar fecha);
	
	boolean esFutura(GregorianCalendar fecha);
}
