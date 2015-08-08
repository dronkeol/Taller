package org.tds.sgh.test.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tds.sgh.infrastructure.ISistemaMensajeria;

public class SistemaMensajeriaStub implements ISistemaMensajeria
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private List<Mail> mails;
	
	
	// Constructors (public) ----------------------------------------------------------------------
	
	public SistemaMensajeriaStub()
	{
		mails = new ArrayList<Mail>();
	}
	
	
	// Properties (public) ------------------------------------------------------------------------
	
	public List<Mail> getMailEnviados()
	{
		return Collections.unmodifiableList(mails);
	}
	
	
	// Operations (public) ------------------------------------------------------------------------
	
	@Override
	public void enviarMail(String destinatario, String asunto, String texto)
	{
		mails.add(new Mail(destinatario, asunto, texto));
	}

	
	// ============================================================================================
	
	public static class Mail
	{
		// Attributes (private) -------------------------------------------------------------------
		
		private String destinatario;
		
		private String asunto;
		
		private String texto;
		
		
		// Constructors (public) ------------------------------------------------------------------
		
		public Mail(String destinatario, String asunto, String texto)
		{
			this.destinatario = destinatario;
			
			this.asunto = asunto;
			
			this.texto = texto;
		}
		
		
		// Properties (public) --------------------------------------------------------------------
		
		public String getDestinatario()
		{
			return destinatario;
		}
		
		public String getAsunto()
		{
			return asunto;
		}
		
		public String getTexto()
		{
			return texto;
		}
	}
}
