/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.bo;

import java.util.*;

    import com.occulue.primarykey.*;
    import com.occulue.bo.*;
    
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Encapsulates data for business entity Company.
 * 
 * @author 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
// AIB : #getBOClassDecl()
 public class Company extends Base
		{
// ~AIB

//************************************************************************
// Constructors
//************************************************************************

    /** 
     * Default Constructor 
     */
    public Company() 
    {
    }   

//************************************************************************
// Accessor Methods
//************************************************************************

    /** 
     * Returns the CompanyPrimaryKey
     * @return CompanyPrimaryKey   
     */
    public CompanyPrimaryKey getCompanyPrimaryKey() 
    {    
    	    	CompanyPrimaryKey key = new CompanyPrimaryKey();
		key.setCompanyId( this.companyId );
        return( key );
    } 


// AIB : #getBOAccessorMethods(true)
             /**
    * Returns the name
  	* @return String	
	*/                    		    	    	    
	public String getName() 	    	   
	{
		return this.name;		
	}
	
	/**
              	* Assigns the name
    	* @param name	String
    	*/
    	public void setName( String name )
    	{
    		this.name = name;
    	}	
               /**
    * Returns the establishedOn
  	* @return java.util.Date	
	*/                    		    	    	    
	public java.util.Date getEstablishedOn() 	    	   
	{
		return this.establishedOn;		
	}
	
	/**
              	* Assigns the establishedOn
    	* @param establishedOn	java.util.Date
    	*/
    	public void setEstablishedOn( java.util.Date establishedOn )
    	{
    		this.establishedOn = establishedOn;
    	}	
               /**
    * Returns the revenue
  	* @return String	
	*/                    		    	    	    
	public String getRevenue() 	    	   
	{
		return this.revenue;		
	}
	
	/**
              	* Assigns the revenue
    	* @param revenue	String
    	*/
    	public void setRevenue( String revenue )
    	{
    		this.revenue = revenue;
    	}	
               /**
    * Returns the Employees
  	* @return Set<Employee>	
	*/                    		    	    	    
	public Set<Employee> getEmployees() 	    	   
	{
		return this.employees;		
	}
	
	/**
              	* Assigns the employees
    	* @param employees	Set<Employee>
    	*/
    	public void setEmployees( Set<Employee> employees )
    	{
    		this.employees = employees;
    	}	
               /**
    * Returns the Departments
  	* @return Set<Department>	
	*/                    		    	    	    
	public Set<Department> getDepartments() 	    	   
	{
		return this.departments;		
	}
	
	/**
              	* Assigns the departments
    	* @param departments	Set<Department>
    	*/
    	public void setDepartments( Set<Department> departments )
    	{
    		this.departments = departments;
    	}	
               /**
    * Returns the Divisions
  	* @return Set<Division>	
	*/                    		    	    	    
	public Set<Division> getDivisions() 	    	   
	{
		return this.divisions;		
	}
	
	/**
              	* Assigns the divisions
    	* @param divisions	Set<Division>
    	*/
    	public void setDivisions( Set<Division> divisions )
    	{
    		this.divisions = divisions;
    	}	
               /**
    * Returns the BoardMembers
  	* @return Set<Employee>	
	*/                    		    	    	    
	public Set<Employee> getBoardMembers() 	    	   
	{
		return this.boardMembers;		
	}
	
	/**
              	* Assigns the boardMembers
    	* @param boardMembers	Set<Employee>
    	*/
    	public void setBoardMembers( Set<Employee> boardMembers )
    	{
    		this.boardMembers = boardMembers;
    	}	
               /**
    * Returns the Address
  	* @return Address	
	*/                    		    	    	    
	public Address getAddress() 	    	   
	{
		return this.address;		
	}
	
	/**
              	* Assigns the address
    	* @param address	Address
    	*/
    	public void setAddress( Address address )
    	{
    		this.address = address;
    	}	
               /**
    * Returns the Type
  	* @return CompanyType	
	*/                    		    	    	    
	public CompanyType getType() 	    	   
	{
		return this.type;		
	}
	
	/**
              	* Assigns the type
    	* @param type	CompanyType
    	*/
    	public void setType( CompanyType type )
    	{
    		this.type = type;
    	}	
               /**
    * Returns the Industry
  	* @return Industry	
	*/                    		    	    	    
	public Industry getIndustry() 	    	   
	{
		return this.industry;		
	}
	
	/**
              	* Assigns the industry
    	* @param industry	Industry
    	*/
    	public void setIndustry( Industry industry )
    	{
    		this.industry = industry;
    	}	
               /**
    * Returns the companyId
  	* @return Long	
	*/                    		    	    	    
	public Long getCompanyId() 	    	   
	{
		return this.companyId;		
	}
	
	/**
              	* Assigns the companyId
    	* @param companyId	Long
    	*/
    	public void setCompanyId( Long companyId )
    	{
    		this.companyId = companyId;
    	}	
  
// ~AIB
 
    /**
     * Performs a shallow copy.
     * @param object 	Company		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Company copyShallow( Company object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Company:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        // Set member attributes
                
// AIB : #getCopyString( false )
        this.companyId = object.getCompanyId();
        this.name = object.getName();
        this.establishedOn = object.getEstablishedOn();
        this.revenue = object.getRevenue();
        this.type = object.getType();
        this.industry = object.getIndustry();
// ~AIB 

		return this;
    }

    /**
     * Performs a deep copy.
     * @param object 	Company		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Company copy( Company object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Company:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        copyShallow( object );
        // Set member attributes
                
// AIB : #getCopyString( true )
        if ( object.getEmployees() != null )
        {
    		this.employees = new HashSet<Employee>();
    		Employee tmp = null;
        	for ( Employee listEntry : object.getEmployees() )
        	{
        		tmp = new Employee();
        		tmp.copyShallow( listEntry );
        		this.employees.add( tmp );
        	}
        }
        else
        	this.employees = null;
        if ( object.getDepartments() != null )
        {
    		this.departments = new HashSet<Department>();
    		Department tmp = null;
        	for ( Department listEntry : object.getDepartments() )
        	{
        		tmp = new Department();
        		tmp.copyShallow( listEntry );
        		this.departments.add( tmp );
        	}
        }
        else
        	this.departments = null;
        if ( object.getDivisions() != null )
        {
    		this.divisions = new HashSet<Division>();
    		Division tmp = null;
        	for ( Division listEntry : object.getDivisions() )
        	{
        		tmp = new Division();
        		tmp.copyShallow( listEntry );
        		this.divisions.add( tmp );
        	}
        }
        else
        	this.divisions = null;
        if ( object.getBoardMembers() != null )
        {
    		this.boardMembers = new HashSet<Employee>();
    		Employee tmp = null;
        	for ( Employee listEntry : object.getBoardMembers() )
        	{
        		tmp = new Employee();
        		tmp.copyShallow( listEntry );
        		this.boardMembers.add( tmp );
        	}
        }
        else
        	this.boardMembers = null;
    	if ( object.getAddress() != null)
    	{
    		this.address = new Address();
    		this.address.copyShallow( object.getAddress() );
    	}
    	else
    		this.address = null;
// ~AIB 

		return( this );
    }

    /**
     * Returns a string representation of the object.
     * @return String
     */
    public String toString()
    {
        StringBuilder returnString = new StringBuilder();

        returnString.append( super.toString() + ", " );     

// AIB : #getToString( false )
		returnString.append( "companyId = " + this.companyId + ", ");
		returnString.append( "name = " + this.name + ", ");
		returnString.append( "establishedOn = " + this.establishedOn + ", ");
		returnString.append( "revenue = " + this.revenue + ", ");
		returnString.append( "type = " + this.type + ", ");
		returnString.append( "industry = " + this.industry + ", ");
// ~AIB 

        return returnString.toString();
    }

	public java.util.Collection<String> getAttributesByNameUserIdentifiesBy()
	{
		Collection<String> names = new java.util.ArrayList<String>();
				
	return( names );
	}	
	
    public String getIdentity()
    {
		StringBuilder identity = new StringBuilder( "Company" );
		
			identity.append(  "::" );
		identity.append( companyId );
	        return ( identity.toString() );
    }

    public String getObjectType()
    {
        return ("Company");
    }	

//************************************************************************
// Object Overloads
//************************************************************************

	public boolean equals( Object object )
	{
	    Object tmpObject = null;	    
	    if (this == object) 
	        return true;
	        
		if ( object == null )
			return false;
			
	    if (!(object instanceof Company)) 
	        return false;
	        
		Company bo = (Company)object;
		
		return( getCompanyPrimaryKey().equals( bo.getCompanyPrimaryKey() ) ); 
	}
	
	
// attributes

// AIB : #getAttributeDeclarations( true  )
protected Long companyId = null;
 protected String name = null;
 protected java.util.Date establishedOn = null;
 protected String revenue = null;
protected Set<Employee> employees = null;
protected Set<Department> departments = null;
protected Set<Division> divisions = null;
protected Set<Employee> boardMembers = null;
protected Address address = null;
 protected CompanyType type = CompanyType.getDefaultValue();
 protected Industry industry = Industry.getDefaultValue();
// ~AIB

}


