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
 * Test Division class.
 *
 * @author    
 */
public class DivisionTest
{

// constructors

    public DivisionTest()
    {
    	LOGGER.setUseParentHandlers(false);	// only want to output to the provided LogHandler
    }

// test methods
    @Test
    /** 
     * Full Create-Read-Update-Delete of a Division, through a DivisionTest.
     */
    public void testCRUD()
    throws Throwable
   {        
        try
        {
        	LOGGER.info( "**********************************************************" );
            LOGGER.info( "Beginning full test on DivisionTest..." );
            
            testCreate();            
            testRead();        
            testUpdate();
            testGetAll();                
            testDelete();
            
            LOGGER.info( "Successfully ran a full test on DivisionTest..." );
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
     * Tests creating a new Division.
     *
     * @return    Division
     */
    public Division testCreate()
    throws Throwable
    {
        Division businessObject = null;

    	{
	        LOGGER.info( "DivisionTest:testCreate()" );
	        LOGGER.info( "-- Attempting to create a Division");
	
	        StringBuilder msg = new StringBuilder( "-- Failed to create a Division" );
	
	        try 
	        {            
	            businessObject = DivisionBusinessDelegate.getDivisionInstance().createDivision( getNewBO() );
	            assertNotNull( businessObject, msg.toString() );
	
	            thePrimaryKey = (DivisionPrimaryKey)businessObject.getDivisionPrimaryKey();
	            assertNotNull( thePrimaryKey, msg.toString() + " Contains a null primary key" );
	
	            LOGGER.info( "-- Successfully created a Division with primary key" + thePrimaryKey );
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
     * Tests reading a Division.
     *
     * @return    Division  
     */
    public Division testRead()
    throws Throwable
    {
        LOGGER.info( "DivisionTest:testRead()" );
        LOGGER.info( "-- Reading a previously created Division" );

        Division businessObject = null;
        StringBuilder msg = new StringBuilder( "-- Failed to read Division with primary key" );
        msg.append( thePrimaryKey );

        try
        {
            businessObject = DivisionBusinessDelegate.getDivisionInstance().getDivision( thePrimaryKey );
            
            assertNotNull( businessObject,msg.toString() );

            LOGGER.info( "-- Successfully found Division " + businessObject.toString() );
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
     * Tests updating a Division.
     *
     * @return    Division
     */
    public Division testUpdate()
    throws Throwable
    {

        LOGGER.info( "DivisionTest:testUpdate()" );
        LOGGER.info( "-- Attempting to update a Division." );

        StringBuilder msg = new StringBuilder( "Failed to update a Division : " );        
        Division businessObject = null;
    
        try
        {            
            businessObject = testCreate();
            
            assertNotNull( businessObject, msg.toString() );

            LOGGER.info( "-- Now updating the created Division." );
            
            // for use later on...
            thePrimaryKey = (DivisionPrimaryKey)businessObject.getDivisionPrimaryKey();
            
            DivisionBusinessDelegate proxy = DivisionBusinessDelegate.getDivisionInstance();            
            businessObject = proxy.saveDivision( businessObject );   
            
            assertNotNull( businessObject, msg.toString()  );

            LOGGER.info( "-- Successfully saved Division - " + businessObject.toString() );
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
     * Tests deleting a Division.
     */
    public void testDelete()
    throws Throwable
    {
        LOGGER.info( "DivisionTest:testDelete()" );
        LOGGER.info( "-- Deleting a previously created Division." );
        
        try
        {
            DivisionBusinessDelegate.getDivisionInstance().delete( thePrimaryKey );
            
            LOGGER.info( "-- Successfully deleted Division with primary key " + thePrimaryKey );            
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( "-- Failed to delete Division with primary key " + thePrimaryKey );
            
            throw e;
        }
    }

    /** 
     * Tests getting all Divisions.
     *
     * @return    Collection
     */
    public ArrayList<Division> testGetAll()
    throws Throwable
    {    
        LOGGER.info( "DivisionTest:testGetAll() - Retrieving Collection of Divisions:" );

        StringBuilder msg = new StringBuilder( "-- Failed to get all Division : " );        
        ArrayList<Division> collection  = null;

        try
        {
            // call the static get method on the DivisionBusinessDelegate
            collection = DivisionBusinessDelegate.getDivisionInstance().getAllDivision();

            if ( collection == null || collection.size() == 0 )
            {
                LOGGER.warning( unexpectedErrorMsg );
                LOGGER.warning( "-- " + msg.toString() + " Empty collection returned."  );
            }
            else
            {
	            // Now print out the values
	            Division currentBO  = null;            
	            Iterator<Division> iter = collection.iterator();
					
	            while( iter.hasNext() )
	            {
	                // Retrieve the businessObject   
	                currentBO = iter.next();
	                
	                assertNotNull( currentBO,"-- null value object in Collection." );
	                assertNotNull( currentBO.getDivisionPrimaryKey(), "-- value object in Collection has a null primary key" );        
	
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
    
    public DivisionTest setHandler( Handler handler ) {
    	this.handler = handler;
    	LOGGER.addHandler(handler);	// assign so the LOGGER can only output results to the Handler
    	return this;
    }
    
    /** 
     * Returns a new populate Division
     * 
     * @return    Division
     */    
    protected Division getNewBO()
    {
        Division newBO = new Division();

// AIB : \#defaultBOOutput() 
newBO.setName(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
// ~AIB

        return( newBO );
    }
    
// attributes 

    protected DivisionPrimaryKey thePrimaryKey      = null;
	protected Properties frameworkProperties 			= null;
	private final Logger LOGGER = Logger.getLogger(Division.class.getName());
	private Handler handler = null;
	private String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
}
