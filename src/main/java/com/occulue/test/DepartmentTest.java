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
 * Test Department class.
 *
 * @author    
 */
public class DepartmentTest
{

// constructors

    public DepartmentTest()
    {
    	LOGGER.setUseParentHandlers(false);	// only want to output to the provided LogHandler
    }

// test methods
    @Test
    /** 
     * Full Create-Read-Update-Delete of a Department, through a DepartmentTest.
     */
    public void testCRUD()
    throws Throwable
   {        
        try
        {
        	LOGGER.info( "**********************************************************" );
            LOGGER.info( "Beginning full test on DepartmentTest..." );
            
            testCreate();            
            testRead();        
            testUpdate();
            testGetAll();                
            testDelete();
            
            LOGGER.info( "Successfully ran a full test on DepartmentTest..." );
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
     * Tests creating a new Department.
     *
     * @return    Department
     */
    public Department testCreate()
    throws Throwable
    {
        Department businessObject = null;

    	{
	        LOGGER.info( "DepartmentTest:testCreate()" );
	        LOGGER.info( "-- Attempting to create a Department");
	
	        StringBuilder msg = new StringBuilder( "-- Failed to create a Department" );
	
	        try 
	        {            
	            businessObject = DepartmentBusinessDelegate.getDepartmentInstance().createDepartment( getNewBO() );
	            assertNotNull( businessObject, msg.toString() );
	
	            thePrimaryKey = (DepartmentPrimaryKey)businessObject.getDepartmentPrimaryKey();
	            assertNotNull( thePrimaryKey, msg.toString() + " Contains a null primary key" );
	
	            LOGGER.info( "-- Successfully created a Department with primary key" + thePrimaryKey );
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
     * Tests reading a Department.
     *
     * @return    Department  
     */
    public Department testRead()
    throws Throwable
    {
        LOGGER.info( "DepartmentTest:testRead()" );
        LOGGER.info( "-- Reading a previously created Department" );

        Department businessObject = null;
        StringBuilder msg = new StringBuilder( "-- Failed to read Department with primary key" );
        msg.append( thePrimaryKey );

        try
        {
            businessObject = DepartmentBusinessDelegate.getDepartmentInstance().getDepartment( thePrimaryKey );
            
            assertNotNull( businessObject,msg.toString() );

            LOGGER.info( "-- Successfully found Department " + businessObject.toString() );
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
     * Tests updating a Department.
     *
     * @return    Department
     */
    public Department testUpdate()
    throws Throwable
    {

        LOGGER.info( "DepartmentTest:testUpdate()" );
        LOGGER.info( "-- Attempting to update a Department." );

        StringBuilder msg = new StringBuilder( "Failed to update a Department : " );        
        Department businessObject = null;
    
        try
        {            
            businessObject = testCreate();
            
            assertNotNull( businessObject, msg.toString() );

            LOGGER.info( "-- Now updating the created Department." );
            
            // for use later on...
            thePrimaryKey = (DepartmentPrimaryKey)businessObject.getDepartmentPrimaryKey();
            
            DepartmentBusinessDelegate proxy = DepartmentBusinessDelegate.getDepartmentInstance();            
            businessObject = proxy.saveDepartment( businessObject );   
            
            assertNotNull( businessObject, msg.toString()  );

            LOGGER.info( "-- Successfully saved Department - " + businessObject.toString() );
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
     * Tests deleting a Department.
     */
    public void testDelete()
    throws Throwable
    {
        LOGGER.info( "DepartmentTest:testDelete()" );
        LOGGER.info( "-- Deleting a previously created Department." );
        
        try
        {
            DepartmentBusinessDelegate.getDepartmentInstance().delete( thePrimaryKey );
            
            LOGGER.info( "-- Successfully deleted Department with primary key " + thePrimaryKey );            
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( "-- Failed to delete Department with primary key " + thePrimaryKey );
            
            throw e;
        }
    }

    /** 
     * Tests getting all Departments.
     *
     * @return    Collection
     */
    public ArrayList<Department> testGetAll()
    throws Throwable
    {    
        LOGGER.info( "DepartmentTest:testGetAll() - Retrieving Collection of Departments:" );

        StringBuilder msg = new StringBuilder( "-- Failed to get all Department : " );        
        ArrayList<Department> collection  = null;

        try
        {
            // call the static get method on the DepartmentBusinessDelegate
            collection = DepartmentBusinessDelegate.getDepartmentInstance().getAllDepartment();

            if ( collection == null || collection.size() == 0 )
            {
                LOGGER.warning( unexpectedErrorMsg );
                LOGGER.warning( "-- " + msg.toString() + " Empty collection returned."  );
            }
            else
            {
	            // Now print out the values
	            Department currentBO  = null;            
	            Iterator<Department> iter = collection.iterator();
					
	            while( iter.hasNext() )
	            {
	                // Retrieve the businessObject   
	                currentBO = iter.next();
	                
	                assertNotNull( currentBO,"-- null value object in Collection." );
	                assertNotNull( currentBO.getDepartmentPrimaryKey(), "-- value object in Collection has a null primary key" );        
	
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
    
    public DepartmentTest setHandler( Handler handler ) {
    	this.handler = handler;
    	LOGGER.addHandler(handler);	// assign so the LOGGER can only output results to the Handler
    	return this;
    }
    
    /** 
     * Returns a new populate Department
     * 
     * @return    Department
     */    
    protected Department getNewBO()
    {
        Department newBO = new Department();

// AIB : \#defaultBOOutput() 
newBO.setName(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
// ~AIB

        return( newBO );
    }
    
// attributes 

    protected DepartmentPrimaryKey thePrimaryKey      = null;
	protected Properties frameworkProperties 			= null;
	private final Logger LOGGER = Logger.getLogger(Department.class.getName());
	private Handler handler = null;
	private String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
}
