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
import static org.springframework.util.Assert.state;

import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.api.*;
import com.occulue.subscriber.*;

/**
 * Test Employee class.
 *
 * @author your_name_here
 */
public class EmployeeTest{

    // ------------------------------------
	// default constructor
    // ------------------------------------
	public EmployeeTest() {
		subscriber = new EmployeeSubscriber();
	}

	// test methods
	@Test
	/*
	 * Initiate EmployeeTest.
	 */
	public void startTest() throws Throwable {
		try {
			LOGGER.info("**********************************************************");
			LOGGER.info("Beginning test on Employee...");
			LOGGER.info("**********************************************************");
			
			// ---------------------------------------------
			// jumpstart process
			// ---------------------------------------------
			jumpStart();
			
		} catch (Throwable e) {
			throw e;
		} finally {
		}
	}

	/** 
	 * jumpstart the process by instantiating2 Employee
	 */
	protected void jumpStart() throws Throwable {
		LOGGER.info( "\n======== create instances to get the ball rolling  ======== ");

		employeeId = EmployeeBusinessDelegate.getEmployeeInstance()
		.createEmployee( generateNewCommand() )
		.get();

		// ---------------------------------------------
		// set up query subscriptions after the 1st create
		// ---------------------------------------------
		setUpQuerySubscriptions();

		EmployeeBusinessDelegate.getEmployeeInstance()
					.createEmployee( generateNewCommand() );

	}

	/** 
	 * Set up query subscriptions
	 */
	protected void setUpQuerySubscriptions() throws Throwable {
		LOGGER.info( "\n======== Setting Up Query Subscriptions ======== ");
			
		try {            
			subscriber.employeeSubscribe().updates().subscribe(
					  successValue -> { 
						  LOGGER.info(successValue.toString());
						  try {
							  employeeId = successValue.getEmployeeId();
							  Employee entity = read();
							  state( entity.getEmployeeId() == successValue.getEmployeeId(), "Subscribed value and associated read value are not equal");
						  } catch( Throwable exc ) {
							  LOGGER.warning( exc.getMessage() );
						  }
					  	},
					  error -> LOGGER.warning(error.getMessage()),
					  () -> LOGGER.info("Subscription on employee consumed")
					);
			subscriber.employeeSubscribe( employeeId ).updates().subscribe(
					  successValue -> { 
						  LOGGER.info(successValue.toString());
						  try {
							  List<Employee> all = getAll();
							  int size = all.size();
							  employeeId = all.get(size-1).getEmployeeId();
							  delete();
							  state( getAll().size() == size - 1 , "value not deleted from list");
						  } catch( Throwable exc ) {
							  LOGGER.warning( exc.getMessage() );
						  }
					  	},
					  error -> LOGGER.warning(error.getMessage()),
					  () -> LOGGER.info("Subscription on employee for employeeId consumed")

					);
			

			}
			catch (Exception e) {
				LOGGER.warning( e.getMessage() );
				throw e;
			}
		}
		
		/** 
	 * read a Employee. 
	 */
	protected Employee read() throws Throwable {
		LOGGER.info( "\n======== READ ======== ");
		LOGGER.info( "-- Reading a previously created Employee" );

		Employee entity = null;
		StringBuilder msg = new StringBuilder( "-- Failed to read Employee with primary key" );
		msg.append( employeeId );
		
		EmployeeFetchOneSummary fetchOneSummary = new EmployeeFetchOneSummary( employeeId );

		try {
			entity = EmployeeBusinessDelegate.getEmployeeInstance().getEmployee( fetchOneSummary );

			assertNotNull( entity,msg.toString() );

			LOGGER.info( "-- Successfully found Employee " + entity.toString() );
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : " + e );

			throw e;
		}
		
		return entity;
	}

	/** 
	 * updating a Employee.
	 */
	protected void update() throws Throwable {
		LOGGER.info( "\n======== UPDATE ======== ");
		LOGGER.info( "-- Attempting to update a Employee." );

		StringBuilder msg = new StringBuilder( "Failed to update a Employee : " );        
		Employee entity = read();
		UpdateEmployeeCommand command = generateUpdateCommand();

		try {            
			assertNotNull( entity, msg.toString() );

			LOGGER.info( "-- Now updating the created Employee." );

			// for use later on...
			employeeId = entity.getEmployeeId();

			EmployeeBusinessDelegate proxy = EmployeeBusinessDelegate.getEmployeeInstance();  

			proxy.updateEmployee( command );   

			LOGGER.info( "-- Successfully saved Employee - " + entity.toString() );
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : primarykey = " + employeeId + " : command -" +  command + " : " + e );

			throw e;
		}
	}

	/** 
	 * delete a Employee.
	 */
	protected void delete() throws Throwable {
		LOGGER.info( "\n======== DELETE ======== ");
		LOGGER.info( "-- Deleting a previously created Employee." );

		Employee entity = null;
		
		try{
		    entity = read(); 
			LOGGER.info( "-- Successfully read Employee with id " + employeeId );            
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( "-- Failed to read Employee with id " + employeeId );

			throw e;
		}

		try{
			EmployeeBusinessDelegate.getEmployeeInstance().delete( new DeleteEmployeeCommand( entity.getEmployeeId() ) );
			LOGGER.info( "-- Successfully deleted Employee with id " + employeeId );            
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( "-- Failed to delete Employee with id " + employeeId );

			throw e;
		}
	}

	/**
	 * get all Employees.
	 */
	protected List<Employee> getAll() throws Throwable {    
		LOGGER.info( "======== GETALL ======== ");
		LOGGER.info( "-- Retrieving Collection of Employees:" );

		StringBuilder msg = new StringBuilder( "-- Failed to get all Employee : " );        
		List<Employee> collection  = new ArrayList<>();

		try {
			// call the static get method on the EmployeeBusinessDelegate
			collection = EmployeeBusinessDelegate.getEmployeeInstance().getAllEmployee();
			assertNotNull( collection, "An Empty collection of Employee was incorrectly returned.");
			
			// Now print out the values
			Employee entity = null;            
			Iterator<Employee> iter = collection.iterator();
			int index = 1;

			while( iter.hasNext() ) {
				// Retrieve the entity   
				entity = iter.next();

				assertNotNull( entity,"-- null entity in Collection." );
				assertNotNull( entity.getEmployeeId(), "-- entity in Collection has a null primary key" );        

				LOGGER.info( " - " + String.valueOf(index) + ". " + entity.toString() );
				index++;
			}
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : " + e );

			throw e;
		}
		
		return collection;			
	}

	/**
	 * Assigns a common log handler for each test class in the suite 
	 * in the event log output needs to go elsewhere
	 * 
	 * @param		handler	Handler
	 * @return		EmployeeTest
	 */
	protected EmployeeTest setHandler(Handler handler) {
		if ( handler != null )
			LOGGER.addHandler(handler); 
		return this;
	}

	/**
	 * Returns a new populated Employee
	 * 
	 * @return CreateEmployeeCommand alias
	 */
	protected CreateEmployeeCommand generateNewCommand() {
        CreateEmployeeCommand command = new CreateEmployeeCommand( null,  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),  EmploymentType.values()[0] );
		
		return( command );
	}

		/**
		 * Returns a new populated Employee
		 * 
		 * @return UpdateEmployeeCommand alias
		 */
	protected UpdateEmployeeCommand generateUpdateCommand() {
	        UpdateEmployeeCommand command = new UpdateEmployeeCommand( null,  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),  EmploymentType.values()[0] );
			
			return( command );
		}
	//-----------------------------------------------------
	// attributes 
	//-----------------------------------------------------
	protected UUID employeeId = null;
	protected EmployeeSubscriber subscriber = null;
	private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
	private final Logger LOGGER = Logger.getLogger(EmployeeTest.class.getName());
}
