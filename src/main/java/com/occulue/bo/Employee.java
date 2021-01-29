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
 * Encapsulates data for business entity Employee.
 * 
 * @author 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
// AIB : #getBOClassDecl()
 public class Employee extends Base
		{
// ~AIB

//************************************************************************
// Constructors
//************************************************************************

    /** 
     * Default Constructor 
     */
    public Employee() 
    {
    }   

//************************************************************************
// Accessor Methods
//************************************************************************

    /** 
     * Returns the EmployeePrimaryKey
     * @return EmployeePrimaryKey   
     */
    public EmployeePrimaryKey getEmployeePrimaryKey() 
    {    
    	    	EmployeePrimaryKey key = new EmployeePrimaryKey();
		key.setEmployeeId( this.employeeId );
        return( key );
    } 


// AIB : #getBOAccessorMethods(true)
             /**
    * Returns the firstName
  	* @return String	
	*/                    		    	    	    
	public String getFirstName() 	    	   
	{
		return this.firstName;		
	}
	
	/**
              	* Assigns the firstName
    	* @param firstName	String
    	*/
    	public void setFirstName( String firstName )
    	{
    		this.firstName = firstName;
    	}	
               /**
    * Returns the lastName
  	* @return String	
	*/                    		    	    	    
	public String getLastName() 	    	   
	{
		return this.lastName;		
	}
	
	/**
              	* Assigns the lastName
    	* @param lastName	String
    	*/
    	public void setLastName( String lastName )
    	{
    		this.lastName = lastName;
    	}	
               /**
    * Returns the Type
  	* @return EmploymentType	
	*/                    		    	    	    
	public EmploymentType getType() 	    	   
	{
		return this.type;		
	}
	
	/**
              	* Assigns the type
    	* @param type	EmploymentType
    	*/
    	public void setType( EmploymentType type )
    	{
    		this.type = type;
    	}	
               /**
    * Returns the employeeId
  	* @return Long	
	*/                    		    	    	    
	public Long getEmployeeId() 	    	   
	{
		return this.employeeId;		
	}
	
	/**
              	* Assigns the employeeId
    	* @param employeeId	Long
    	*/
    	public void setEmployeeId( Long employeeId )
    	{
    		this.employeeId = employeeId;
    	}	
  
// ~AIB
 
    /**
     * Performs a shallow copy.
     * @param object 	Employee		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Employee copyShallow( Employee object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Employee:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        // Set member attributes
                
// AIB : #getCopyString( false )
        this.employeeId = object.getEmployeeId();
        this.firstName = object.getFirstName();
        this.lastName = object.getLastName();
        this.type = object.getType();
// ~AIB 

		return this;
    }

    /**
     * Performs a deep copy.
     * @param object 	Employee		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Employee copy( Employee object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Employee:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        copyShallow( object );
        // Set member attributes
                
// AIB : #getCopyString( true )
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
		returnString.append( "employeeId = " + this.employeeId + ", ");
		returnString.append( "firstName = " + this.firstName + ", ");
		returnString.append( "lastName = " + this.lastName + ", ");
		returnString.append( "type = " + this.type + ", ");
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
		StringBuilder identity = new StringBuilder( "Employee" );
		
			identity.append(  "::" );
		identity.append( employeeId );
	        return ( identity.toString() );
    }

    public String getObjectType()
    {
        return ("Employee");
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
			
	    if (!(object instanceof Employee)) 
	        return false;
	        
		Employee bo = (Employee)object;
		
		return( getEmployeePrimaryKey().equals( bo.getEmployeePrimaryKey() ) ); 
	}
	
	
// attributes

// AIB : #getAttributeDeclarations( true  )
protected Long employeeId = null;
 protected String firstName = null;
 protected String lastName = null;
 protected EmploymentType type = EmploymentType.getDefaultValue();
// ~AIB

}


