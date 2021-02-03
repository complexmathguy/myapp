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
 * Implements the Hibernate persistence processing for business entity Department.
 *
 * @author 
 */

// AIB : #getDAOClassDecl()
 public class DepartmentDAO extends BaseDAO
// ~AIB
{
    /**
     * default constructor
     */
    public DepartmentDAO()
    {
    }

    /**
     * Retrieves a Department from the persistent store, using the provided primary key. 
     * If no match is found, a null Department is returned.
     * <p>
     * @param       pk
     * @return      Department
     * @exception   ProcessingException
     */
    public Department findDepartment( DepartmentPrimaryKey pk ) 
    throws ProcessingException
    {
        if (pk == null)
        {
            throw new ProcessingException("DepartmentDAO.findDepartment(...) cannot have a null primary key argument");
        }
    
		Query query 	= null;		
		Department businessObject = null;
		
	    StringBuilder fromClause = new StringBuilder( "from com.occulue.bo.Department as department where " );
		
        Session session = null;
        Transaction tx = null;

        try
        {
            session = currentSession();
            tx      = currentTransaction(session);
            
// AIB : #getHibernateFindFromClause()
			fromClause.append( "department.departmentId = " + pk.getDepartmentId().toString() );
// ~AIB

	 		query = session.createQuery( fromClause.toString() );
	 		
	 		if ( query != null )
	 		{
                businessObject = new Department();
                businessObject.copy((Department)query.list().iterator().next());
	 		}

			commitTransaction(tx);						
		}
		catch( Throwable exc )
		{
			businessObject = null;
			exc.printStackTrace();
			throw new ProcessingException( "DepartmentDAO.findDepartment failed for primary key " + pk + " - " + exc );		
		}		
		finally
		{
			try
			{
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "DepartmentDAO.findDepartment - Hibernate failed to close the Session - " + exc );
			}
		}		    
				
        return( businessObject );
    }
        
	    /**
	     * returns a Collection of all Departments
	     * @return		ArrayList<Department>
	     * @exception   ProcessingException
	     */
	    public ArrayList<Department> findAllDepartment()
	    throws ProcessingException
	    {
			ArrayList<Department> list = new ArrayList<Department>();
			ArrayList<Department> refList = new ArrayList<Department>();
			Query query 							= null;		
			StringBuilder buf 						= new StringBuilder( "from com.occulue.bo.Department" );

			try
			{
				Session session = currentSession();

		 		query = session.createQuery( buf.toString() );
		 		
				if ( query != null )
				{
					list = (ArrayList<Department>)query.list();
					Department tmp = null;
					
	                for (Department listEntry : list)
	                {
	                    tmp = new Department();
	                    tmp.copyShallow(listEntry);
	                    refList.add(tmp);
	                }
				}
			}
			catch( Throwable exc )
			{
				exc.printStackTrace();		
				throw new ProcessingException( "DepartmentDAO.findAllDepartment failed - " + exc );		
			}		
			finally
			{
				try
				{
					closeSession();			
				}
				catch( Throwable exc )
				{		
					LOGGER.info( "DepartmentDAO.findAllDepartment - Hibernate failed to close the Session - " + exc );
				}		
			}
			
			if ( list.size() <= 0 )
			{
				LOGGER.info( "DepartmentDAO:findAllDepartments() - List is empty.");
			}
	        
			return( refList );		        
	    }
		

	    /**
	     * Inserts a new Department into the persistent store.
	     * @param       businessObject
	     * @return      newly persisted Department
	     * @exception   ProcessingException
	     */
	    public Department createDepartment( Department businessObject )
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
					LOGGER.info( "DepartmentDAO.createDepartment - Hibernate failed to rollback - " + exc1 );
				}
				exc.printStackTrace();			
				throw new ProcessingException( "DepartmentDAO.createDepartment failed - " + exc );
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
					LOGGER.info( "DepartmentDAO.createDepartment - Hibernate failed to close the Session - " + exc );
				}
			}		
			
	        // return the businessObject
	        return(  businessObject );
	    }
	    
    /**
     * Stores the provided Department to the persistent store.
     *
     * @param       businessObject
     * @return      Department	stored entity
     * @exception   ProcessingException 
     */
    public Department saveDepartment( Department businessObject )
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
				LOGGER.info( "DepartmentDAO.saveDepartment - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "DepartmentDAO.saveDepartment failed - " + exc );		
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
				LOGGER.info( "DepartmentDAO.saveDepartment - Hibernate failed to close the Session - " + exc );
			}
		}		    
        return( businessObject );
    }
    
    /**
    * Removes a Department from the persistent store.
    *
    * @param        pk		identity of object to remove
    * @exception    ProcessingException
    */
    public void deleteDepartment( DepartmentPrimaryKey pk ) 
    throws ProcessingException 
    {
	    Transaction tx 	= null;
	    Session session = null;
	    
    	try
    	{    	
			Department bo = findDepartment(pk);    	
			
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
				LOGGER.info( "DepartmentDAO.deleteDepartment - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "DepartmentDAO.deleteDepartment failed - " + exc );					
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
				LOGGER.info( "DepartmentDAO.deleteDepartment - Hibernate failed to close the Session - " + exc );
			}
		}
    }


// AIB : #outputDAOFindAllImplementations()
// ~AIB


//*****************************************************
// Attributes
//*****************************************************
	private static final Logger LOGGER 	= Logger.getLogger(Department.class.getName());

}
