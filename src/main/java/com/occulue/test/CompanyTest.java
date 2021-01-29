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
 * Test Company class.
 *
 * @author    
 */
public class CompanyTest
{

// constructors

    public CompanyTest()
    {
    	LOGGER.setUseParentHandlers(false);	// only want to output to the provided LogHandler
    }

// test methods
    @Test
    /** 
     * Full Create-Read-Update-Delete of a Company, through a CompanyTest.
     */
    public void testCRUD()
    throws Throwable
   {        
        try
        {
        	LOGGER.info( "**********************************************************" );
            LOGGER.info( "Beginning full test on CompanyTest..." );
            
            testCreate();            
            testRead();        
            testUpdate();
            testGetAll();                
            testDelete();
            
            LOGGER.info( "Successfully ran a full test on CompanyTest..." );
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
     * Tests creating a new Company.
     *
     * @return    Company
     */
    public Company testCreate()
    throws Throwable
    {
        Company businessObject = null;

    	{
	        LOGGER.info( "CompanyTest:testCreate()" );
	        LOGGER.info( "-- Attempting to create a Company");
	
	        StringBuilder msg = new StringBuilder( "-- Failed to create a Company" );
	
	        try 
	        {            
	            businessObject = CompanyBusinessDelegate.getCompanyInstance().createCompany( getNewBO() );
	            assertNotNull( businessObject, msg.toString() );
	
	            thePrimaryKey = (CompanyPrimaryKey)businessObject.getCompanyPrimaryKey();
	            assertNotNull( thePrimaryKey, msg.toString() + " Contains a null primary key" );
	
	            LOGGER.info( "-- Successfully created a Company with primary key" + thePrimaryKey );
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
     * Tests reading a Company.
     *
     * @return    Company  
     */
    public Company testRead()
    throws Throwable
    {
        LOGGER.info( "CompanyTest:testRead()" );
        LOGGER.info( "-- Reading a previously created Company" );

        Company businessObject = null;
        StringBuilder msg = new StringBuilder( "-- Failed to read Company with primary key" );
        msg.append( thePrimaryKey );

        try
        {
            businessObject = CompanyBusinessDelegate.getCompanyInstance().getCompany( thePrimaryKey );
            
            assertNotNull( businessObject,msg.toString() );

            LOGGER.info( "-- Successfully found Company " + businessObject.toString() );
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
     * Tests updating a Company.
     *
     * @return    Company
     */
    public Company testUpdate()
    throws Throwable
    {

        LOGGER.info( "CompanyTest:testUpdate()" );
        LOGGER.info( "-- Attempting to update a Company." );

        StringBuilder msg = new StringBuilder( "Failed to update a Company : " );        
        Company businessObject = null;
    
        try
        {            
            businessObject = testCreate();
            
            assertNotNull( businessObject, msg.toString() );

            LOGGER.info( "-- Now updating the created Company." );
            
            // for use later on...
            thePrimaryKey = (CompanyPrimaryKey)businessObject.getCompanyPrimaryKey();
            
            CompanyBusinessDelegate proxy = CompanyBusinessDelegate.getCompanyInstance();            
            businessObject = proxy.saveCompany( businessObject );   
            
            assertNotNull( businessObject, msg.toString()  );

            LOGGER.info( "-- Successfully saved Company - " + businessObject.toString() );
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
     * Tests deleting a Company.
     */
    public void testDelete()
    throws Throwable
    {
        LOGGER.info( "CompanyTest:testDelete()" );
        LOGGER.info( "-- Deleting a previously created Company." );
        
        try
        {
            CompanyBusinessDelegate.getCompanyInstance().delete( thePrimaryKey );
            
            LOGGER.info( "-- Successfully deleted Company with primary key " + thePrimaryKey );            
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( "-- Failed to delete Company with primary key " + thePrimaryKey );
            
            throw e;
        }
    }

    /** 
     * Tests getting all Companys.
     *
     * @return    Collection
     */
    public ArrayList<Company> testGetAll()
    throws Throwable
    {    
        LOGGER.info( "CompanyTest:testGetAll() - Retrieving Collection of Companys:" );

        StringBuilder msg = new StringBuilder( "-- Failed to get all Company : " );        
        ArrayList<Company> collection  = null;

        try
        {
            // call the static get method on the CompanyBusinessDelegate
            collection = CompanyBusinessDelegate.getCompanyInstance().getAllCompany();

            if ( collection == null || collection.size() == 0 )
            {
                LOGGER.warning( unexpectedErrorMsg );
                LOGGER.warning( "-- " + msg.toString() + " Empty collection returned."  );
            }
            else
            {
	            // Now print out the values
	            Company currentBO  = null;            
	            Iterator<Company> iter = collection.iterator();
					
	            while( iter.hasNext() )
	            {
	                // Retrieve the businessObject   
	                currentBO = iter.next();
	                
	                assertNotNull( currentBO,"-- null value object in Collection." );
	                assertNotNull( currentBO.getCompanyPrimaryKey(), "-- value object in Collection has a null primary key" );        
	
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
    
    public CompanyTest setHandler( Handler handler ) {
    	this.handler = handler;
    	LOGGER.addHandler(handler);	// assign so the LOGGER can only output results to the Handler
    	return this;
    }
    
    /** 
     * Returns a new populate Company
     * 
     * @return    Company
     */    
    protected Company getNewBO()
    {
        Company newBO = new Company();

// AIB : \#defaultBOOutput() 
newBO.setName(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setEstablishedOn(
    java.util.Calendar.getInstance().getTime()
 );
newBO.setRevenue(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setType(
    CompanyType.getDefaultValue()
 );
newBO.setIndustry(
    Industry.getDefaultValue()
 );
// ~AIB

        return( newBO );
    }
    
// attributes 

    protected CompanyPrimaryKey thePrimaryKey      = null;
	protected Properties frameworkProperties 			= null;
	private final Logger LOGGER = Logger.getLogger(Company.class.getName());
	private Handler handler = null;
	private String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
}
