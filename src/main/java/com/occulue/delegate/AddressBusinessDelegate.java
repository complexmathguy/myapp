/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.delegate;

import java.util.*;
import java.util.HashMap;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

    import com.occulue.primarykey.*;
    import com.occulue.dao.*;
    import com.occulue.bo.*;
    
import com.occulue.exception.*;

/**
 * Address business delegate class.
 * <p>
 * This class implements the Business Delegate design pattern for the purpose of:
 * <ol>
 * <li>Reducing coupling between the business tier and a client of the business tier by hiding all business-tier implementation details</li>
 * <li>Improving the available of Address related services in the case of a Address business related service failing.</li>
 * <li>Exposes a simpler, uniform Address interface to the business tier, making it easy for clients to consume a simple Java object.</li>
 * <li>Hides the communication protocol that may be required to fulfill Address business related services.</li>
 * </ol>
 * <p>
 * @author 
 */
public class AddressBusinessDelegate 
extends BaseBusinessDelegate
{
//************************************************************************
// Public Methods
//************************************************************************
    /** 
     * Default Constructor 
     */
    public AddressBusinessDelegate() 
	{
	}

        
   /**
	* Address Business Delegate Factory Method
	*
	* Returns a singleton instance of AddressBusinessDelegate().
	* All methods are expected to be self-sufficient.
	*
	* @return 	AddressBusinessDelegate
	*/
	public static AddressBusinessDelegate getAddressInstance()
	{
	    if ( singleton == null )
	    {
	    	singleton = new AddressBusinessDelegate();
	    }
	    
	    return( singleton );
	}
 
    /**
     * Method to retrieve the Address via an AddressPrimaryKey.
     * @param 	key
     * @return 	Address
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    public Address getAddress( AddressPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "AddressBusinessDelegate:getAddress - ";
        if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        Address returnBO = null;
                
        AddressDAO dao = getAddressDAO();
        
        try
        {
            returnBO = dao.findAddress( key );
        }
        catch( Exception exc )
        {
            String errMsg = "AddressBusinessDelegate:getAddress( AddressPrimaryKey key ) - unable to locate Address with key " + key.toString() + " - " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseAddressDAO( dao );
        }        
        
        return returnBO;
    }


    /**
     * Method to retrieve a collection of all Addresss
     *
     * @return 	ArrayList<Address> 
     * @exception ProcessingException Thrown if any problems
     */
    public ArrayList<Address> getAllAddress() 
    throws ProcessingException
    {
    	String msgPrefix				= "AddressBusinessDelegate:getAllAddress() - ";
        ArrayList<Address> array	= null;
        
        AddressDAO dao = getAddressDAO();
    
        try
        {
            array = dao.findAllAddress();
        }
        catch( Exception exc )
        {
            String errMsg = msgPrefix + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseAddressDAO( dao );
        }        
        
        return array;
    }

   /**
    * Creates the provided BO.
    * @param		businessObject 	Address
    * @return       Address
    * @exception    ProcessingException
    * @exception	IllegalArgumentException
    */
	public Address createAddress( Address businessObject )
    throws ProcessingException, IllegalArgumentException
    {
		String msgPrefix = "AddressBusinessDelegate:createAddress - ";
		
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        // return value once persisted
        AddressDAO dao  = getAddressDAO();
        
        try
        {
            businessObject = dao.createAddress( businessObject );
        }
        catch (Exception exc)
        {
            String errMsg = "AddressBusinessDelegate:createAddress() - Unable to create Address" + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseAddressDAO( dao );
        }        
        
        return( businessObject );
        
    }

   /**
    * Saves the underlying BO.
    * @param		businessObject		Address
    * @return       what was just saved
    * @exception    ProcessingException
    * @exception  	IllegalArgumentException
    */
    public Address saveAddress( Address businessObject ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "AddressBusinessDelegate:saveAddress - ";
    	
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
                
        // --------------------------------
        // If the businessObject has a key, find it and apply the businessObject
        // --------------------------------
        AddressPrimaryKey key = businessObject.getAddressPrimaryKey();
                    
        if ( key != null )
        {
            AddressDAO dao = getAddressDAO();

            try
            {                    
                businessObject = (Address)dao.saveAddress( businessObject );
            }
            catch (Exception exc)
            {
                String errMsg = "AddressBusinessDelegate:saveAddress() - Unable to save Address" + exc;
                LOGGER.warning( errMsg );
                throw new ProcessingException( errMsg  );
            }
            finally
            {
                releaseAddressDAO( dao );
            }
            
        }
        else
        {
            String errMsg = "AddressBusinessDelegate:saveAddress() - Unable to create Address due to it having a null AddressPrimaryKey."; 
            
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
		        
        return( businessObject );
        
    }
   
   /**
    * Deletes the associatied value object using the provided primary key.
    * @param		key 	AddressPrimaryKey    
    * @exception 	ProcessingException
    */
    public void delete( AddressPrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {    	
    	String msgPrefix = "AddressBusinessDelegate:saveAddress - ";
    	
		if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        AddressDAO dao  = getAddressDAO();

        try
        {                    
            dao.deleteAddress( key );
        }
        catch (Exception exc)
        {
            String errMsg = msgPrefix + "Unable to delete Address using key = "  + key + ". " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseAddressDAO( dao );
        }
        		
        return;
    }
        

// business methods
    /**
     * Returns the Address specific DAO.
     *
     * @return      Address DAO
     */
    public AddressDAO getAddressDAO()
    {
        return( new com.occulue.dao.AddressDAO() ); 
    }

    /**
     * Release the AddressDAO back to the FrameworkDAOFactory
     */
    public void releaseAddressDAO( com.occulue.dao.AddressDAO dao )
    {
        dao = null;
    }

// AIB : #getBusinessMethodImplementations( $classObject.getName() $classObject $classObject.getBusinessMethods() $classObject.getInterfaces() )
// ~AIB
     
//************************************************************************
// Attributes
//************************************************************************

   /**
    * Singleton instance
    */
    protected static AddressBusinessDelegate singleton = null;
    private static final Logger LOGGER = Logger.getLogger(Address.class.getName());
    
}



