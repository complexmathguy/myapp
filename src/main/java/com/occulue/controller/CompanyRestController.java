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
 * Implements Struts action processing for business entity Company.
 *
 * @author 
 */
@RestController
@RequestMapping("/Company")
public class CompanyRestController extends BaseSpringRestController
{

    /**
     * Handles saving a Company BO.  if not key provided, calls create, otherwise calls save
     * @param		Company company
     * @return		Company
     */
	@RequestMapping("/save")
    public Company save( @RequestBody Company company )
    {
    	// assign locally
    	this.company = company;
    	
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
     * Handles deleting a Company BO
     * @param		Long companyId
     * @param 		Long[] childIds
     * @return		boolean
     */
    @RequestMapping("/delete")    
    public boolean delete( @RequestParam(value="company.companyId", required=false) Long companyId, 
    						@RequestParam(value="childIds", required=false) Long[] childIds )
    {                
        try
        {
        	CompanyBusinessDelegate delegate = CompanyBusinessDelegate.getCompanyInstance();
        	
        	if ( childIds == null || childIds.length == 0 )
        	{
        		delegate.delete( new CompanyPrimaryKey( companyId ) );
        		LOGGER.info( "CompanyController:delete() - successfully deleted Company with key " + company.getCompanyPrimaryKey().valuesAsCollection() );
        	}
        	else
        	{
        		for ( Long id : childIds )
        		{
        			try
        			{
        				delegate.delete( new CompanyPrimaryKey( id ) );
        			}
	                catch( Throwable exc )
	                {
	                	LOGGER.info( "CompanyController:delete() - " + exc.getMessage() );
	                	return false;
	                }
        		}
        	}
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "CompanyController:delete() - " + exc.getMessage() );
        	return false;        	
        }
        
        return true;
	}        
	
    /**
     * Handles loading a Company BO
     * @param		Long companyId
     * @return		Company
     */    
    @RequestMapping("/load")
    public Company load( @RequestParam(value="company.companyId", required=true) Long companyId )
    {    	
        CompanyPrimaryKey pk = null;

    	try
        {  
    		System.out.println( "\n\n****Company.load pk is " + companyId );
        	if ( companyId != null )
        	{
        		pk = new CompanyPrimaryKey( companyId );
        		
        		// load the Company
	            this.company = CompanyBusinessDelegate.getCompanyInstance().getCompany( pk );
	            
	            LOGGER.info( "CompanyController:load() - successfully loaded - " + this.company.toString() );             
			}
			else
			{
	            LOGGER.info( "CompanyController:load() - unable to locate the primary key as an attribute or a selection for - " + company.toString() );
	            return null;
			}	            
        }
        catch( Throwable exc )
        {
            LOGGER.info( "CompanyController:load() - failed to load Company using Id " + companyId + ", " + exc.getMessage() );
            return null;
        }

        return company;

    }

    /**
     * Handles loading all Company business objects
     * @return		List<Company>
     */
    @RequestMapping("/loadAll")
    public List<Company> loadAll()
    {                
        List<Company> companyList = null;
        
    	try
        {                        
            // load the Company
            companyList = CompanyBusinessDelegate.getCompanyInstance().getAllCompany();
            
            if ( companyList != null )
                LOGGER.info(  "CompanyController:loadAllCompany() - successfully loaded all Companys" );
        }
        catch( Throwable exc )
        {
            LOGGER.info(  "CompanyController:loadAll() - failed to load all Companys - " + exc.getMessage() );
        	return null;
            
        }

        return companyList;
                            
    }


// findAllBy methods


    /**
     * save Address on Company
     * @param		Long	companyId
     * @param		Long	childId
     * @param		Company company
     * @return		Company
     */     
	@RequestMapping("/saveAddress")
	public Company saveAddress( @RequestParam(value="company.companyId", required=true) Long companyId, 
											@RequestParam(value="childIds", required=true) Long childId, @RequestBody Company company )
	{
		if ( load( companyId ) == null )
			return( null );
		
		if ( childId != null )
		{
			AddressBusinessDelegate childDelegate 	= AddressBusinessDelegate.getAddressInstance();
			CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();			
			Address child 							= null;

			try
			{
				child = childDelegate.getAddress( new AddressPrimaryKey( childId ) );
			}
            catch( Throwable exc )
            {
            	LOGGER.info( "CompanyController:saveAddress() failed to get Address using id " + childId + " - " + exc.getMessage() );
            	return null;
            }
	
			company.setAddress( child );
		
			try
			{
				// save it
				parentDelegate.saveCompany( company );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:saveAddress() failed saving parent Company - " + exc.getMessage() );
			}
		}
		
		return company;
	}

    /**
     * delete Address on Company
     * @param		Long companyId
     * @return		Company
     */     
	@RequestMapping("/deleteAddress")
	public Company deleteAddress( @RequestParam(value="company.companyId", required=true) Long companyId )
	
	{
		if ( load( companyId ) == null )
			return( null );

		if ( company.getAddress() != null )
		{
			AddressPrimaryKey pk = company.getAddress().getAddressPrimaryKey();
			
			// null out the parent first so there's no constraint during deletion
			company.setAddress( null );
			try
			{
				CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();

				// save it
				company = parentDelegate.saveCompany( company );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:deleteAddress() failed to save Company - " + exc.getMessage() );
			}
			
			try
			{
				// safe to delete the child			
				AddressBusinessDelegate childDelegate = AddressBusinessDelegate.getAddressInstance();
				childDelegate.delete( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:deleteAddress() failed  - " + exc.getMessage() );
			}
		}
		
		return company;
	}
	

    /**
     * save Employees on Company
     * @param		Long companyId
     * @param		Long childId
     * @param		Long[] childIds
     * @return		Company
     */     
	@RequestMapping("/saveEmployees")
	public Company saveEmployees( @RequestParam(value="company.companyId", required=false) Long companyId, 
											@RequestParam(value="childIds", required=false) Long childId, @RequestParam("") Long[] childIds )
	{
		if ( load( companyId ) == null )
			return( null );
		
		EmployeePrimaryKey pk 					= null;
		Employee child							= null;
		EmployeeBusinessDelegate childDelegate 	= EmployeeBusinessDelegate.getEmployeeInstance();
		CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();		
		
		if ( childId != null || childIds.length == 0 )// creating or saving one
		{
			pk = new EmployeePrimaryKey( childId );
			
			try
			{
				// find the Employee
				child = childDelegate.getEmployee( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:saveEmployees() failed get child Employee using id " + childId  + "- " + exc.getMessage() );
				return( null );
			}
			
			// add it to the Employees 
			company.getEmployees().add( child );				
		}
		else
		{
			// clear out the Employees but 
			company.getEmployees().clear();
			
			// finally, find each child and add it
			if ( childIds != null )
			{
				for( Long id : childIds )
				{
					pk = new EmployeePrimaryKey( id );
					try
					{
						// find the Employee
						child = childDelegate.getEmployee( pk );
						// add it to the Employees List
						company.getEmployees().add( child );
					}
					catch( Exception exc )
					{
						LOGGER.info( "CompanyController:saveEmployees() failed get child Employee using id " + id  + "- " + exc.getMessage() );
					}
				}
			}
		}

		try
		{
			// save the Company
			parentDelegate.saveCompany( company );
		}
		catch( Exception exc )
		{
			LOGGER.info( "CompanyController:saveEmployees() failed saving parent Company - " + exc.getMessage() );
		}

		return company;
	}

    /**
     * delete Employees on Company
     * @param		Long companyId
     * @param		Long[] childIds
     * @return		Company
     */     	
	@RequestMapping("/deleteEmployees")
	public Company deleteEmployees( @RequestParam(value="company.companyId", required=true) Long companyId, 
											@RequestParam(value="childIds", required=false) Long[] childIds )
	{		
		if ( load( companyId ) == null )
			return( null );

		if ( childIds != null || childIds.length == 0 )
		{
			EmployeePrimaryKey pk 					= null;
			EmployeeBusinessDelegate childDelegate 	= EmployeeBusinessDelegate.getEmployeeInstance();
			CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();
			Set<Employee> children					= company.getEmployees();
			Employee child 							= null;
			
			for( Long id : childIds )
			{
				try
				{
					pk = new EmployeePrimaryKey( id );
					
					// first remove the relevant child from the list
					child = childDelegate.getEmployee( pk );
					children.remove( child );
					
					// then safe to delete the child				
					childDelegate.delete( pk );
				}
				catch( Exception exc )
				{
					LOGGER.info( "CompanyController:deleteEmployees() failed - " + exc.getMessage() );
				}
			}
			
			// assign the modified list of Employee back to the company
			company.setEmployees( children );			
			// save it 
			try
			{
				company = parentDelegate.saveCompany( company );
			}
			catch( Throwable exc )
			{
				LOGGER.info( "CompanyController:deleteEmployees() failed to save the Company - " + exc.getMessage() );
			}
		}
		
		return company;
	}

    /**
     * save Departments on Company
     * @param		Long companyId
     * @param		Long childId
     * @param		Long[] childIds
     * @return		Company
     */     
	@RequestMapping("/saveDepartments")
	public Company saveDepartments( @RequestParam(value="company.companyId", required=false) Long companyId, 
											@RequestParam(value="childIds", required=false) Long childId, @RequestParam("") Long[] childIds )
	{
		if ( load( companyId ) == null )
			return( null );
		
		DepartmentPrimaryKey pk 					= null;
		Department child							= null;
		DepartmentBusinessDelegate childDelegate 	= DepartmentBusinessDelegate.getDepartmentInstance();
		CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();		
		
		if ( childId != null || childIds.length == 0 )// creating or saving one
		{
			pk = new DepartmentPrimaryKey( childId );
			
			try
			{
				// find the Department
				child = childDelegate.getDepartment( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:saveDepartments() failed get child Department using id " + childId  + "- " + exc.getMessage() );
				return( null );
			}
			
			// add it to the Departments 
			company.getDepartments().add( child );				
		}
		else
		{
			// clear out the Departments but 
			company.getDepartments().clear();
			
			// finally, find each child and add it
			if ( childIds != null )
			{
				for( Long id : childIds )
				{
					pk = new DepartmentPrimaryKey( id );
					try
					{
						// find the Department
						child = childDelegate.getDepartment( pk );
						// add it to the Departments List
						company.getDepartments().add( child );
					}
					catch( Exception exc )
					{
						LOGGER.info( "CompanyController:saveDepartments() failed get child Department using id " + id  + "- " + exc.getMessage() );
					}
				}
			}
		}

		try
		{
			// save the Company
			parentDelegate.saveCompany( company );
		}
		catch( Exception exc )
		{
			LOGGER.info( "CompanyController:saveDepartments() failed saving parent Company - " + exc.getMessage() );
		}

		return company;
	}

    /**
     * delete Departments on Company
     * @param		Long companyId
     * @param		Long[] childIds
     * @return		Company
     */     	
	@RequestMapping("/deleteDepartments")
	public Company deleteDepartments( @RequestParam(value="company.companyId", required=true) Long companyId, 
											@RequestParam(value="childIds", required=false) Long[] childIds )
	{		
		if ( load( companyId ) == null )
			return( null );

		if ( childIds != null || childIds.length == 0 )
		{
			DepartmentPrimaryKey pk 					= null;
			DepartmentBusinessDelegate childDelegate 	= DepartmentBusinessDelegate.getDepartmentInstance();
			CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();
			Set<Department> children					= company.getDepartments();
			Department child 							= null;
			
			for( Long id : childIds )
			{
				try
				{
					pk = new DepartmentPrimaryKey( id );
					
					// first remove the relevant child from the list
					child = childDelegate.getDepartment( pk );
					children.remove( child );
					
					// then safe to delete the child				
					childDelegate.delete( pk );
				}
				catch( Exception exc )
				{
					LOGGER.info( "CompanyController:deleteDepartments() failed - " + exc.getMessage() );
				}
			}
			
			// assign the modified list of Department back to the company
			company.setDepartments( children );			
			// save it 
			try
			{
				company = parentDelegate.saveCompany( company );
			}
			catch( Throwable exc )
			{
				LOGGER.info( "CompanyController:deleteDepartments() failed to save the Company - " + exc.getMessage() );
			}
		}
		
		return company;
	}

    /**
     * save Divisions on Company
     * @param		Long companyId
     * @param		Long childId
     * @param		Long[] childIds
     * @return		Company
     */     
	@RequestMapping("/saveDivisions")
	public Company saveDivisions( @RequestParam(value="company.companyId", required=false) Long companyId, 
											@RequestParam(value="childIds", required=false) Long childId, @RequestParam("") Long[] childIds )
	{
		if ( load( companyId ) == null )
			return( null );
		
		DivisionPrimaryKey pk 					= null;
		Division child							= null;
		DivisionBusinessDelegate childDelegate 	= DivisionBusinessDelegate.getDivisionInstance();
		CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();		
		
		if ( childId != null || childIds.length == 0 )// creating or saving one
		{
			pk = new DivisionPrimaryKey( childId );
			
			try
			{
				// find the Division
				child = childDelegate.getDivision( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:saveDivisions() failed get child Division using id " + childId  + "- " + exc.getMessage() );
				return( null );
			}
			
			// add it to the Divisions 
			company.getDivisions().add( child );				
		}
		else
		{
			// clear out the Divisions but 
			company.getDivisions().clear();
			
			// finally, find each child and add it
			if ( childIds != null )
			{
				for( Long id : childIds )
				{
					pk = new DivisionPrimaryKey( id );
					try
					{
						// find the Division
						child = childDelegate.getDivision( pk );
						// add it to the Divisions List
						company.getDivisions().add( child );
					}
					catch( Exception exc )
					{
						LOGGER.info( "CompanyController:saveDivisions() failed get child Division using id " + id  + "- " + exc.getMessage() );
					}
				}
			}
		}

		try
		{
			// save the Company
			parentDelegate.saveCompany( company );
		}
		catch( Exception exc )
		{
			LOGGER.info( "CompanyController:saveDivisions() failed saving parent Company - " + exc.getMessage() );
		}

		return company;
	}

    /**
     * delete Divisions on Company
     * @param		Long companyId
     * @param		Long[] childIds
     * @return		Company
     */     	
	@RequestMapping("/deleteDivisions")
	public Company deleteDivisions( @RequestParam(value="company.companyId", required=true) Long companyId, 
											@RequestParam(value="childIds", required=false) Long[] childIds )
	{		
		if ( load( companyId ) == null )
			return( null );

		if ( childIds != null || childIds.length == 0 )
		{
			DivisionPrimaryKey pk 					= null;
			DivisionBusinessDelegate childDelegate 	= DivisionBusinessDelegate.getDivisionInstance();
			CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();
			Set<Division> children					= company.getDivisions();
			Division child 							= null;
			
			for( Long id : childIds )
			{
				try
				{
					pk = new DivisionPrimaryKey( id );
					
					// first remove the relevant child from the list
					child = childDelegate.getDivision( pk );
					children.remove( child );
					
					// then safe to delete the child				
					childDelegate.delete( pk );
				}
				catch( Exception exc )
				{
					LOGGER.info( "CompanyController:deleteDivisions() failed - " + exc.getMessage() );
				}
			}
			
			// assign the modified list of Division back to the company
			company.setDivisions( children );			
			// save it 
			try
			{
				company = parentDelegate.saveCompany( company );
			}
			catch( Throwable exc )
			{
				LOGGER.info( "CompanyController:deleteDivisions() failed to save the Company - " + exc.getMessage() );
			}
		}
		
		return company;
	}

    /**
     * save BoardMembers on Company
     * @param		Long companyId
     * @param		Long childId
     * @param		Long[] childIds
     * @return		Company
     */     
	@RequestMapping("/saveBoardMembers")
	public Company saveBoardMembers( @RequestParam(value="company.companyId", required=false) Long companyId, 
											@RequestParam(value="childIds", required=false) Long childId, @RequestParam("") Long[] childIds )
	{
		if ( load( companyId ) == null )
			return( null );
		
		EmployeePrimaryKey pk 					= null;
		Employee child							= null;
		EmployeeBusinessDelegate childDelegate 	= EmployeeBusinessDelegate.getEmployeeInstance();
		CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();		
		
		if ( childId != null || childIds.length == 0 )// creating or saving one
		{
			pk = new EmployeePrimaryKey( childId );
			
			try
			{
				// find the Employee
				child = childDelegate.getEmployee( pk );
			}
			catch( Exception exc )
			{
				LOGGER.info( "CompanyController:saveBoardMembers() failed get child Employee using id " + childId  + "- " + exc.getMessage() );
				return( null );
			}
			
			// add it to the BoardMembers 
			company.getBoardMembers().add( child );				
		}
		else
		{
			// clear out the BoardMembers but 
			company.getBoardMembers().clear();
			
			// finally, find each child and add it
			if ( childIds != null )
			{
				for( Long id : childIds )
				{
					pk = new EmployeePrimaryKey( id );
					try
					{
						// find the Employee
						child = childDelegate.getEmployee( pk );
						// add it to the BoardMembers List
						company.getBoardMembers().add( child );
					}
					catch( Exception exc )
					{
						LOGGER.info( "CompanyController:saveBoardMembers() failed get child Employee using id " + id  + "- " + exc.getMessage() );
					}
				}
			}
		}

		try
		{
			// save the Company
			parentDelegate.saveCompany( company );
		}
		catch( Exception exc )
		{
			LOGGER.info( "CompanyController:saveBoardMembers() failed saving parent Company - " + exc.getMessage() );
		}

		return company;
	}

    /**
     * delete BoardMembers on Company
     * @param		Long companyId
     * @param		Long[] childIds
     * @return		Company
     */     	
	@RequestMapping("/deleteBoardMembers")
	public Company deleteBoardMembers( @RequestParam(value="company.companyId", required=true) Long companyId, 
											@RequestParam(value="childIds", required=false) Long[] childIds )
	{		
		if ( load( companyId ) == null )
			return( null );

		if ( childIds != null || childIds.length == 0 )
		{
			EmployeePrimaryKey pk 					= null;
			EmployeeBusinessDelegate childDelegate 	= EmployeeBusinessDelegate.getEmployeeInstance();
			CompanyBusinessDelegate parentDelegate = CompanyBusinessDelegate.getCompanyInstance();
			Set<Employee> children					= company.getBoardMembers();
			Employee child 							= null;
			
			for( Long id : childIds )
			{
				try
				{
					pk = new EmployeePrimaryKey( id );
					
					// first remove the relevant child from the list
					child = childDelegate.getEmployee( pk );
					children.remove( child );
					
					// then safe to delete the child				
					childDelegate.delete( pk );
				}
				catch( Exception exc )
				{
					LOGGER.info( "CompanyController:deleteBoardMembers() failed - " + exc.getMessage() );
				}
			}
			
			// assign the modified list of Employee back to the company
			company.setBoardMembers( children );			
			// save it 
			try
			{
				company = parentDelegate.saveCompany( company );
			}
			catch( Throwable exc )
			{
				LOGGER.info( "CompanyController:deleteBoardMembers() failed to save the Company - " + exc.getMessage() );
			}
		}
		
		return company;
	}


    /**
     * Handles creating a Company BO
     * @return		Company
     */
    protected Company create()
    {
        try
        {       
			this.company = CompanyBusinessDelegate.getCompanyInstance().createCompany( company );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "CompanyController:create() - exception Company - " + exc.getMessage());        	
        	return null;
        }
        
        return this.company;
    }

    /**
     * Handles updating a Company BO
     * @return		Company
     */    
    protected Company update()
    {
    	// store provided data
        Company tmp = company;

        // load actual data from db
    	load();
    	
    	// copy provided data into actual data
    	company.copyShallow( tmp );
    	
        try
        {                        	        
			// create the CompanyBusiness Delegate            
			CompanyBusinessDelegate delegate = CompanyBusinessDelegate.getCompanyInstance();
            this.company = delegate.saveCompany( company );
            
            if ( this.company != null )
                LOGGER.info( "CompanyController:update() - successfully updated Company - " + company.toString() );
        }
        catch( Throwable exc )
        {
        	LOGGER.info( "CompanyController:update() - successfully update Company - " + exc.getMessage());        	
        	return null;
        }
        
        return this.company;
        
    }


    /**
     * Returns true if the company is non-null and has it's primary key field(s) set
     * @return		boolean
     */
    protected boolean hasPrimaryKey()
    {
    	boolean hasPK = false;

		if ( company != null && company.getCompanyPrimaryKey().hasBeenAssigned() == true )
		   hasPK = true;
		
		return( hasPK );
    }

    protected Company load()
    {
    	return( load( new Long( company.getCompanyPrimaryKey().getFirstKey().toString() ) ));
    }

//************************************************************************    
// Attributes
//************************************************************************
    protected Company company = null;
    private static final Logger LOGGER = Logger.getLogger(Company.class.getName());
    
}


