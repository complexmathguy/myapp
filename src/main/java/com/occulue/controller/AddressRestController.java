/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

    import com.occulue.primarykey.*;
    import com.occulue.delegate.*;
    import com.occulue.bo.*;
    
/** 
 * Implements Struts action processing for business entity Address.
 *
 * @author 
 */
@RestController
@RequestMapping("/Address")
public class AddressRestController extends BaseSpringRestController
{

    /**
     * Handles saving a Address BO.  if not key provided, calls create, otherwise calls save
     * @param		Address address
     * @return		Address
     */
	@RequestMapping("/save")
    public Address save( @RequestBody Address address )
    {
    	// assign locally
    	this.address = address;
    	
        if ( hasPrimaryKey() )
        {
            return (update());
        }
        else
        {
            return (create());
        }
    }

    /**
     * Handles deleting a Address BO
     * @param		Long addressId
     * @param 		Long[] childIds
     * @return		boolean
     */
    @RequestMapping("/delete")    
    public boolean delete( @RequestParam(value="address.addressId", required=false) Long addressId, 
    						@RequestParam(value="childIds", required=false) Long[] childIds )
    {                
        try
        {
        	AddressBusinessDelegate delegate = AddressBusinessDelegate.getAddressInstance();
        	
        	if ( childIds == null || childIds.length == 0 )
        	{
        		delegate.delete( new AddressPrimaryKey( addressId ) );
        		LOGGER.info( "AddressController:delete() - successfully deleted Address with key " + address.getAddressPrimaryKey().valuesAsCollection() );
        	}
        	else
        	{
        		for ( Long id : childIds )
        		{
        			try
        			{
        				delegate.delete( new AddressPrimaryKey( id ) );
        			}
	                catch( Throwable exc )
	                {
	                	LOGGER.info( "AddressController:delete() - " + exc.getMessage() );
	                	return false;
	                }
        		}
        	}
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "AddressController:delete() - " + exc.getMessage() );
        	return false;        	
        }
        
        return true;
	}        
	
    /**
     * Handles loading a Address BO
     * @param		Long addressId
     * @return		Address
     */    
    @RequestMapping("/load")
    public Address load( @RequestParam(value="address.addressId", required=true) Long addressId )
    {    	
        AddressPrimaryKey pk = null;

    	try
        {  
    		System.out.println( "\n\n****Address.load pk is " + addressId );
        	if ( addressId != null )
        	{
        		pk = new AddressPrimaryKey( addressId );
        		
        		// load the Address
	            this.address = AddressBusinessDelegate.getAddressInstance().getAddress( pk );
	            
	            LOGGER.info( "AddressController:load() - successfully loaded - " + this.address.toString() );             
			}
			else
			{
	            LOGGER.info( "AddressController:load() - unable to locate the primary key as an attribute or a selection for - " + address.toString() );
	            return null;
			}	            
        }
        catch( Throwable exc )
        {
            LOGGER.info( "AddressController:load() - failed to load Address using Id " + addressId + ", " + exc.getMessage() );
            return null;
        }

        return address;

    }

    /**
     * Handles loading all Address business objects
     * @return		List<Address>
     */
    @RequestMapping("/loadAll")
    public List<Address> loadAll()
    {                
        List<Address> addressList = null;
        
    	try
        {                        
            // load the Address
            addressList = AddressBusinessDelegate.getAddressInstance().getAllAddress();
            
            if ( addressList != null )
                LOGGER.info(  "AddressController:loadAllAddress() - successfully loaded all Addresss" );
        }
        catch( Throwable exc )
        {
            LOGGER.info(  "AddressController:loadAll() - failed to load all Addresss - " + exc.getMessage() );
        	return null;
            
        }

        return addressList;
                            
    }


// findAllBy methods




    /**
     * Handles creating a Address BO
     * @return		Address
     */
    protected Address create()
    {
        try
        {       
			this.address = AddressBusinessDelegate.getAddressInstance().createAddress( address );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "AddressController:create() - exception Address - " + exc.getMessage());        	
        	return null;
        }
        
        return this.address;
    }

    /**
     * Handles updating a Address BO
     * @return		Address
     */    
    protected Address update()
    {
    	// store provided data
        Address tmp = address;

        // load actual data from db
    	load();
    	
    	// copy provided data into actual data
    	address.copyShallow( tmp );
    	
        try
        {                        	        
			// create the AddressBusiness Delegate            
			AddressBusinessDelegate delegate = AddressBusinessDelegate.getAddressInstance();
            this.address = delegate.saveAddress( address );
            
            if ( this.address != null )
                LOGGER.info( "AddressController:update() - successfully updated Address - " + address.toString() );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "AddressController:update() - successfully update Address - " + exc.getMessage());        	
        	return null;
        }
        
        return this.address;
        
    }


    /**
     * Returns true if the address is non-null and has it's primary key field(s) set
     * @return		boolean
     */
    protected boolean hasPrimaryKey()
    {
    	boolean hasPK = false;

		if ( address != null && address.getAddressPrimaryKey().hasBeenAssigned() == true )
		   hasPK = true;
		
		return( hasPK );
    }

    protected Address load()
    {
    	return( load( new Long( address.getAddressPrimaryKey().getFirstKey().toString() ) ));
    }

//************************************************************************    
// Attributes
//************************************************************************
    protected Address address = null;
    private static final Logger LOGGER = Logger.getLogger(Address.class.getName());
    
}


