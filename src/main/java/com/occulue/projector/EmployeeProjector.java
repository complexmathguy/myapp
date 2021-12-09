/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.handler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.repository.*;

/**
 * Projector for Employee as outlined for the CQRS pattern.  All event handling and query handling related to Employee are invoked here
 * and dispersed as an event to be handled elsewhere.
 * 
 * Commands are handled by EmployeeAggregate
 * 
 * @author your_name_here
 *
 */
@ProcessingGroup("employee")
@Component("employee-handler")
public class EmployeeProjector {
		
	// core constructor
	public EmployeeProjector(EmployeeRepository repository, QueryUpdateEmitter queryUpdateEmitter ) {
        this.repository = repository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }	

	/*
     * @param	event CreateEmployeeEvent
     */
    @EventHandler( payloadType=CreateEmployeeEvent.class )
    public void handle( CreateEmployeeEvent event) {
	    LOGGER.info("handling CreateEmployeeEvent - " + event );
	    Employee entity = new Employee();
        entity.setEmployeeId( event.getEmployeeId() );
        entity.setFirstName( event.getFirstName() );
        entity.setLastName( event.getLastName() );
        entity.setType( event.getType() );
	    
    	// ------------------------------------------
    	// persist a new one
    	// ------------------------------------------ 
	    repository.save(entity);
        
        // ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllEmployee( entity );
    }

    /*
     * @param	event UpdateEmployeeEvent
     */
    @EventHandler( payloadType=UpdateEmployeeEvent.class )
    public void handle( UpdateEmployeeEvent event) {
    	LOGGER.info("handling UpdateEmployeeEvent - " + event );
    	
	    Employee entity = new Employee();
        entity.setEmployeeId( event.getEmployeeId() );
        entity.setFirstName( event.getFirstName() );
        entity.setLastName( event.getLastName() );
        entity.setType( event.getType() );
 
    	// ------------------------------------------
    	// save with an existing instance
    	// ------------------------------------------ 
		repository.save(entity);

    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindEmployee( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllEmployee( entity );        
    }
    
    /*
     * @param	event DeleteEmployeeEvent
     */
    @EventHandler( payloadType=DeleteEmployeeEvent.class )
    public void handle( DeleteEmployeeEvent event) {
    	LOGGER.info("handling DeleteEmployeeEvent - " + event );
    	
    	Employee entity = repository.findById( event.getEmployeeId()).get();
    	
    	// ------------------------------------------
    	// delete what is discovered via find on the embedded identifier
    	// ------------------------------------------
    	repository.delete( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllEmployee( entity );

    }    




    /**
     * Method to retrieve the Employee via an EmployeePrimaryKey.
     * @param 	id Long
     * @return 	Employee
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public Employee handle( FindEmployeeQuery query ) 
    throws ProcessingException, IllegalArgumentException {
    	LOGGER.info("handling FindEmployeeQuery" );

    	UUID id = query.getFilter().getEmployeeId();
    	return repository.findById(id).get();
    }
    
    /**
     * Method to retrieve a collection of all Employees
     *
     * @param	inputQuery	FindAllEmployeeQuery 
     * @return 	List<Employee> 
     * @exception ProcessingException Thrown if any problems
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public List<Employee> handle( FindAllEmployeeQuery inputQuery ) throws ProcessingException {
    	LOGGER.info("handling FindAllEmployeeQuery" );
    	
    	return repository.findAll();
    }


	/**
	 * emit to subscription queries of type FindEmployee, 
	 * but only if the id matches
	 * 
	 * @param		entity	Employee
	 */
	protected void emitFindEmployee( Employee entity ) {
		LOGGER.info("handling emitFindEmployee" );
		
	    queryUpdateEmitter.emit(FindEmployeeQuery.class,
	                            query -> query.getFilter().getEmployeeId().equals(entity.getEmployeeId()),
	                            entity);
	}
	
	/**
	 * unconditionally emit to subscription queries of type FindAllEmployee
	 * 
	 * @param		entity	Employee
	 */
	protected void emitFindAllEmployee( Employee entity ) {
		LOGGER.info("handling emitFindAllEmployee" );
		
	    queryUpdateEmitter.emit(FindAllEmployeeQuery.class,
	                            query -> true,
	                            entity);
	}


    //--------------------------------------------------
    // attributes
    // --------------------------------------------------
	@Autowired
    private final EmployeeRepository repository;
	@Autowired
	private final QueryUpdateEmitter queryUpdateEmitter;

    private static final Logger LOGGER 	= Logger.getLogger(EmployeeProjector.class.getName());

}
