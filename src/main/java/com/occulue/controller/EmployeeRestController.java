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
 * Implements Struts action processing for business entity Employee.
 *
 * @author 
 */
@RestController
@RequestMapping("/Employee")
public class EmployeeRestController extends BaseSpringRestController
{

    /**
     * Handles saving a Employee BO.  if not key provided, calls create, otherwise calls save
     * @param		Employee employee
     * @return		Employee
     */
	@RequestMapping("/save")
    public Employee save( @RequestBody Employee employee )
    {
    	// assign locally
    	this.employee = employee;
    	
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
     * Handles deleting a Employee BO
     * @param		Long employeeId
     * @param 		Long[] childIds
     * @return		boolean
     */
    @RequestMapping("/delete")    
    public boolean delete( @RequestParam(value="employee.employeeId", required=false) Long employeeId, 
    						@RequestParam(value="childIds", required=false) Long[] childIds )
    {                
        try
        {
        	EmployeeBusinessDelegate delegate = EmployeeBusinessDelegate.getEmployeeInstance();
        	
        	if ( childIds == null || childIds.length == 0 )
        	{
        		delegate.delete( new EmployeePrimaryKey( employeeId ) );
        		LOGGER.info( "EmployeeController:delete() - successfully deleted Employee with key " + employee.getEmployeePrimaryKey().valuesAsCollection() );
        	}
        	else
        	{
        		for ( Long id : childIds )
        		{
        			try
        			{
        				delegate.delete( new EmployeePrimaryKey( id ) );
        			}
	                catch( Throwable exc )
	                {
	                	LOGGER.info( "EmployeeController:delete() - " + exc.getMessage() );
	                	return false;
	                }
        		}
        	}
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "EmployeeController:delete() - " + exc.getMessage() );
        	return false;        	
        }
        
        return true;
	}        
	
    /**
     * Handles loading a Employee BO
     * @param		Long employeeId
     * @return		Employee
     */    
    @RequestMapping("/load")
    public Employee load( @RequestParam(value="employee.employeeId", required=true) Long employeeId )
    {    	
        EmployeePrimaryKey pk = null;

    	try
        {  
    		System.out.println( "\n\n****Employee.load pk is " + employeeId );
        	if ( employeeId != null )
        	{
        		pk = new EmployeePrimaryKey( employeeId );
        		
        		// load the Employee
	            this.employee = EmployeeBusinessDelegate.getEmployeeInstance().getEmployee( pk );
	            
	            LOGGER.info( "EmployeeController:load() - successfully loaded - " + this.employee.toString() );             
			}
			else
			{
	            LOGGER.info( "EmployeeController:load() - unable to locate the primary key as an attribute or a selection for - " + employee.toString() );
	            return null;
			}	            
        }
        catch( Throwable exc )
        {
            LOGGER.info( "EmployeeController:load() - failed to load Employee using Id " + employeeId + ", " + exc.getMessage() );
            return null;
        }

        return employee;

    }

    /**
     * Handles loading all Employee business objects
     * @return		List<Employee>
     */
    @RequestMapping("/loadAll")
    public List<Employee> loadAll()
    {                
        List<Employee> employeeList = null;
        
    	try
        {                        
            // load the Employee
            employeeList = EmployeeBusinessDelegate.getEmployeeInstance().getAllEmployee();
            
            if ( employeeList != null )
                LOGGER.info(  "EmployeeController:loadAllEmployee() - successfully loaded all Employees" );
        }
        catch( Throwable exc )
        {
            LOGGER.info(  "EmployeeController:loadAll() - failed to load all Employees - " + exc.getMessage() );
        	return null;
            
        }

        return employeeList;
                            
    }


// findAllBy methods




    /**
     * Handles creating a Employee BO
     * @return		Employee
     */
    protected Employee create()
    {
        try
        {       
			this.employee = EmployeeBusinessDelegate.getEmployeeInstance().createEmployee( employee );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "EmployeeController:create() - exception Employee - " + exc.getMessage());        	
        	return null;
        }
        
        return this.employee;
    }

    /**
     * Handles updating a Employee BO
     * @return		Employee
     */    
    protected Employee update()
    {
    	// store provided data
        Employee tmp = employee;

        // load actual data from db
    	load();
    	
    	// copy provided data into actual data
    	employee.copyShallow( tmp );
    	
        try
        {                        	        
			// create the EmployeeBusiness Delegate            
			EmployeeBusinessDelegate delegate = EmployeeBusinessDelegate.getEmployeeInstance();
            this.employee = delegate.saveEmployee( employee );
            
            if ( this.employee != null )
                LOGGER.info( "EmployeeController:update() - successfully updated Employee - " + employee.toString() );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "EmployeeController:update() - successfully update Employee - " + exc.getMessage());        	
        	return null;
        }
        
        return this.employee;
        
    }


    /**
     * Returns true if the employee is non-null and has it's primary key field(s) set
     * @return		boolean
     */
    protected boolean hasPrimaryKey()
    {
    	boolean hasPK = false;

		if ( employee != null && employee.getEmployeePrimaryKey().hasBeenAssigned() == true )
		   hasPK = true;
		
		return( hasPK );
    }

    protected Employee load()
    {
    	return( load( new Long( employee.getEmployeePrimaryKey().getFirstKey().toString() ) ));
    }

//************************************************************************    
// Attributes
//************************************************************************
    protected Employee employee = null;
    private static final Logger LOGGER = Logger.getLogger(Employee.class.getName());
    
}


