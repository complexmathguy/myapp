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
 * Test Employee class.
 *
 * @author    
 */
public class EmployeeTest
{

// constructors

    public EmployeeTest()
    {
    	LOGGER.setUseParentHandlers(false);	// only want to output to the provided LogHandler
    }

// test methods
    @Test
    /** 
     * Full Create-Read-Update-Delete of a Employee, through a EmployeeTest.
     */
    public void testCRUD()
    throws Throwable
   {        
        try
        {
        	LOGGER.info( "**********************************************************" );
            LOGGER.info( "Beginning full test on EmployeeTest..." );
            
            testCreate();            
            testRead();        
            testUpdate();
            testGetAll();                
            testDelete();
            
            LOGGER.info( "Successfully ran a full test on EmployeeTest..." );
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
     * Tests creating a new Employee.
     *
     * @return    Employee
     */
    public Employee testCreate()
    throws Throwable
    {
        Employee businessObject = null;

    	{
	        LOGGER.info( "EmployeeTest:testCreate()" );
	        LOGGER.info( "-- Attempting to create a Employee");
	
	        StringBuilder msg = new StringBuilder( "-- Failed to create a Employee" );
	
	        try 
	        {            
	            businessObject = EmployeeBusinessDelegate.getEmployeeInstance().createEmployee( getNewBO() );
	            assertNotNull( businessObject, msg.toString() );
	
	            thePrimaryKey = (EmployeePrimaryKey)businessObject.getEmployeePrimaryKey();
	            assertNotNull( thePrimaryKey, msg.toString() + " Contains a null primary key" );
	
	            LOGGER.info( "-- Successfully created a Employee with primary key" + thePrimaryKey );
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
     * Tests reading a Employee.
     *
     * @return    Employee  
     */
    public Employee testRead()
    throws Throwable
    {
        LOGGER.info( "EmployeeTest:testRead()" );
        LOGGER.info( "-- Reading a previously created Employee" );

        Employee businessObject = null;
        StringBuilder msg = new StringBuilder( "-- Failed to read Employee with primary key" );
        msg.append( thePrimaryKey );

        try
        {
            businessObject = EmployeeBusinessDelegate.getEmployeeInstance().getEmployee( thePrimaryKey );
            
            assertNotNull( businessObject,msg.toString() );

            LOGGER.info( "-- Successfully found Employee " + businessObject.toString() );
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
     * Tests updating a Employee.
     *
     * @return    Employee
     */
    public Employee testUpdate()
    throws Throwable
    {

        LOGGER.info( "EmployeeTest:testUpdate()" );
        LOGGER.info( "-- Attempting to update a Employee." );

        StringBuilder msg = new StringBuilder( "Failed to update a Employee : " );        
        Employee businessObject = null;
    
        try
        {            
            businessObject = testCreate();
            
            assertNotNull( businessObject, msg.toString() );

            LOGGER.info( "-- Now updating the created Employee." );
            
            // for use later on...
            thePrimaryKey = (EmployeePrimaryKey)businessObject.getEmployeePrimaryKey();
            
            EmployeeBusinessDelegate proxy = EmployeeBusinessDelegate.getEmployeeInstance();            
            businessObject = proxy.saveEmployee( businessObject );   
            
            assertNotNull( businessObject, msg.toString()  );

            LOGGER.info( "-- Successfully saved Employee - " + businessObject.toString() );
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
     * Tests deleting a Employee.
     */
    public void testDelete()
    throws Throwable
    {
        LOGGER.info( "EmployeeTest:testDelete()" );
        LOGGER.info( "-- Deleting a previously created Employee." );
        
        try
        {
            EmployeeBusinessDelegate.getEmployeeInstance().delete( thePrimaryKey );
            
            LOGGER.info( "-- Successfully deleted Employee with primary key " + thePrimaryKey );            
        }
        catch ( Throwable e )
        {
            LOGGER.warning( unexpectedErrorMsg );
            LOGGER.warning( "-- Failed to delete Employee with primary key " + thePrimaryKey );
            
            throw e;
        }
    }

    /** 
     * Tests getting all Employees.
     *
     * @return    Collection
     */
    public ArrayList<Employee> testGetAll()
    throws Throwable
    {    
        LOGGER.info( "EmployeeTest:testGetAll() - Retrieving Collection of Employees:" );

        StringBuilder msg = new StringBuilder( "-- Failed to get all Employee : " );        
        ArrayList<Employee> collection  = null;

        try
        {
            // call the static get method on the EmployeeBusinessDelegate
            collection = EmployeeBusinessDelegate.getEmployeeInstance().getAllEmployee();

            if ( collection == null || collection.size() == 0 )
            {
                LOGGER.warning( unexpectedErrorMsg );
                LOGGER.warning( "-- " + msg.toString() + " Empty collection returned."  );
            }
            else
            {
	            // Now print out the values
	            Employee currentBO  = null;            
	            Iterator<Employee> iter = collection.iterator();
					
	            while( iter.hasNext() )
	            {
	                // Retrieve the businessObject   
	                currentBO = iter.next();
	                
	                assertNotNull( currentBO,"-- null value object in Collection." );
	                assertNotNull( currentBO.getEmployeePrimaryKey(), "-- value object in Collection has a null primary key" );        
	
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
    
    public EmployeeTest setHandler( Handler handler ) {
    	this.handler = handler;
    	LOGGER.addHandler(handler);	// assign so the LOGGER can only output results to the Handler
    	return this;
    }
    
    /** 
     * Returns a new populate Employee
     * 
     * @return    Employee
     */    
    protected Employee getNewBO()
    {
        Employee newBO = new Employee();

// AIB : \#defaultBOOutput() 
newBO.setFirstName(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setLastName(
    new String( org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10) )
 );
newBO.setType(
    EmploymentType.getDefaultValue()
 );
// ~AIB

        return( newBO );
    }
    
// attributes 

    protected EmployeePrimaryKey thePrimaryKey      = null;
	protected Properties frameworkProperties 			= null;
	private final Logger LOGGER = Logger.getLogger(Employee.class.getName());
	private Handler handler = null;
	private String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
}
