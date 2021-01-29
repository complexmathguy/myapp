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
 * Encapsulates data for business entity Division.
 * 
 * @author 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
// AIB : #getBOClassDecl()
 public class Division extends Base
		{
// ~AIB

//************************************************************************
// Constructors
//************************************************************************

    /** 
     * Default Constructor 
     */
    public Division() 
    {
    }   

//************************************************************************
// Accessor Methods
//************************************************************************

    /** 
     * Returns the DivisionPrimaryKey
     * @return DivisionPrimaryKey   
     */
    public DivisionPrimaryKey getDivisionPrimaryKey() 
    {    
    	    	DivisionPrimaryKey key = new DivisionPrimaryKey();
		key.setDivisionId( this.divisionId );
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
    * Returns the divisionId
  	* @return Long	
	*/                    		    	    	    
	public Long getDivisionId() 	    	   
	{
		return this.divisionId;		
	}
	
	/**
              	* Assigns the divisionId
    	* @param divisionId	Long
    	*/
    	public void setDivisionId( Long divisionId )
    	{
    		this.divisionId = divisionId;
    	}	
  
// ~AIB
 
    /**
     * Performs a shallow copy.
     * @param object 	Division		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Division copyShallow( Division object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Division:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        // Set member attributes
                
// AIB : #getCopyString( false )
        this.divisionId = object.getDivisionId();
        this.name = object.getName();
// ~AIB 

		return this;
    }

    /**
     * Performs a deep copy.
     * @param object 	Division		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Division copy( Division object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Division:copy(..) - object cannot be null.");           
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
		returnString.append( "divisionId = " + this.divisionId + ", ");
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
		StringBuilder identity = new StringBuilder( "Division" );
		
			identity.append(  "::" );
		identity.append( divisionId );
	        return ( identity.toString() );
    }

    public String getObjectType()
    {
        return ("Division");
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
			
	    if (!(object instanceof Division)) 
	        return false;
	        
		Division bo = (Division)object;
		
		return( getDivisionPrimaryKey().equals( bo.getDivisionPrimaryKey() ) ); 
	}
	
	
// attributes

// AIB : #getAttributeDeclarations( true  )
protected Long divisionId = null;
 protected String name = null;
protected Employee head = null;
// ~AIB

}


