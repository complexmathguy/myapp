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
 * Projector for Division as outlined for the CQRS pattern.  All event handling and query handling related to Division are invoked here
 * and dispersed as an event to be handled elsewhere.
 * 
 * Commands are handled by DivisionAggregate
 * 
 * @author your_name_here
 *
 */
@ProcessingGroup("division")
@Component("division-handler")
public class DivisionProjector {
		
	// core constructor
	public DivisionProjector(DivisionRepository repository, QueryUpdateEmitter queryUpdateEmitter ) {
        this.repository = repository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }	

	/*
     * @param	event CreateDivisionEvent
     */
    @EventHandler( payloadType=CreateDivisionEvent.class )
    public void handle( CreateDivisionEvent event) {
	    LOGGER.info("handling CreateDivisionEvent - " + event );
	    Division entity = new Division();
        entity.setDivisionId( event.getDivisionId() );
        entity.setName( event.getName() );
	    
    	// ------------------------------------------
    	// persist a new one
    	// ------------------------------------------ 
	    repository.save(entity);
        
        // ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDivision( entity );
    }

    /*
     * @param	event UpdateDivisionEvent
     */
    @EventHandler( payloadType=UpdateDivisionEvent.class )
    public void handle( UpdateDivisionEvent event) {
    	LOGGER.info("handling UpdateDivisionEvent - " + event );
    	
	    Division entity = new Division();
        entity.setDivisionId( event.getDivisionId() );
        entity.setName( event.getName() );
        entity.setHead( event.getHead() );
        entity.setDepartments( event.getDepartments() );
 
    	// ------------------------------------------
    	// save with an existing instance
    	// ------------------------------------------ 
		repository.save(entity);

    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindDivision( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDivision( entity );        
    }
    
    /*
     * @param	event DeleteDivisionEvent
     */
    @EventHandler( payloadType=DeleteDivisionEvent.class )
    public void handle( DeleteDivisionEvent event) {
    	LOGGER.info("handling DeleteDivisionEvent - " + event );
    	
    	Division entity = repository.findById( event.getDivisionId()).get();
    	
    	// ------------------------------------------
    	// delete what is discovered via find on the embedded identifier
    	// ------------------------------------------
    	repository.delete( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDivision( entity );

    }    

    /*
     * @param	event PromotedToDivisionHeadEvent
     */
    @EventHandler( payloadType=PromotedToDivisionHeadEvent.class)
    public void handle( PromotedToDivisionHeadEvent event) {
	    LOGGER.info("handling PromotedToDivisionHeadEvent - " + event );

	    Division entity = repository.findById( event.getDivisionId()).get();
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
        emitFindDivision( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDivision( entity );
    }
    

/*
 * @param	event DemomotedFromDivisionHeadCommand
 */
@EventHandler( payloadType=DemomotedFromDivisionHeadCommand.class)
public void handle( DemomotedFromDivisionHeadCommand event) {
    LOGGER.info("handling DemomotedFromDivisionHeadCommand - " + event );
    
    Division entity = repository.findById(event.getDivisionId()).get();

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
    emitFindDivision( entity );

	// ------------------------------------------
	// emit to subscribers that find all
	// ------------------------------------------    	
    emitFindAllDivision( entity );
}


    /*
     * @param	event AssignDepartmentsToDivisionEvent
     */
    @EventHandler( payloadType=AssignDepartmentsToDivisionEvent.class)
    public void handle( AssignDepartmentsToDivisionEvent event) {
	    LOGGER.info("handling AssignDepartmentsToDivisionEvent - " + event );
	    
	    Division entity = repository.findById(event.getDivisionId()).get();
	    Department child = DepartmentRepo.findById(event.getAddTo().getDepartmentId()).get();
	    
	    entity.getDepartments().add( child );

	    // ------------------------------------------
    	// save 
    	// ------------------------------------------ 
	    repository.save(entity);
        
    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindDivision( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllDivision( entity );
    }
    

/*
 * @param	event RemoveDepartmentsFromDivisionEvent
 */
@EventHandler( payloadType=RemoveDepartmentsFromDivisionEvent.class)
public void handle( RemoveDepartmentsFromDivisionEvent event) {
    LOGGER.info("handling RemoveDepartmentsFromDivisionEvent - " + event );

    Division entity = repository.findById(event.getDivisionId()).get();
    Department child = DepartmentRepo.findById(event.getRemoveFrom().getDepartmentId()).get();
    
    entity.getDepartments().remove( child );

    // ------------------------------------------
	// save
	// ------------------------------------------ 
    repository.save(entity);
    
	// ------------------------------------------
	// emit to subscribers that find one
	// ------------------------------------------    	
    emitFindDivision( entity );

	// ------------------------------------------
	// emit to subscribers that find all
	// ------------------------------------------    	
    emitFindAllDivision( entity );
}



    /**
     * Method to retrieve the Division via an DivisionPrimaryKey.
     * @param 	id Long
     * @return 	Division
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public Division handle( FindDivisionQuery query ) 
    throws ProcessingException, IllegalArgumentException {
    	LOGGER.info("handling FindDivisionQuery" );

    	UUID id = query.getFilter().getDivisionId();
    	return repository.findById(id).get();
    }
    
    /**
     * Method to retrieve a collection of all Divisions
     *
     * @param	inputQuery	FindAllDivisionQuery 
     * @return 	List<Division> 
     * @exception ProcessingException Thrown if any problems
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public List<Division> handle( FindAllDivisionQuery inputQuery ) throws ProcessingException {
    	LOGGER.info("handling FindAllDivisionQuery" );
    	
    	return repository.findAll();
    }


	/**
	 * emit to subscription queries of type FindDivision, 
	 * but only if the id matches
	 * 
	 * @param		entity	Division
	 */
	protected void emitFindDivision( Division entity ) {
		LOGGER.info("handling emitFindDivision" );
		
	    queryUpdateEmitter.emit(FindDivisionQuery.class,
	                            query -> query.getFilter().getDivisionId().equals(entity.getDivisionId()),
	                            entity);
	}
	
	/**
	 * unconditionally emit to subscription queries of type FindAllDivision
	 * 
	 * @param		entity	Division
	 */
	protected void emitFindAllDivision( Division entity ) {
		LOGGER.info("handling emitFindAllDivision" );
		
	    queryUpdateEmitter.emit(FindAllDivisionQuery.class,
	                            query -> true,
	                            entity);
	}


    //--------------------------------------------------
    // attributes
    // --------------------------------------------------
	@Autowired
    private final DivisionRepository repository;
	@Autowired
	private final QueryUpdateEmitter queryUpdateEmitter;
    @Autowired
    EmployeeRepository EmployeeRepo;
    @Autowired
    DepartmentRepository DepartmentRepo;

    private static final Logger LOGGER 	= Logger.getLogger(DivisionProjector.class.getName());

}
