/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

    import com.occulue.primarykey.*;
    import com.occulue.delegate.*;
    import com.occulue.bo.*;
    
/** 
 * Implements Struts action processing for business entity Division.
 *
 * @author 
 */
@RestController
@RequestMapping("/Division")
public class DivisionRestController extends BaseSpringRestController
{

    /**
     * Handles saving a Division BO.  if not key provided, calls create, otherwise calls save
     * @param		Division division
     * @return		Division
     */
	@RequestMapping("/save")
    public Division save( @RequestBody Division division )
    {
    	// assign locally
    	this.division = division;
    	
        if ( hasPrimaryKey() )
        {
            return (update());
        }
        else
        {
            return (create());
        }
    }

    /**
     * Handles deleting a Division BO
     * @param		Long divisionId
     * @param 		Long[] childIds
     * @return		boolean
     */
    @RequestMapping("/delete")    
    public boolean delete( @RequestParam(value="division.divisionId", required=false) Long divisionId, 
    						@RequestParam(value="childIds", required=false) Long[] childIds )
    {                
        try
        {
        	DivisionBusinessDelegate delegate = DivisionBusinessDelegate.getDivisionInstance();
        	
        	if ( childIds == null || childIds.length == 0 )
        	{
        		delegate.delete( new DivisionPrimaryKey( divisionId ) );
        		LOGGER.info( "DivisionController:delete() - successfully deleted Division with key " + division.getDivisionPrimaryKey().valuesAsCollection() );
        	}
        	else
        	{
        		for ( Long id : childIds )
        		{
        			try
        			{
        				delegate.delete( new DivisionPrimaryKey( id ) );
        			}
	                catch( Throwable exc )
	                {
	                	LOGGER.info( "DivisionController:delete() - " + exc.getMessage() );
	                	return false;
	                }
        		}
        	}
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "DivisionController:delete() - " + exc.getMessage() );
        	return false;        	
        }
        
        return true;
	}        
	
    /**
     * Handles loading a Division BO
     * @param		Long divisionId
     * @return		Division
     */    
    @RequestMapping("/load")
    public Division load( @RequestParam(value="division.divisionId", required=true) Long divisionId )
    {    	
        DivisionPrimaryKey pk = null;

    	try
        {  
    		System.out.println( "\n\n****Division.load pk is " + divisionId );
        	if ( divisionId != null )
        	{
        		pk = new DivisionPrimaryKey( divisionId );
        		
        		// load the Division
	            this.division = DivisionBusinessDelegate.getDivisionInstance().getDivision( pk );
	            
	            LOGGER.info( "DivisionController:load() - successfully loaded - " + this.division.toString() );             
			}
			else
			{
	            LOGGER.info( "DivisionController:load() - unable to locate the primary key as an attribute or a selection for - " + division.toString() );
	            return null;
			}	            
        }
        catch( Throwable exc )
        {
            LOGGER.info( "DivisionController:load() - failed to load Division using Id " + divisionId + ", " + exc.getMessage() );
            return null;
        }

        return division;

    }

    /**
     * Handles loading all Division business objects
     * @return		List<Division>
     */
    @RequestMapping("/loadAll")
    public List<Division> loadAll()
    {                
        List<Division> divisionList = null;
        
    	try
        {                        
            // load the Division
            divisionList = DivisionBusinessDelegate.getDivisionInstance().getAllDivision();
            
            if ( divisionList != null )
                LOGGER.info(  "DivisionController:loadAllDivision() - successfully loaded all Divisions" );
        }
        catch( Throwable exc )
        {
            LOGGER.info(  "DivisionController:loadAll() - failed to load all Divisions - " + exc.getMessage() );
        	return null;
            
        }

        return divisionList;
                            
    }


// findAllBy methods


    /**
     * save Head on Division
     * @param		Long	divisionId
     * @param		Long	childId
     * @param		Division division
     * @return		Division
     */     
	@RequestMapping("/saveHead")
	public Division saveHead( @RequestParam(value="division.divisionId", required=true) Long divisionId, 
											@RequestParam(value="childIds", required=true) Long childId, @RequestBody Division division )
	{
		if ( load( divisionId ) == null )
			return( null );
		
		if ( childId != null )
		{
			EmployeeBusinessDelegate childDelegate 	= EmployeeBusinessDelegate.getEmployeeInstance();
			DivisionBusinessDelegate parentDelegate = DivisionBusinessDelegate.getDivisionInstance();			
			Employee child 							= null;

			try
			{
				child = childDelegate.getEmployee( new EmployeePrimaryKey( childId ) );
			}
            catch( Throwable exc )
            {
            	LOGGER.info( "DivisionController:saveHead() failed to get Employee using id " + childId + " - " + exc.getMessage() );
            	return null;
            }
	
			division.setHead( child );
		
			try
			{
				// save it
				parentDelegate.saveDivision( division );
			}
			catch( Exception exc )
			{
				LOGGER.info( "DivisionController:saveHead() failed saving parent Division - " + exc.getMessage() );
			}
		}
		
		return division;
	}

    /**
     * delete Head on Division
     * @param		Long divisionId
     * @return		Division
     */     
	@RequestMapping("/deleteHead")
	public Division deleteHead( @RequestParam(value="division.divisionId", required=true) Long divisionId )
	
	{
		if ( load( divisionId ) == null )
			return( null );

		if ( division.getHead() != null )
		{
			EmployeePrimaryKey pk = division.getHead().getEmployeePrimaryKey();
			
			// null out the parent first so there's no constraint during deletion
			division.setHead( null );
			try
			{
				DivisionBusinessDelegate parentDelegate = DivisionBusinessDelegate.getDivisionInstance();

				// save it
				division = parentDelegate.saveDivision( division );
			}
			catch( Exception exc )
			{
				LOGGER.info( "DivisionController:deleteHead() failed to save Division - " + exc.getMessage() );
			}
			
			try
			{
				// safe to delete the child			
				EmployeeBusinessDelegate childDelegate = EmployeeBusinessDelegate.getEmployeeInstance();
				childDelegate.delete( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "DivisionController:deleteHead() failed  - " + exc.getMessage() );
			}
		}
		
		return division;
	}
	


    /**
     * Handles creating a Division BO
     * @return		Division
     */
    protected Division create()
    {
        try
        {       
			this.division = DivisionBusinessDelegate.getDivisionInstance().createDivision( division );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "DivisionController:create() - exception Division - " + exc.getMessage());        	
        	return null;
        }
        
        return this.division;
    }

    /**
     * Handles updating a Division BO
     * @return		Division
     */    
    protected Division update()
    {
    	// store provided data
        Division tmp = division;

        // load actual data from db
    	load();
    	
    	// copy provided data into actual data
    	division.copyShallow( tmp );
    	
        try
        {                        	        
			// create the DivisionBusiness Delegate            
			DivisionBusinessDelegate delegate = DivisionBusinessDelegate.getDivisionInstance();
            this.division = delegate.saveDivision( division );
            
            if ( this.division != null )
                LOGGER.info( "DivisionController:update() - successfully updated Division - " + division.toString() );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "DivisionController:update() - successfully update Division - " + exc.getMessage());        	
        	return null;
        }
        
        return this.division;
        
    }


    /**
     * Returns true if the division is non-null and has it's primary key field(s) set
     * @return		boolean
     */
    protected boolean hasPrimaryKey()
    {
    	boolean hasPK = false;

		if ( division != null && division.getDivisionPrimaryKey().hasBeenAssigned() == true )
		   hasPK = true;
		
		return( hasPK );
    }

    protected Division load()
    {
    	return( load( new Long( division.getDivisionPrimaryKey().getFirstKey().toString() ) ));
    }

//************************************************************************    
// Attributes
//************************************************************************
    protected Division division = null;
    private static final Logger LOGGER = Logger.getLogger(Division.class.getName());
    
}


