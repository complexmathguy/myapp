/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.dao;

import java.util.*;
import java.util.logging.Logger;

import java.sql.*;

import org.hibernate.*;
import org.hibernate.cfg.*;

import com.occulue.exception.ProcessingException;

    import com.occulue.primarykey.*;
    import com.occulue.bo.*;
    
import com.occulue.dao.*;

/** 
 * Implements the Hibernate persistence processing for business entity Employee.
 *
 * @author 
 */

// AIB : #getDAOClassDecl()
 public class EmployeeDAO extends BaseDAO
// ~AIB
{
    /**
     * default constructor
     */
    public EmployeeDAO()
    {
    }

    /**
     * Retrieves a Employee from the persistent store, using the provided primary key. 
     * If no match is found, a null Employee is returned.
     * <p>
     * @param       pk
     * @return      Employee
     * @exception   ProcessingException
     */
    public Employee findEmployee( EmployeePrimaryKey pk ) 
    throws ProcessingException
    {
        if (pk == null)
        {
            throw new ProcessingException("EmployeeDAO.findEmployee(...) cannot have a null primary key argument");
        }
    
		Query query 	= null;		
		Employee businessObject = null;
		
	    StringBuilder fromClause = new StringBuilder( "from com.occulue.bo.Employee as employee where " );
		
        Session session = null;
        Transaction tx = null;

        try
        {
            session = currentSession();
            tx      = currentTransaction(session);
            
// AIB : #getHibernateFindFromClause()
			fromClause.append( "employee.employeeId = " + pk.getEmployeeId().toString() );
// ~AIB

	 		query = session.createQuery( fromClause.toString() );
	 		
	 		if ( query != null )
	 		{
                businessObject = new Employee();
                businessObject.copy((Employee)query.list().iterator().next());
	 		}

			commitTransaction(tx);						
		}
		catch( Throwable exc )
		{
			businessObject = null;
			exc.printStackTrace();
			throw new ProcessingException( "EmployeeDAO.findEmployee failed for primary key " + pk + " - " + exc );		
		}		
		finally
		{
			try
			{
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "EmployeeDAO.findEmployee - Hibernate failed to close the Session - " + exc );
			}
		}		    
				
        return( businessObject );
    }
        
	    /**
	     * returns a Collection of all Employees
	     * @return		ArrayList<Employee>
	     * @exception   ProcessingException
	     */
	    public ArrayList<Employee> findAllEmployee()
	    throws ProcessingException
	    {
			ArrayList<Employee> list = new ArrayList<Employee>();
			ArrayList<Employee> refList = new ArrayList<Employee>();
			Query query 							= null;		
			StringBuilder buf 						= new StringBuilder( "from com.occulue.bo.Employee" );

			try
			{
				Session session = currentSession();

		 		query = session.createQuery( buf.toString() );
		 		
				if ( query != null )
				{
					list = (ArrayList<Employee>)query.list();
					Employee tmp = null;
					
	                for (Employee listEntry : list)
	                {
	                    tmp = new Employee();
	                    tmp.copyShallow(listEntry);
	                    refList.add(tmp);
	                }
				}
			}
			catch( Throwable exc )
			{
				exc.printStackTrace();		
				throw new ProcessingException( "EmployeeDAO.findAllEmployee failed - " + exc );		
			}		
			finally
			{
				try
				{
					closeSession();			
				}
				catch( Throwable exc )
				{		
					LOGGER.info( "EmployeeDAO.findAllEmployee - Hibernate failed to close the Session - " + exc );
				}		
			}
			
			if ( list.size() <= 0 )
			{
				LOGGER.info( "EmployeeDAO:findAllEmployees() - List is empty.");
			}
	        
			return( refList );		        
	    }
		

	    /**
	     * Inserts a new Employee into the persistent store.
	     * @param       businessObject
	     * @return      newly persisted Employee
	     * @exception   ProcessingException
	     */
	    public Employee createEmployee( Employee businessObject )
	    throws ProcessingException
	    {
		    Transaction tx 	= null;
			Session session	= null;
			
	    	try
	    	{    				
				session	= currentSession();
				tx 		= currentTransaction( session );
		
				session.save( businessObject );
				commitTransaction( tx );	
				
			}
			catch( Throwable exc )
			{
				try
				{
					if ( tx != null )
						rollbackTransaction( tx );				
				}
				catch( Throwable exc1 )
				{
					LOGGER.info( "EmployeeDAO.createEmployee - Hibernate failed to rollback - " + exc1 );
				}
				exc.printStackTrace();			
				throw new ProcessingException( "EmployeeDAO.createEmployee failed - " + exc );
			}		
			finally
			{
				try
				{
					session.flush();
					closeSession();			
				}
				catch( Throwable exc )
				{		
					LOGGER.info( "EmployeeDAO.createEmployee - Hibernate failed to close the Session - " + exc );
				}
			}		
			
	        // return the businessObject
	        return(  businessObject );
	    }
	    
    /**
     * Stores the provided Employee to the persistent store.
     *
     * @param       businessObject
     * @return      Employee	stored entity
     * @exception   ProcessingException 
     */
    public Employee saveEmployee( Employee businessObject )
    throws ProcessingException
    {
		Transaction tx 	= null;
		Session session = null;
		
    	try
    	{
			session = currentSession();
			tx		= currentTransaction( session );
	
			session.update( businessObject );
			commitTransaction( tx );	
		}
		catch( Throwable exc )
		{
			try
			{
				if ( tx != null )
					rollbackTransaction( tx );
			}
			catch( Throwable exc1 )
			{
				LOGGER.info( "EmployeeDAO.saveEmployee - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "EmployeeDAO.saveEmployee failed - " + exc );		
		}		
		finally
		{
			try
			{
				session.flush();
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "EmployeeDAO.saveEmployee - Hibernate failed to close the Session - " + exc );
			}
		}		    
        return( businessObject );
    }
    
    /**
    * Removes a Employee from the persistent store.
    *
    * @param        pk		identity of object to remove
    * @exception    ProcessingException
    */
    public void deleteEmployee( EmployeePrimaryKey pk ) 
    throws ProcessingException 
    {
	    Transaction tx 	= null;
	    Session session = null;
	    
    	try
    	{    	
			Employee bo = findEmployee(pk);    	
			
			session = currentSession();
			tx		= currentTransaction( session );						
			session.delete( bo );
			commitTransaction( tx );
		}
		catch( Throwable exc )
		{
			try
			{
				if ( tx != null )
					rollbackTransaction( tx );				
			}
			catch( Throwable exc1 )
			{
				LOGGER.info( "EmployeeDAO.deleteEmployee - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "EmployeeDAO.deleteEmployee failed - " + exc );					
		}		
		finally
		{
			try
			{
				session.flush();
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "EmployeeDAO.deleteEmployee - Hibernate failed to close the Session - " + exc );
			}
		}
    }


// AIB : #outputDAOFindAllImplementations()
// ~AIB


//*****************************************************
// Attributes
//*****************************************************
	private static final Logger LOGGER 	= Logger.getLogger(Employee.class.getName());

}
