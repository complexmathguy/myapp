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
 * Implements Struts action processing for business entity Department.
 *
 * @author 
 */
@RestController
@RequestMapping("/Department")
public class DepartmentRestController extends BaseSpringRestController
{

    /**
     * Handles saving a Department BO.  if not key provided, calls create, otherwise calls save
     * @param		Department department
     * @return		Department
     */
	@RequestMapping("/save")
    public Department save( @RequestBody Department department )
    {
    	// assign locally
    	this.department = department;
    	
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
     * Handles deleting a Department BO
     * @param		Long departmentId
     * @param 		Long[] childIds
     * @return		boolean
     */
    @RequestMapping("/delete")    
    public boolean delete( @RequestParam(value="department.departmentId", required=false) Long departmentId, 
    						@RequestParam(value="childIds", required=false) Long[] childIds )
    {                
        try
        {
        	DepartmentBusinessDelegate delegate = DepartmentBusinessDelegate.getDepartmentInstance();
        	
        	if ( childIds == null || childIds.length == 0 )
        	{
        		delegate.delete( new DepartmentPrimaryKey( departmentId ) );
        		LOGGER.info( "DepartmentController:delete() - successfully deleted Department with key " + department.getDepartmentPrimaryKey().valuesAsCollection() );
        	}
        	else
        	{
        		for ( Long id : childIds )
        		{
        			try
        			{
        				delegate.delete( new DepartmentPrimaryKey( id ) );
        			}
	                catch( Throwable exc )
	                {
	                	LOGGER.info( "DepartmentController:delete() - " + exc.getMessage() );
	                	return false;
	                }
        		}
        	}
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "DepartmentController:delete() - " + exc.getMessage() );
        	return false;        	
        }
        
        return true;
	}        
	
    /**
     * Handles loading a Department BO
     * @param		Long departmentId
     * @return		Department
     */    
    @RequestMapping("/load")
    public Department load( @RequestParam(value="department.departmentId", required=true) Long departmentId )
    {    	
        DepartmentPrimaryKey pk = null;

    	try
        {  
    		System.out.println( "\n\n****Department.load pk is " + departmentId );
        	if ( departmentId != null )
        	{
        		pk = new DepartmentPrimaryKey( departmentId );
        		
        		// load the Department
	            this.department = DepartmentBusinessDelegate.getDepartmentInstance().getDepartment( pk );
	            
	            LOGGER.info( "DepartmentController:load() - successfully loaded - " + this.department.toString() );             
			}
			else
			{
	            LOGGER.info( "DepartmentController:load() - unable to locate the primary key as an attribute or a selection for - " + department.toString() );
	            return null;
			}	            
        }
        catch( Throwable exc )
        {
            LOGGER.info( "DepartmentController:load() - failed to load Department using Id " + departmentId + ", " + exc.getMessage() );
            return null;
        }

        return department;

    }

    /**
     * Handles loading all Department business objects
     * @return		List<Department>
     */
    @RequestMapping("/loadAll")
    public List<Department> loadAll()
    {                
        List<Department> departmentList = null;
        
    	try
        {                        
            // load the Department
            departmentList = DepartmentBusinessDelegate.getDepartmentInstance().getAllDepartment();
            
            if ( departmentList != null )
                LOGGER.info(  "DepartmentController:loadAllDepartment() - successfully loaded all Departments" );
        }
        catch( Throwable exc )
        {
            LOGGER.info(  "DepartmentController:loadAll() - failed to load all Departments - " + exc.getMessage() );
        	return null;
            
        }

        return departmentList;
                            
    }


// findAllBy methods


    /**
     * save Head on Department
     * @param		Long	departmentId
     * @param		Long	childId
     * @param		Department department
     * @return		Department
     */     
	@RequestMapping("/saveHead")
	public Department saveHead( @RequestParam(value="department.departmentId", required=true) Long departmentId, 
											@RequestParam(value="childIds", required=true) Long childId, @RequestBody Department department )
	{
		if ( load( departmentId ) == null )
			return( null );
		
		if ( childId != null )
		{
			EmployeeBusinessDelegate childDelegate 	= EmployeeBusinessDelegate.getEmployeeInstance();
			DepartmentBusinessDelegate parentDelegate = DepartmentBusinessDelegate.getDepartmentInstance();			
			Employee child 							= null;

			try
			{
				child = childDelegate.getEmployee( new EmployeePrimaryKey( childId ) );
			}
            catch( Throwable exc )
            {
            	LOGGER.info( "DepartmentController:saveHead() failed to get Employee using id " + childId + " - " + exc.getMessage() );
            	return null;
            }
	
			department.setHead( child );
		
			try
			{
				// save it
				parentDelegate.saveDepartment( department );
			}
			catch( Exception exc )
			{
				LOGGER.info( "DepartmentController:saveHead() failed saving parent Department - " + exc.getMessage() );
			}
		}
		
		return department;
	}

    /**
     * delete Head on Department
     * @param		Long departmentId
     * @return		Department
     */     
	@RequestMapping("/deleteHead")
	public Department deleteHead( @RequestParam(value="department.departmentId", required=true) Long departmentId )
	
	{
		if ( load( departmentId ) == null )
			return( null );

		if ( department.getHead() != null )
		{
			EmployeePrimaryKey pk = department.getHead().getEmployeePrimaryKey();
			
			// null out the parent first so there's no constraint during deletion
			department.setHead( null );
			try
			{
				DepartmentBusinessDelegate parentDelegate = DepartmentBusinessDelegate.getDepartmentInstance();

				// save it
				department = parentDelegate.saveDepartment( department );
			}
			catch( Exception exc )
			{
				LOGGER.info( "DepartmentController:deleteHead() failed to save Department - " + exc.getMessage() );
			}
			
			try
			{
				// safe to delete the child			
				EmployeeBusinessDelegate childDelegate = EmployeeBusinessDelegate.getEmployeeInstance();
				childDelegate.delete( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "DepartmentController:deleteHead() failed  - " + exc.getMessage() );
			}
		}
		
		return department;
	}
	


    /**
     * Handles creating a Department BO
     * @return		Department
     */
    protected Department create()
    {
        try
        {       
			this.department = DepartmentBusinessDelegate.getDepartmentInstance().createDepartment( department );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "DepartmentController:create() - exception Department - " + exc.getMessage());        	
        	return null;
        }
        
        return this.department;
    }

    /**
     * Handles updating a Department BO
     * @return		Department
     */    
    protected Department update()
    {
    	// store provided data
        Department tmp = department;

        // load actual data from db
    	load();
    	
    	// copy provided data into actual data
    	department.copyShallow( tmp );
    	
        try
        {                        	        
			// create the DepartmentBusiness Delegate            
			DepartmentBusinessDelegate delegate = DepartmentBusinessDelegate.getDepartmentInstance();
            this.department = delegate.saveDepartment( department );
            
            if ( this.department != null )
                LOGGER.info( "DepartmentController:update() - successfully updated Department - " + department.toString() );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "DepartmentController:update() - successfully update Department - " + exc.getMessage());        	
        	return null;
        }
        
        return this.department;
        
    }


    /**
     * Returns true if the department is non-null and has it's primary key field(s) set
     * @return		boolean
     */
    protected boolean hasPrimaryKey()
    {
    	boolean hasPK = false;

		if ( department != null && department.getDepartmentPrimaryKey().hasBeenAssigned() == true )
		   hasPK = true;
		
		return( hasPK );
    }

    protected Department load()
    {
    	return( load( new Long( department.getDepartmentPrimaryKey().getFirstKey().toString() ) ));
    }

//************************************************************************    
// Attributes
//************************************************************************
    protected Department department = null;
    private static final Logger LOGGER = Logger.getLogger(Department.class.getName());
    
}


