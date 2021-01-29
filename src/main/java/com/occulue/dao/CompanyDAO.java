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
 * Implements the Hibernate persistence processing for business entity Company.
 *
 * @author 
 */

// AIB : #getDAOClassDecl()
 public class CompanyDAO extends BaseDAO
// ~AIB
{
    /**
     * default constructor
     */
    public CompanyDAO()
    {
    }

    /**
     * Retrieves a Company from the persistent store, using the provided primary key. 
     * If no match is found, a null Company is returned.
     * <p>
     * @param       pk
     * @return      Company
     * @exception   ProcessingException
     */
    public Company findCompany( CompanyPrimaryKey pk ) 
    throws ProcessingException
    {
        if (pk == null)
        {
            throw new ProcessingException("CompanyDAO.findCompany(...) cannot have a null primary key argument");
        }
    
		Query query 	= null;		
		Company businessObject = null;
		
	    StringBuilder fromClause = new StringBuilder( "from com.occulue.bo.Company as company where " );
		
        Session session = null;
        Transaction tx = null;

        try
        {
            session = currentSession();
            tx      = currentTransaction(session);
            
// AIB : #getHibernateFindFromClause()
			fromClause.append( "company.companyId = " + pk.getCompanyId().toString() );
// ~AIB

	 		query = session.createQuery( fromClause.toString() );
	 		
	 		if ( query != null )
	 		{
                businessObject = new Company();
                businessObject.copy((Company)query.list().iterator().next());
	 		}

			commitTransaction(tx);						
		}
		catch( Throwable exc )
		{
			businessObject = null;
			exc.printStackTrace();
			throw new ProcessingException( "CompanyDAO.findCompany failed for primary key " + pk + " - " + exc );		
		}		
		finally
		{
			try
			{
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "CompanyDAO.findCompany - Hibernate failed to close the Session - " + exc );
			}
		}		    
				
        return( businessObject );
    }
        
	    /**
	     * returns a Collection of all Companys
	     * @return		ArrayList<Company>
	     * @exception   ProcessingException
	     */
	    public ArrayList<Company> findAllCompany()
	    throws ProcessingException
	    {
			ArrayList<Company> list = new ArrayList<Company>();
			ArrayList<Company> refList = new ArrayList<Company>();
			Query query 							= null;		
			StringBuilder buf 						= new StringBuilder( "from com.occulue.bo.Company" );

			try
			{
				Session session = currentSession();

		 		query = session.createQuery( buf.toString() );
		 		
				if ( query != null )
				{
					list = (ArrayList<Company>)query.list();
					Company tmp = null;
					
	                for (Company listEntry : list)
	                {
	                    tmp = new Company();
	                    tmp.copyShallow(listEntry);
	                    refList.add(tmp);
	                }
				}
			}
			catch( Throwable exc )
			{
				exc.printStackTrace();		
				throw new ProcessingException( "CompanyDAO.findAllCompany failed - " + exc );		
			}		
			finally
			{
				try
				{
					closeSession();			
				}
				catch( Throwable exc )
				{		
					LOGGER.info( "CompanyDAO.findAllCompany - Hibernate failed to close the Session - " + exc );
				}		
			}
			
			if ( list.size() <= 0 )
			{
				LOGGER.info( "CompanyDAO:findAllCompanys() - List is empty.");
			}
	        
			return( refList );		        
	    }
		

	    /**
	     * Inserts a new Company into the persistent store.
	     * @param       businessObject
	     * @return      newly persisted Company
	     * @exception   ProcessingException
	     */
	    public Company createCompany( Company businessObject )
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
					LOGGER.info( "CompanyDAO.createCompany - Hibernate failed to rollback - " + exc1 );
				}
				exc.printStackTrace();			
				throw new ProcessingException( "CompanyDAO.createCompany failed - " + exc );
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
					LOGGER.info( "CompanyDAO.createCompany - Hibernate failed to close the Session - " + exc );
				}
			}		
			
	        // return the businessObject
	        return(  businessObject );
	    }
	    
    /**
     * Stores the provided Company to the persistent store.
     *
     * @param       businessObject
     * @return      Company	stored entity
     * @exception   ProcessingException 
     */
    public Company saveCompany( Company businessObject )
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
				LOGGER.info( "CompanyDAO.saveCompany - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "CompanyDAO.saveCompany failed - " + exc );		
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
				LOGGER.info( "CompanyDAO.saveCompany - Hibernate failed to close the Session - " + exc );
			}
		}		    
        return( businessObject );
    }
    
    /**
    * Removes a Company from the persistent store.
    *
    * @param        pk		identity of object to remove
    * @exception    ProcessingException
    */
    public void deleteCompany( CompanyPrimaryKey pk ) 
    throws ProcessingException 
    {
	    Transaction tx 	= null;
	    Session session = null;
	    
    	try
    	{    	
			Company bo = findCompany(pk);    	
			
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
				LOGGER.info( "CompanyDAO.deleteCompany - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "CompanyDAO.deleteCompany failed - " + exc );					
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
				LOGGER.info( "CompanyDAO.deleteCompany - Hibernate failed to close the Session - " + exc );
			}
		}
    }


// AIB : #outputDAOFindAllImplementations()
// ~AIB


//*****************************************************
// Attributes
//*****************************************************
	private static final Logger LOGGER 	= Logger.getLogger(Company.class.getName());

}
