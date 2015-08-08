package org.tds.sgh.infrastructure;

public class NotImplementedException extends RuntimeException
{
	// Constants ----------------------------------------------------------------------------------
	
	private static final long serialVersionUID = -2925980211706573003L;

	
	// Constructors -------------------------------------------------------------------------------
	
	public NotImplementedException()
	{
		super("La operación no está implementada.");
	}
	
	public NotImplementedException(String message)
	{
		super(message);
	}
}