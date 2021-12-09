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
 * Projector for Department as outlined for the CQRS pattern.  All event handling and query handling related to Department are invoked here
 * and dispersed as an event to be handled elsewhere.
 * 
 * Commands are handled by DepartmentAggregate
 * 
 * @author your_name_here
 *
 */
@ProcessingGroup("department")
@Component("department-handler")
public class DepartmentProjector {
		
	// core constructor
	public DepartmentProjector(DepartmentRepository repository, QueryUpdateEmitter queryUpdateEmitter ) {
        this.repository = repository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }	

	/*
     * @param	event CreateDepartmentEvent
     */
    @EventHandler( payloadType=CreateDepartmentEvent.class )
    public void handle( CreateDepartmentEvent event) {
	    LOGGER.info("handling CreateDepartmentEvent - " + event );
	    Department entity = new Department();
        entity.setDepartmentId( event.getDepartmentId() );
        entity.setName( event.getName() );
	    
    	// ------------------------------------------
    	// persist a new one
    	// ------------------------------------------ 
	    repository.save(entity);
        
        // ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDepartment( entity );
    }

    /*
     * @param	event UpdateDepartmentEvent
     */
    @EventHandler( payloadType=UpdateDepartmentEvent.class )
    public void handle( UpdateDepartmentEvent event) {
    	LOGGER.info("handling UpdateDepartmentEvent - " + event );
    	
	    Department entity = new Department();
        entity.setDepartmentId( event.getDepartmentId() );
        entity.setName( event.getName() );
        entity.setHead( event.getHead() );
        entity.setEmployees( event.getEmployees() );
 
    	// ------------------------------------------
    	// save with an existing instance
    	// ------------------------------------------ 
		repository.save(entity);

    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindDepartment( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDepartment( entity );        
    }
    
    /*
     * @param	event DeleteDepartmentEvent
     */
    @EventHandler( payloadType=DeleteDepartmentEvent.class )
    public void handle( DeleteDepartmentEvent event) {
    	LOGGER.info("handling DeleteDepartmentEvent - " + event );
    	
    	Department entity = repository.findById( event.getDepartmentId()).get();
    	
    	// ------------------------------------------
    	// delete what is discovered via find on the embedded identifier
    	// ------------------------------------------
    	repository.delete( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDepartment( entity );

    }    

    /*
     * @param	event AssignHeadToDepartmentEvent
     */
    @EventHandler( payloadType=AssignHeadToDepartmentEvent.class)
    public void handle( AssignHeadToDepartmentEvent event) {
	    LOGGER.info("handling AssignHeadToDepartmentEvent - " + event );

	    Department entity = repository.findById( event.getDepartmentId()).get();
	    Employee assignment = EmployeeRepo.findById(event.getAssignment().getEmployeeId()).get();
	    
	    // ------------------------------------------
		// assign the Head
		// ------------------------------------------ 
	    entity.setHead( assignment );

	    // ------------------------------------------
    	// save 
    	// ------------------------------------------ 
	    repository.save(entity);
        
    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindDepartment( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDepartment( entity );
    }
    

/*
 * @param	event UnAssignHeadFromDepartmentEvent
 */
@EventHandler( payloadType=UnAssignHeadFromDepartmentEvent.class)
public void handle( UnAssignHeadFromDepartmentEvent event) {
    LOGGER.info("handling UnAssignHeadFromDepartmentEvent - " + event );
    
    Department entity = repository.findById(event.getDepartmentId()).get();

    // ------------------------------------------
	// null out the Head
	// ------------------------------------------     
    entity.setHead(null);

    // ------------------------------------------
	// save 
	// ------------------------------------------ 
    repository.save(entity);
    
	// ------------------------------------------
	// emit to subscribers that find one
	// ------------------------------------------    	
    emitFindDepartment( entity );

	// ------------------------------------------
	// emit to subscribers that find all
	// ------------------------------------------    	
    emitFindAllDepartment( entity );
}


    /*
     * @param	event AssignEmployeesToDepartmentEvent
     */
    @EventHandler( payloadType=AssignEmployeesToDepartmentEvent.class)
    public void handle( AssignEmployeesToDepartmentEvent event) {
	    LOGGER.info("handling AssignEmployeesToDepartmentEvent - " + event );
	    
	    Department entity = repository.findById(event.getDepartmentId()).get();
	    Employee child = EmployeeRepo.findById(event.getAddTo().getEmployeeId()).get();
	    
	    entity.getEmployees().add( child );

	    // ------------------------------------------
    	// save 
    	// ------------------------------------------ 
	    repository.save(entity);
        
    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindDepartment( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDepartment( entity );
    }
    

/*
 * @param	event RemoveEmployeesFromDepartmentEvent
 */
@EventHandler( payloadType=RemoveEmployeesFromDepartmentEvent.class)
public void handle( RemoveEmployeesFromDepartmentEvent event) {
    LOGGER.info("handling RemoveEmployeesFromDepartmentEvent - " + event );

    Department entity = repository.findById(event.getDepartmentId()).get();
    Employee child = EmployeeRepo.findById(event.getRemoveFrom().getEmployeeId()).get();
    
    entity.getEmployees().remove( child );

    // ------------------------------------------
	// save
	// ------------------------------------------ 
    repository.save(entity);
    
	// ------------------------------------------
	// emit to subscribers that find one
	// ------------------------------------------    	
    emitFindDepartment( entity );

	// ------------------------------------------
	// emit to subscribers that find all
	// ------------------------------------------    	
    emitFindAllDepartment( entity );
}



    /**
     * Method to retrieve the Department via an DepartmentPrimaryKey.
     * @param 	id Long
     * @return 	Department
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public Department handle( FindDepartmentQuery query ) 
    throws ProcessingException, IllegalArgumentException {
    	LOGGER.info("handling FindDepartmentQuery" );

    	UUID id = query.getFilter().getDepartmentId();
    	return repository.findById(id).get();
    }
    
    /**
     * Method to retrieve a collection of all Departments
     *
     * @param	inputQuery	FindAllDepartmentQuery 
     * @return 	List<Department> 
     * @exception ProcessingException Thrown if any problems
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public List<Department> handle( FindAllDepartmentQuery inputQuery ) throws ProcessingException {
    	LOGGER.info("handling FindAllDepartmentQuery" );
    	
    	return repository.findAll();
    }


	/**
	 * emit to subscription queries of type FindDepartment, 
	 * but only if the id matches
	 * 
	 * @param		entity	Department
	 */
	protected void emitFindDepartment( Department entity ) {
		LOGGER.info("handling emitFindDepartment" );
		
	    queryUpdateEmitter.emit(FindDepartmentQuery.class,
	                            query -> query.getFilter().getDepartmentId().equals(entity.getDepartmentId()),
	                            entity);
	}
	
	/**
	 * unconditionally emit to subscription queries of type FindAllDepartment
	 * 
	 * @param		entity	Department
	 */
	protected void emitFindAllDepartment( Department entity ) {
		LOGGER.info("handling emitFindAllDepartment" );
		
	    queryUpdateEmitter.emit(FindAllDepartmentQuery.class,
	                            query -> true,
	                            entity);
	}


    //--------------------------------------------------
    // attributes
    // --------------------------------------------------
	@Autowired
    private final DepartmentRepository repository;
	@Autowired
	private final QueryUpdateEmitter queryUpdateEmitter;
    @Autowired
    EmployeeRepository EmployeeRepo;

    private static final Logger LOGGER 	= Logger.getLogger(DepartmentProjector.class.getName());

}
