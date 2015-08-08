package org.tds.sgh.test.stubs;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

public class DataAccessConnection
{
	// Attributes (private) -----------------------------------------------------------------------
	
	private Session session;
	
	private Transaction transaction;
	
	
	// Constructors (public) ----------------------------------------------------------------------

	public DataAccessConnection(Session session)
	{
		this.session = session;
	}
	
	
	// Operations (public) ------------------------------------------------------------------------
	
	public void beginTx()
	{
		transaction = session.beginTransaction();				
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz)
	{
		@SuppressWarnings("rawtypes")
		List objlist = session.createCriteria(clazz).list();
		
		if (objlist.isEmpty())
		{
			return null;
		}
		else
		{
			return (T)objlist.get(0);
		}
	}
	
	public <T> int getCount(Class<T> clazz)
	{
		return (int)session.createCriteria(clazz).setProjection(Projections.rowCount()).uniqueResult();
	}
	
	public void save(Object o)
	{
		session.save(o);
	}
	
	public void commitTx() 
	{
		transaction.commit();
	}
	
	public void rollbackTx()
	{
		transaction.rollback();
	}
	
	public void close()
	{
		session.close();
	}
}
