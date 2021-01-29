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
 * Encapsulates data for business entity Department.
 * 
 * @author 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
// AIB : #getBOClassDecl()
 public class Department extends Base
		{
// ~AIB

//************************************************************************
// Constructors
//************************************************************************

    /** 
     * Default Constructor 
     */
    public Department() 
    {
    }   

//************************************************************************
// Accessor Methods
//************************************************************************

    /** 
     * Returns the DepartmentPrimaryKey
     * @return DepartmentPrimaryKey   
     */
    public DepartmentPrimaryKey getDepartmentPrimaryKey() 
    {    
    	    	DepartmentPrimaryKey key = new DepartmentPrimaryKey();
		key.setDepartmentId( this.departmentId );
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
    * Returns the Head
  	* @return Employee	
	*/                    		    	    	    
	public Employee getHead() 	    	   
	{
		return this.head;		
	}
	
	/**
              	* Assigns the head
    	* @param head	Employee
    	*/
    	public void setHead( Employee head )
    	{
    		this.head = head;
    	}	
               /**
    * Returns the departmentId
  	* @return Long	
	*/                    		    	    	    
	public Long getDepartmentId() 	    	   
	{
		return this.departmentId;		
	}
	
	/**
              	* Assigns the departmentId
    	* @param departmentId	Long
    	*/
    	public void setDepartmentId( Long departmentId )
    	{
    		this.departmentId = departmentId;
    	}	
  
// ~AIB
 
    /**
     * Performs a shallow copy.
     * @param object 	Department		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Department copyShallow( Department object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Department:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        // Set member attributes
                
// AIB : #getCopyString( false )
        this.departmentId = object.getDepartmentId();
        this.name = object.getName();
// ~AIB 

		return this;
    }

    /**
     * Performs a deep copy.
     * @param object 	Department		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Department copy( Department object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Department:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        copyShallow( object );
        // Set member attributes
                
// AIB : #getCopyString( true )
    	if ( object.getHead() != null)
    	{
    		this.head = new Employee();
    		this.head.copyShallow( object.getHead() );
    	}
    	else
    		this.head = null;
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
		returnString.append( "departmentId = " + this.departmentId + ", ");
		returnString.append( "name = " + this.name + ", ");
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
		StringBuilder identity = new StringBuilder( "Department" );
		
			identity.append(  "::" );
		identity.append( departmentId );
	        return ( identity.toString() );
    }

    public String getObjectType()
    {
        return ("Department");
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
			
	    if (!(object instanceof Department)) 
	        return false;
	        
		Department bo = (Department)object;
		
		return( getDepartmentPrimaryKey().equals( bo.getDepartmentPrimaryKey() ) ); 
	}
	
	
// attributes

// AIB : #getAttributeDeclarations( true  )
protected Long departmentId = null;
 protected String name = null;
protected Employee head = null;
// ~AIB

}


