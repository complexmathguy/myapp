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
 * Implements the Hibernate persistence processing for business entity Division.
 *
 * @author 
 */

// AIB : #getDAOClassDecl()
 public class DivisionDAO extends BaseDAO
// ~AIB
{
    /**
     * default constructor
     */
    public DivisionDAO()
    {
    }

    /**
     * Retrieves a Division from the persistent store, using the provided primary key. 
     * If no match is found, a null Division is returned.
     * <p>
     * @param       pk
     * @return      Division
     * @exception   ProcessingException
     */
    public Division findDivision( DivisionPrimaryKey pk ) 
    throws ProcessingException
    {
        if (pk == null)
        {
            throw new ProcessingException("DivisionDAO.findDivision(...) cannot have a null primary key argument");
        }
    
		Query query 	= null;		
		Division businessObject = null;
		
	    StringBuilder fromClause = new StringBuilder( "from com.occulue.bo.Division as division where " );
		
        Session session = null;
        Transaction tx = null;

        try
        {
            session = currentSession();
            tx      = currentTransaction(session);
            
// AIB : #getHibernateFindFromClause()
			fromClause.append( "division.divisionId = " + pk.getDivisionId().toString() );
// ~AIB

	 		query = session.createQuery( fromClause.toString() );
	 		
	 		if ( query != null )
	 		{
                businessObject = new Division();
                businessObject.copy((Division)query.list().iterator().next());
	 		}

			commitTransaction(tx);						
		}
		catch( Throwable exc )
		{
			businessObject = null;
			exc.printStackTrace();
			throw new ProcessingException( "DivisionDAO.findDivision failed for primary key " + pk + " - " + exc );		
		}		
		finally
		{
			try
			{
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "DivisionDAO.findDivision - Hibernate failed to close the Session - " + exc );
			}
		}		    
				
        return( businessObject );
    }
        
	    /**
	     * returns a Collection of all Divisions
	     * @return		ArrayList<Division>
	     * @exception   ProcessingException
	     */
	    public ArrayList<Division> findAllDivision()
	    throws ProcessingException
	    {
			ArrayList<Division> list = new ArrayList<Division>();
			ArrayList<Division> refList = new ArrayList<Division>();
			Query query 							= null;		
			StringBuilder buf 						= new StringBuilder( "from com.occulue.bo.Division" );

			try
			{
				Session session = currentSession();

		 		query = session.createQuery( buf.toString() );
		 		
				if ( query != null )
				{
					list = (ArrayList<Division>)query.list();
					Division tmp = null;
					
	                for (Division listEntry : list)
	                {
	                    tmp = new Division();
	                    tmp.copyShallow(listEntry);
	                    refList.add(tmp);
	                }
				}
			}
			catch( Throwable exc )
			{
				exc.printStackTrace();		
				throw new ProcessingException( "DivisionDAO.findAllDivision failed - " + exc );		
			}		
			finally
			{
				try
				{
					closeSession();			
				}
				catch( Throwable exc )
				{		
					LOGGER.info( "DivisionDAO.findAllDivision - Hibernate failed to close the Session - " + exc );
				}		
			}
			
			if ( list.size() <= 0 )
			{
				LOGGER.info( "DivisionDAO:findAllDivisions() - List is empty.");
			}
	        
			return( refList );		        
	    }
		

	    /**
	     * Inserts a new Division into the persistent store.
	     * @param       businessObject
	     * @return      newly persisted Division
	     * @exception   ProcessingException
	     */
	    public Division createDivision( Division businessObject )
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
					LOGGER.info( "DivisionDAO.createDivision - Hibernate failed to rollback - " + exc1 );
				}
				exc.printStackTrace();			
				throw new ProcessingException( "DivisionDAO.createDivision failed - " + exc );
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
					LOGGER.info( "DivisionDAO.createDivision - Hibernate failed to close the Session - " + exc );
				}
			}		
			
	        // return the businessObject
	        return(  businessObject );
	    }
	    
    /**
     * Stores the provided Division to the persistent store.
     *
     * @param       businessObject
     * @return      Division	stored entity
     * @exception   ProcessingException 
     */
    public Division saveDivision( Division businessObject )
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
				LOGGER.info( "DivisionDAO.saveDivision - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "DivisionDAO.saveDivision failed - " + exc );		
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
				LOGGER.info( "DivisionDAO.saveDivision - Hibernate failed to close the Session - " + exc );
			}
		}		    
        return( businessObject );
    }
    
    /**
    * Removes a Division from the persistent store.
    *
    * @param        pk		identity of object to remove
    * @exception    ProcessingException
    */
    public void deleteDivision( DivisionPrimaryKey pk ) 
    throws ProcessingException 
    {
	    Transaction tx 	= null;
	    Session session = null;
	    
    	try
    	{    	
			Division bo = findDivision(pk);    	
			
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
				LOGGER.info( "DivisionDAO.deleteDivision - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "DivisionDAO.deleteDivision failed - " + exc );					
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
				LOGGER.info( "DivisionDAO.deleteDivision - Hibernate failed to close the Session - " + exc );
			}
		}
    }


// AIB : #outputDAOFindAllImplementations()
// ~AIB


//*****************************************************
// Attributes
//*****************************************************
	private static final Logger LOGGER 	= Logger.getLogger(Division.class.getName());

}
