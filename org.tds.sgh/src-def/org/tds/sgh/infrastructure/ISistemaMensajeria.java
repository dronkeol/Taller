package org.tds.sgh.infrastructure;

public interface ISistemaMensajeria
{
	// Operations ---------------------------------------------------------------------------------

	public void enviarMail(String destinatario, String asunto, String mensaje);
}
