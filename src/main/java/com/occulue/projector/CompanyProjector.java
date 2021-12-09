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
 * Projector for Company as outlined for the CQRS pattern.  All event handling and query handling related to Company are invoked here
 * and dispersed as an event to be handled elsewhere.
 * 
 * Commands are handled by CompanyAggregate
 * 
 * @author your_name_here
 *
 */
@ProcessingGroup("company")
@Component("company-handler")
public class CompanyProjector {
		
	// core constructor
	public CompanyProjector(CompanyRepository repository, QueryUpdateEmitter queryUpdateEmitter ) {
        this.repository = repository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }	

	/*
     * @param	event CreatedCompanyEvent
     */
    @EventHandler( payloadType=CreatedCompanyEvent.class )
    public void handle( CreatedCompanyEvent event) {
	    LOGGER.info("handling CreatedCompanyEvent - " + event );
	    Company entity = new Company();
        entity.setCompanyId( event.getCompanyId() );
        entity.setName( event.getName() );
        entity.setEstablishedOn( event.getEstablishedOn() );
        entity.setRevenue( event.getRevenue() );
        entity.setAddress( event.getAddress() );
        entity.setBillingAddress( event.getBillingAddress() );
        entity.setType( event.getType() );
        entity.setIndustry( event.getIndustry() );
	    
    	// ------------------------------------------
    	// persist a new one
    	// ------------------------------------------ 
	    repository.save(entity);
        
        // ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllCompany( entity );
    }

    /*
     * @param	event RefreshedCompanyEvent
     */
    @EventHandler( payloadType=RefreshedCompanyEvent.class )
    public void handle( RefreshedCompanyEvent event) {
    	LOGGER.info("handling RefreshedCompanyEvent - " + event );
    	
	    Company entity = new Company();
        entity.setCompanyId( event.getCompanyId() );
        entity.setName( event.getName() );
        entity.setEstablishedOn( event.getEstablishedOn() );
        entity.setRevenue( event.getRevenue() );
        entity.setDivisions( event.getDivisions() );
        entity.setBoardMembers( event.getBoardMembers() );
        entity.setAddress( event.getAddress() );
        entity.setBillingAddress( event.getBillingAddress() );
        entity.setType( event.getType() );
        entity.setIndustry( event.getIndustry() );
 
    	// ------------------------------------------
    	// save with an existing instance
    	// ------------------------------------------ 
		repository.save(entity);

    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindCompany( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllCompany( entity );        
    }
    
    /*
     * @param	event ClosedCompanyEvent
     */
    @EventHandler( payloadType=ClosedCompanyEvent.class )
    public void handle( ClosedCompanyEvent event) {
    	LOGGER.info("handling ClosedCompanyEvent - " + event );
    	
    	Company entity = repository.findById( event.getCompanyId()).get();
    	
    	// ------------------------------------------
    	// delete what is discovered via find on the embedded identifier
    	// ------------------------------------------
    	repository.delete( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllCompany( entity );

    }    


    /*
     * @param	event AssignDivisionsToCompanyEvent
     */
    @EventHandler( payloadType=AssignDivisionsToCompanyEvent.class)
    public void handle( AssignDivisionsToCompanyEvent event) {
	    LOGGER.info("handling AssignDivisionsToCompanyEvent - " + event );
	    
	    Company entity = repository.findById(event.getCompanyId()).get();
	    Division child = DivisionRepo.findById(event.getAddTo().getDivisionId()).get();
	    
	    entity.getDivisions().add( child );

	    // ------------------------------------------
    	// save 
    	// ------------------------------------------ 
	    repository.save(entity);
        
    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindCompany( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllCompany( entity );
    }
    

/*
 * @param	event RemoveDivisionsFromCompanyEvent
 */
@EventHandler( payloadType=RemoveDivisionsFromCompanyEvent.class)
public void handle( RemoveDivisionsFromCompanyEvent event) {
    LOGGER.info("handling RemoveDivisionsFromCompanyEvent - " + event );

    Company entity = repository.findById(event.getCompanyId()).get();
    Division child = DivisionRepo.findById(event.getRemoveFrom().getDivisionId()).get();
    
    entity.getDivisions().remove( child );

    // ------------------------------------------
	// save
	// ------------------------------------------ 
    repository.save(entity);
    
	// ------------------------------------------
	// emit to subscribers that find one
	// ------------------------------------------    	
    emitFindCompany( entity );

	// ------------------------------------------
	// emit to subscribers that find all
	// ------------------------------------------    	
    emitFindAllCompany( entity );
}

    /*
     * @param	event AssignBoardMembersToCompanyEvent
     */
    @EventHandler( payloadType=AssignBoardMembersToCompanyEvent.class)
    public void handle( AssignBoardMembersToCompanyEvent event) {
	    LOGGER.info("handling AssignBoardMembersToCompanyEvent - " + event );
	    
	    Company entity = repository.findById(event.getCompanyId()).get();
	    Employee child = EmployeeRepo.findById(event.getAddTo().getEmployeeId()).get();
	    
	    entity.getBoardMembers().add( child );

	    // ------------------------------------------
    	// save 
    	// ------------------------------------------ 
	    repository.save(entity);
        
    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindCompany( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllCompany( entity );
    }
    

/*
 * @param	event DemomotedFromBoardMemberCommand
 */
@EventHandler( payloadType=DemomotedFromBoardMemberCommand.class)
public void handle( DemomotedFromBoardMemberCommand event) {
    LOGGER.info("handling DemomotedFromBoardMemberCommand - " + event );

    Company entity = repository.findById(event.getCompanyId()).get();
    Employee child = EmployeeRepo.findById(event.getRemoveFrom().getEmployeeId()).get();
    
    entity.getBoardMembers().remove( child );

    // ------------------------------------------
	// save
	// ------------------------------------------ 
    repository.save(entity);
    
	// ------------------------------------------
	// emit to subscribers that find one
	// ------------------------------------------    	
    emitFindCompany( entity );

	// ------------------------------------------
	// emit to subscribers that find all
	// ------------------------------------------    	
    emitFindAllCompany( entity );
}



    /**
     * Method to retrieve the Company via an CompanyPrimaryKey.
     * @param 	id Long
     * @return 	Company
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public Company handle( FindCompanyQuery query ) 
    throws ProcessingException, IllegalArgumentException {
    	LOGGER.info("handling FindCompanyQuery" );

    	UUID id = query.getFilter().getCompanyId();
    	return repository.findById(id).get();
    }
    
    /**
     * Method to retrieve a collection of all Companys
     *
     * @param	inputQuery	FindAllCompanyQuery 
     * @return 	List<Company> 
     * @exception ProcessingException Thrown if any problems
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public List<Company> handle( FindAllCompanyQuery inputQuery ) throws ProcessingException {
    	LOGGER.info("handling FindAllCompanyQuery" );
    	
    	return repository.findAll();
    }

    /**
     * query method to findCompanyByName
     * @param 		String name
     * @return		Company
     */     
	@SuppressWarnings("unused")
	@QueryHandler
	public Company findCompanyByName( FindByNameQuery query ) {
		LOGGER.info("handling findCompanyByName" );
		Company result = null;
		
		try {
		    result = repository.findCompanyByName( query.getFilter().getName() );

        }
        catch( Throwable exc ) {
        	LOGGER.log( Level.WARNING, "Failed to findCompanyByName using " + query.getFilter(), exc );
        }
        
        return result;
	}
    /**
     * query method to findCompaniesByIndustry
     * @param 		Industry industry
     * @return		List<Company>
     */     
	@SuppressWarnings("unused")
	@QueryHandler
	public List<Company> findCompaniesByIndustry( FindCompaniesByIndustryQuery query ) {
		LOGGER.info("handling findCompaniesByIndustry" );
		List<Company> result = null;
		
		try {
            Pageable pageable = PageRequest.of(query.getOffset(), query.getLimit());
            result = repository.findCompaniesByIndustry( query.getFilter().getIndustry(), pageable );

        }
        catch( Throwable exc ) {
        	LOGGER.log( Level.WARNING, "Failed to findCompaniesByIndustry using " + query.getFilter(), exc );
        }
        
        return result;
	}
    /**
     * query method to findCompaniesByType
     * @param 		CompanyType type
     * @return		List<Company>
     */     
	@SuppressWarnings("unused")
	@QueryHandler
	public List<Company> findCompaniesByType( FindByTypeQuery query ) {
		LOGGER.info("handling findCompaniesByType" );
		List<Company> result = null;
		
		try {
            Pageable pageable = PageRequest.of(query.getOffset(), query.getLimit());
            result = repository.findCompaniesByType( query.getFilter().getType(), pageable );

        }
        catch( Throwable exc ) {
        	LOGGER.log( Level.WARNING, "Failed to findCompaniesByType using " + query.getFilter(), exc );
        }
        
        return result;
	}

	/**
	 * emit to subscription queries of type FindCompany, 
	 * but only if the id matches
	 * 
	 * @param		entity	Company
	 */
	protected void emitFindCompany( Company entity ) {
		LOGGER.info("handling emitFindCompany" );
		
	    queryUpdateEmitter.emit(FindCompanyQuery.class,
	                            query -> query.getFilter().getCompanyId().equals(entity.getCompanyId()),
	                            entity);
	}
	
	/**
	 * unconditionally emit to subscription queries of type FindAllCompany
	 * 
	 * @param		entity	Company
	 */
	protected void emitFindAllCompany( Company entity ) {
		LOGGER.info("handling emitFindAllCompany" );
		
	    queryUpdateEmitter.emit(FindAllCompanyQuery.class,
	                            query -> true,
	                            entity);
	}


    //--------------------------------------------------
    // attributes
    // --------------------------------------------------
	@Autowired
    private final CompanyRepository repository;
	@Autowired
	private final QueryUpdateEmitter queryUpdateEmitter;
    @Autowired
    DivisionRepository DivisionRepo;
    @Autowired
    EmployeeRepository EmployeeRepo;

    private static final Logger LOGGER 	= Logger.getLogger(CompanyProjector.class.getName());

}
