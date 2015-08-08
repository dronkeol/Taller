package org.tds.sgh.infrastructure;


public class Infrastructure
{
	// Attributes (static private) ----------------------------------------------------------------
	
	private static final ThreadLocal<Infrastructure> Instance = new ThreadLocal<Infrastructure>();
	
	
	// Operations (static public) -----------------------------------------------------------------
	
	public static void clear()
	{
		Instance.set(null);
	}
	
	public static void configure(ISistemaFacturacion sistemaFacturacion, ISistemaMensajeria sistemaMensajeria, ICalendario calendario)
	{
		Infrastructure instance = new Infrastructure();

		instance.calendario = calendario;

		instance.sistemaFacturacion = sistemaFacturacion;
		
		instance.sistemaMensajeria = sistemaMensajeria;

		Instance.set(instance);
	}
	
	public static Infrastructure getInstance()
	{
		return Instance.get();
	}
	
	// Attributes (private) -----------------------------------------------------------------------

	private ICalendario calendario;
	
	private ISistemaFacturacion sistemaFacturacion;
	
	private ISistemaMensajeria sistemaMensajeria;
	
	
	// Constructors (private) ---------------------------------------------------------------------
	
	private Infrastructure()
	{
	}

	
	// Properties (public) ------------------------------------------------------------------------
	
	public ICalendario getCalendario()
	{
		return calendario;
	}

	public ISistemaFacturacion getSistemaFacturacion()
	{
		return sistemaFacturacion;
	}

	public ISistemaMensajeria getSistemaMensajeria()
	{
		return sistemaMensajeria;
	}
}
