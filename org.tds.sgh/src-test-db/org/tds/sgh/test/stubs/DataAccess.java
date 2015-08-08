package org.tds.sgh.test.stubs;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DataAccess
{
	// Attributes (dependencies) ------------------------------------------------------------------
	
	private static final Logger log = Logger.getLogger(DataAccess.class);
	
	
	// Attributes (static private) ----------------------------------------------------------------

	private static DataAccess Instance;
	
	
	// Operations (static public) -----------------------------------------------------------------
	
	public static DataAccess getInstance()
	{
		if (Instance == null)
		{
			Instance = new DataAccess();
		}
		
		return Instance;
	}
	
	
	// Attributes (private) -----------------------------------------------------------------------
	
	private SessionFactory sessionFactory;
	
	
	// Constructors (private) ---------------------------------------------------------------------
	
	private DataAccess()
	{
	}
	
	
	// Operations (public) ------------------------------------------------------------------------
	
	public void start()
	{
		if (sessionFactory == null)
		{
			try
			{
				sessionFactory =
					new AnnotationConfiguration()
						.configure()
						.buildSessionFactory();
			}
			catch (Throwable t)
			{
				log.error("Cannot access the database", t);
				t.printStackTrace();
				throw new ExceptionInInitializerError(t);
			}
		}
	}
	
	public DataAccessConnection createConnection()
	{
		start();
		return new DataAccessConnection(sessionFactory.openSession());
	}

	public void shutdown()
	{
		sessionFactory.close();
	}
}
