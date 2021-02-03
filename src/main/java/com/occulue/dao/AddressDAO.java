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
 * Implements the Hibernate persistence processing for business entity Address.
 *
 * @author 
 */

// AIB : #getDAOClassDecl()
 public class AddressDAO extends BaseDAO
// ~AIB
{
    /**
     * default constructor
     */
    public AddressDAO()
    {
    }

    /**
     * Retrieves a Address from the persistent store, using the provided primary key. 
     * If no match is found, a null Address is returned.
     * <p>
     * @param       pk
     * @return      Address
     * @exception   ProcessingException
     */
    public Address findAddress( AddressPrimaryKey pk ) 
    throws ProcessingException
    {
        if (pk == null)
        {
            throw new ProcessingException("AddressDAO.findAddress(...) cannot have a null primary key argument");
        }
    
		Query query 	= null;		
		Address businessObject = null;
		
	    StringBuilder fromClause = new StringBuilder( "from com.occulue.bo.Address as address where " );
		
        Session session = null;
        Transaction tx = null;

        try
        {
            session = currentSession();
            tx      = currentTransaction(session);
            
// AIB : #getHibernateFindFromClause()
			fromClause.append( "address.addressId = " + pk.getAddressId().toString() );
// ~AIB

	 		query = session.createQuery( fromClause.toString() );
	 		
	 		if ( query != null )
	 		{
                businessObject = new Address();
                businessObject.copy((Address)query.list().iterator().next());
	 		}

			commitTransaction(tx);						
		}
		catch( Throwable exc )
		{
			businessObject = null;
			exc.printStackTrace();
			throw new ProcessingException( "AddressDAO.findAddress failed for primary key " + pk + " - " + exc );		
		}		
		finally
		{
			try
			{
				closeSession();			
			}
			catch( Throwable exc )
			{		
				LOGGER.info( "AddressDAO.findAddress - Hibernate failed to close the Session - " + exc );
			}
		}		    
				
        return( businessObject );
    }
        
	    /**
	     * returns a Collection of all Addresss
	     * @return		ArrayList<Address>
	     * @exception   ProcessingException
	     */
	    public ArrayList<Address> findAllAddress()
	    throws ProcessingException
	    {
			ArrayList<Address> list = new ArrayList<Address>();
			ArrayList<Address> refList = new ArrayList<Address>();
			Query query 							= null;		
			StringBuilder buf 						= new StringBuilder( "from com.occulue.bo.Address" );

			try
			{
				Session session = currentSession();

		 		query = session.createQuery( buf.toString() );
		 		
				if ( query != null )
				{
					list = (ArrayList<Address>)query.list();
					Address tmp = null;
					
	                for (Address listEntry : list)
	                {
	                    tmp = new Address();
	                    tmp.copyShallow(listEntry);
	                    refList.add(tmp);
	                }
				}
			}
			catch( Throwable exc )
			{
				exc.printStackTrace();		
				throw new ProcessingException( "AddressDAO.findAllAddress failed - " + exc );		
			}		
			finally
			{
				try
				{
					closeSession();			
				}
				catch( Throwable exc )
				{		
					LOGGER.info( "AddressDAO.findAllAddress - Hibernate failed to close the Session - " + exc );
				}		
			}
			
			if ( list.size() <= 0 )
			{
				LOGGER.info( "AddressDAO:findAllAddresss() - List is empty.");
			}
	        
			return( refList );		        
	    }
		

	    /**
	     * Inserts a new Address into the persistent store.
	     * @param       businessObject
	     * @return      newly persisted Address
	     * @exception   ProcessingException
	     */
	    public Address createAddress( Address businessObject )
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
					LOGGER.info( "AddressDAO.createAddress - Hibernate failed to rollback - " + exc1 );
				}
				exc.printStackTrace();			
				throw new ProcessingException( "AddressDAO.createAddress failed - " + exc );
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
					LOGGER.info( "AddressDAO.createAddress - Hibernate failed to close the Session - " + exc );
				}
			}		
			
	        // return the businessObject
	        return(  businessObject );
	    }
	    
    /**
     * Stores the provided Address to the persistent store.
     *
     * @param       businessObject
     * @return      Address	stored entity
     * @exception   ProcessingException 
     */
    public Address saveAddress( Address businessObject )
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
				LOGGER.info( "AddressDAO.saveAddress - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "AddressDAO.saveAddress failed - " + exc );		
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
				LOGGER.info( "AddressDAO.saveAddress - Hibernate failed to close the Session - " + exc );
			}
		}		    
        return( businessObject );
    }
    
    /**
    * Removes a Address from the persistent store.
    *
    * @param        pk		identity of object to remove
    * @exception    ProcessingException
    */
    public void deleteAddress( AddressPrimaryKey pk ) 
    throws ProcessingException 
    {
	    Transaction tx 	= null;
	    Session session = null;
	    
    	try
    	{    	
			Address bo = findAddress(pk);    	
			
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
				LOGGER.info( "AddressDAO.deleteAddress - Hibernate failed to rollback - " + exc1 );
			}
			exc.printStackTrace();			
			throw new ProcessingException( "AddressDAO.deleteAddress failed - " + exc );					
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
				LOGGER.info( "AddressDAO.deleteAddress - Hibernate failed to close the Session - " + exc );
			}
		}
    }


// AIB : #outputDAOFindAllImplementations()
// ~AIB


//*****************************************************
// Attributes
//*****************************************************
	private static final Logger LOGGER 	= Logger.getLogger(Address.class.getName());

}
