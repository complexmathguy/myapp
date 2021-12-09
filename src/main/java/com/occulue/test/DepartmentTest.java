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
 * Test Department class.
 *
 * @author your_name_here
 */
public class DepartmentTest{

    // ------------------------------------
	// default constructor
    // ------------------------------------
	public DepartmentTest() {
		subscriber = new DepartmentSubscriber();
	}

	// test methods
	@Test
	/*
	 * Initiate DepartmentTest.
	 */
	public void startTest() throws Throwable {
		try {
			LOGGER.info("**********************************************************");
			LOGGER.info("Beginning test on Department...");
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
	 * jumpstart the process by instantiating2 Department
	 */
	protected void jumpStart() throws Throwable {
		LOGGER.info( "\n======== create instances to get the ball rolling  ======== ");

		departmentId = DepartmentBusinessDelegate.getDepartmentInstance()
		.createDepartment( generateNewCommand() )
		.get();

		// ---------------------------------------------
		// set up query subscriptions after the 1st create
		// ---------------------------------------------
		setUpQuerySubscriptions();

		DepartmentBusinessDelegate.getDepartmentInstance()
					.createDepartment( generateNewCommand() );

	}

	/** 
	 * Set up query subscriptions
	 */
	protected void setUpQuerySubscriptions() throws Throwable {
		LOGGER.info( "\n======== Setting Up Query Subscriptions ======== ");
			
		try {            
			subscriber.departmentSubscribe().updates().subscribe(
					  successValue -> { 
						  LOGGER.info(successValue.toString());
						  try {
							  departmentId = successValue.getDepartmentId();
							  Department entity = read();
							  state( entity.getDepartmentId() == successValue.getDepartmentId(), "Subscribed value and associated read value are not equal");
						  } catch( Throwable exc ) {
							  LOGGER.warning( exc.getMessage() );
						  }
					  	},
					  error -> LOGGER.warning(error.getMessage()),
					  () -> LOGGER.info("Subscription on department consumed")
					);
			subscriber.departmentSubscribe( departmentId ).updates().subscribe(
					  successValue -> { 
						  LOGGER.info(successValue.toString());
						  try {
							  List<Department> all = getAll();
							  int size = all.size();
							  departmentId = all.get(size-1).getDepartmentId();
							  delete();
							  state( getAll().size() == size - 1 , "value not deleted from list");
						  } catch( Throwable exc ) {
							  LOGGER.warning( exc.getMessage() );
						  }
					  	},
					  error -> LOGGER.warning(error.getMessage()),
					  () -> LOGGER.info("Subscription on department for departmentId consumed")

					);
			

			}
			catch (Exception e) {
				LOGGER.warning( e.getMessage() );
				throw e;
			}
		}
		
		/** 
	 * read a Department. 
	 */
	protected Department read() throws Throwable {
		LOGGER.info( "\n======== READ ======== ");
		LOGGER.info( "-- Reading a previously created Department" );

		Department entity = null;
		StringBuilder msg = new StringBuilder( "-- Failed to read Department with primary key" );
		msg.append( departmentId );
		
		DepartmentFetchOneSummary fetchOneSummary = new DepartmentFetchOneSummary( departmentId );

		try {
			entity = DepartmentBusinessDelegate.getDepartmentInstance().getDepartment( fetchOneSummary );

			assertNotNull( entity,msg.toString() );

			LOGGER.info( "-- Successfully found Department " + entity.toString() );
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : " + e );

			throw e;
		}
		
		return entity;
	}

	/** 
	 * updating a Department.
	 */
	protected void update() throws Throwable {
		LOGGER.info( "\n======== UPDATE ======== ");
		LOGGER.info( "-- Attempting to update a Department." );

		StringBuilder msg = new StringBuilder( "Failed to update a Department : " );        
		Department entity = read();
		UpdateDepartmentCommand command = generateUpdateCommand();

		try {            
			assertNotNull( entity, msg.toString() );

			LOGGER.info( "-- Now updating the created Department." );

			// for use later on...
			departmentId = entity.getDepartmentId();

			DepartmentBusinessDelegate proxy = DepartmentBusinessDelegate.getDepartmentInstance();  

			proxy.updateDepartment( command );   

			LOGGER.info( "-- Successfully saved Department - " + entity.toString() );
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : primarykey = " + departmentId + " : command -" +  command + " : " + e );

			throw e;
		}
	}

	/** 
	 * delete a Department.
	 */
	protected void delete() throws Throwable {
		LOGGER.info( "\n======== DELETE ======== ");
		LOGGER.info( "-- Deleting a previously created Department." );

		Department entity = null;
		
		try{
		    entity = read(); 
			LOGGER.info( "-- Successfully read Department with id " + departmentId );            
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( "-- Failed to read Department with id " + departmentId );

			throw e;
		}

		try{
			DepartmentBusinessDelegate.getDepartmentInstance().delete( new DeleteDepartmentCommand( entity.getDepartmentId() ) );
			LOGGER.info( "-- Successfully deleted Department with id " + departmentId );            
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( "-- Failed to delete Department with id " + departmentId );

			throw e;
		}
	}

	/**
	 * get all Departments.
	 */
	protected List<Department> getAll() throws Throwable {    
		LOGGER.info( "======== GETALL ======== ");
		LOGGER.info( "-- Retrieving Collection of Departments:" );

		StringBuilder msg = new StringBuilder( "-- Failed to get all Department : " );        
		List<Department> collection  = new ArrayList<>();

		try {
			// call the static get method on the DepartmentBusinessDelegate
			collection = DepartmentBusinessDelegate.getDepartmentInstance().getAllDepartment();
			assertNotNull( collection, "An Empty collection of Department was incorrectly returned.");
			
			// Now print out the values
			Department entity = null;            
			Iterator<Department> iter = collection.iterator();
			int index = 1;

			while( iter.hasNext() ) {
				// Retrieve the entity   
				entity = iter.next();

				assertNotNull( entity,"-- null entity in Collection." );
				assertNotNull( entity.getDepartmentId(), "-- entity in Collection has a null primary key" );        

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
	 * @return		DepartmentTest
	 */
	protected DepartmentTest setHandler(Handler handler) {
		if ( handler != null )
			LOGGER.addHandler(handler); 
		return this;
	}

	/**
	 * Returns a new populated Department
	 * 
	 * @return CreateDepartmentCommand alias
	 */
	protected CreateDepartmentCommand generateNewCommand() {
        CreateDepartmentCommand command = new CreateDepartmentCommand( null,  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16) );
		
		return( command );
	}

		/**
		 * Returns a new populated Department
		 * 
		 * @return UpdateDepartmentCommand alias
		 */
	protected UpdateDepartmentCommand generateUpdateCommand() {
	        UpdateDepartmentCommand command = new UpdateDepartmentCommand( null,  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),  null,  new HashSet<>() );
			
			return( command );
		}
	//-----------------------------------------------------
	// attributes 
	//-----------------------------------------------------
	protected UUID departmentId = null;
	protected DepartmentSubscriber subscriber = null;
	private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
	private final Logger LOGGER = Logger.getLogger(DepartmentTest.class.getName());
}
