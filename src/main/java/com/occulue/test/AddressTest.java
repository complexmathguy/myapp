/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.test;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

    import com.occulue.primarykey.*;
    import com.occulue.delegate.*;
    import com.occulue.bo.*;
    
/**
 * Test Address class.
 *
 * @author    
 */
public class AddressTest
{

// constructors

    public AddressTest()
    {
    	LOGGER.setUseParentHandlers(false);	// only want to output to the provided LogHandler
    }

// test methods
    @Test
    /** 
     * Full Create-Read-Update-Delete of a Address, through a AddressTest.
     */
    public void testCRUD()
    throws Throwable
   {        
        try
        {
        	LOGGER.info( "**********************************************************" );
            LOGGER.info( "Beginning full test on AddressTest..." );
            
            testCreate();            
            testRead();        
            testUpdate();
            testGetAll();                
            testDelete();
            
            LOGGER.info( "Successfully ran a full test on AddressTest..." );
            LOGGER.info( "**********************************************************" );
            LOGGER.info( "" );
        }
        catch( Throwable e )
        {
            throw e;
        }
        finally 
        {
        	if ( handler != null ) {
        		handler.flush();
        		LOGGER.removeHandler(handler);
        	}
        }
   }

    /** 
     * Tests creating a new Address.
     *
     * @return    Address
     */
    public Address testCreate()
    throws Throwable
    {
        Address businessObject = null;

    	{
	        LOGGER.info( "AddressTest:testCreate()" );
	        LOGGER.info( "-- Attempting to create a Address");
	
	        StringBuilder msg = new StringBuilder( "-- Failed to create a Address" );
	
	        try 
	        {            
	            businessObject = AddressBusinessDelegate.getAddressInstance().createAddress( getNewBO() );
	            assertNotNull( businessObject, msg.toString() );
	
	            thePrimaryKey = (AddressPrimaryKey)businessObject.getAddressPrimaryKey();
	            assertNotNull( thePrimaryKey, msg.toString() + " Contains a null primary key" );
	
	            LOGGER.info( "-- Successfully created a Address with primary key" + thePrimaryKey );
	        }
	        catch (Exception e) 
	        {
	            LOGGER.warning( unexpectedErrorMsg );
	            LOGGER.warning( msg.toString() + businessObject );
	            
	            throw e;
	        }
    	}
        return businessObject;
    }

    /** 
     * Tests reading a Address.
     *
     * @return    Address  
     */
    public Address testRead()
    throws Throwable
    {
        LOGGER.info( "AddressTest:testRead()" );
        LOGGER.info( "-- Reading a previously created Address" );

        Address businessObject = null;
        StringBuilder msg = new StringBuilder( "-- Failed to read Address with primary key" );
        msg.append( thePrimaryKey );

        try
        {
            businessObject = AddressBusinessDelegate.getAddressInstance().getAddress( thePrimaryKey );
            
            assertNotNull( businessObject,msg.toString() );

            LOGGER.info( "-- Successfully found Address " + businessObject.toString() );
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( msg.toString() + " : " + e );
            
            throw e;
        }

        return( businessObject );
    }

    /** 
     * Tests updating a Address.
     *
     * @return    Address
     */
    public Address testUpdate()
    throws Throwable
    {

        LOGGER.info( "AddressTest:testUpdate()" );
        LOGGER.info( "-- Attempting to update a Address." );

        StringBuilder msg = new StringBuilder( "Failed to update a Address : " );        
        Address businessObject = null;
    
        try
        {            
            businessObject = testCreate();
            
            assertNotNull( businessObject, msg.toString() );

            LOGGER.info( "-- Now updating the created Address." );
            
            // for use later on...
            thePrimaryKey = (AddressPrimaryKey)businessObject.getAddressPrimaryKey();
            
            AddressBusinessDelegate proxy = AddressBusinessDelegate.getAddressInstance();            
            businessObject = proxy.saveAddress( businessObject );   
            
            assertNotNull( businessObject, msg.toString()  );

            LOGGER.info( "-- Successfully saved Address - " + businessObject.toString() );
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( msg.toString() + " : primarykey-" + thePrimaryKey + " : businessObject-" +  businessObject + " : " + e );
            
            throw e;
        }

        return( businessObject );
    }

    /** 
     * Tests deleting a Address.
     */
    public void testDelete()
    throws Throwable
    {
        LOGGER.info( "AddressTest:testDelete()" );
        LOGGER.info( "-- Deleting a previously created Address." );
        
        try
        {
            AddressBusinessDelegate.getAddressInstance().delete( thePrimaryKey );
            
            LOGGER.info( "-- Successfully deleted Address with primary key " + thePrimaryKey );            
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( "-- Failed to delete Address with primary key " + thePrimaryKey );
            
            throw e;
        }
    }

    /** 
     * Tests getting all Addresss.
     *
     * @return    Collection
     */
    public ArrayList<Address> testGetAll()
    throws Throwable
    {    
        LOGGER.info( "AddressTest:testGetAll() - Retrieving Collection of Addresss:" );

        StringBuilder msg = new StringBuilder( "-- Failed to get all Address : " );        
        ArrayList<Address> collection  = null;

        try
        {
            // call the static get method on the AddressBusinessDelegate
            collection = AddressBusinessDelegate.getAddressInstance().getAllAddress();

            if ( collection == null || collection.size() == 0 )
            {
                LOGGER.warning( unexpectedErrorMsg );
                LOGGER.warning( "-- " + msg.toString() + " Empty collection returned."  );
            }
            else
            {
	            // Now print out the values
	            Address currentBO  = null;            
	            Iterator<Address> iter = collection.iterator();
					
	            while( iter.hasNext() )
	            {
	                // Retrieve the businessObject   
	                currentBO = iter.next();
	                
	                assertNotNull( currentBO,"-- null value object in Collection." );
	                assertNotNull( currentBO.getAddressPrimaryKey(), "-- value object in Collection has a null primary key" );        
	
	                LOGGER.info( " - " + currentBO.toString() );
	            }
            }
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( msg.toString() );
            
            throw e;
        }

        return( collection );
    }
    
    public AddressTest setHandler( Handler handler ) {
    	this.handler = handler;
    	LOGGER.addHandler(handler);	// assign so the LOGGER can only output results to the Handler
    	return this;
    }
    
    /** 
     * Returns a new populate Address
     * 
     * @return    Address
     */    
    protected Address getNewBO()
    {
        Address newBO = new Address();

// AIB : \#defaultBOOutput() 
newBO.setStreet(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setCity(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setState(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setZipCode(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
// ~AIB

        return( newBO );
    }
    
// attributes 

    protected AddressPrimaryKey thePrimaryKey      = null;
	protected Properties frameworkProperties 			= null;
	private final Logger LOGGER = Logger.getLogger(Address.class.getName());
	private Handler handler = null;
	private String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
}
