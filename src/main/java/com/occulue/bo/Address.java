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
 * Encapsulates data for business entity Address.
 * 
 * @author 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
// AIB : #getBOClassDecl()
 public class Address extends Base
		{
// ~AIB

//************************************************************************
// Constructors
//************************************************************************

    /** 
     * Default Constructor 
     */
    public Address() 
    {
    }   

//************************************************************************
// Accessor Methods
//************************************************************************

    /** 
     * Returns the AddressPrimaryKey
     * @return AddressPrimaryKey   
     */
    public AddressPrimaryKey getAddressPrimaryKey() 
    {    
    	    	AddressPrimaryKey key = new AddressPrimaryKey();
		key.setAddressId( this.addressId );
        return( key );
    } 


// AIB : #getBOAccessorMethods(true)
             /**
    * Returns the street
  	* @return String	
	*/                    		    	    	    
	public String getStreet() 	    	   
	{
		return this.street;		
	}
	
	/**
              	* Assigns the street
    	* @param street	String
    	*/
    	public void setStreet( String street )
    	{
    		this.street = street;
    	}	
               /**
    * Returns the city
  	* @return String	
	*/                    		    	    	    
	public String getCity() 	    	   
	{
		return this.city;		
	}
	
	/**
              	* Assigns the city
    	* @param city	String
    	*/
    	public void setCity( String city )
    	{
    		this.city = city;
    	}	
               /**
    * Returns the state
  	* @return String	
	*/                    		    	    	    
	public String getState() 	    	   
	{
		return this.state;		
	}
	
	/**
              	* Assigns the state
    	* @param state	String
    	*/
    	public void setState( String state )
    	{
    		this.state = state;
    	}	
               /**
    * Returns the zipCode
  	* @return String	
	*/                    		    	    	    
	public String getZipCode() 	    	   
	{
		return this.zipCode;		
	}
	
	/**
              	* Assigns the zipCode
    	* @param zipCode	String
    	*/
    	public void setZipCode( String zipCode )
    	{
    		this.zipCode = zipCode;
    	}	
               /**
    * Returns the addressId
  	* @return Long	
	*/                    		    	    	    
	public Long getAddressId() 	    	   
	{
		return this.addressId;		
	}
	
	/**
              	* Assigns the addressId
    	* @param addressId	Long
    	*/
    	public void setAddressId( Long addressId )
    	{
    		this.addressId = addressId;
    	}	
  
// ~AIB
 
    /**
     * Performs a shallow copy.
     * @param object 	Address		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Address copyShallow( Address object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Address:copy(..) - object cannot be null.");           
        }

        // Call base class copy
        super.copy( object );
        
        // Set member attributes
                
// AIB : #getCopyString( false )
        this.addressId = object.getAddressId();
        this.street = object.getStreet();
        this.city = object.getCity();
        this.state = object.getState();
        this.zipCode = object.getZipCode();
// ~AIB 

		return this;
    }

    /**
     * Performs a deep copy.
     * @param object 	Address		copy source
     * @exception IllegalArgumentException 	Thrown if the passed in obj is null. It is also
     * 							thrown if the passed in businessObject is not of the correct type.
     */
    public Address copy( Address object ) 
    throws IllegalArgumentException
    {
        if ( object == null )
        {
            throw new IllegalArgumentException(" Address:copy(..) - object cannot be null.");           
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
		returnString.append( "addressId = " + this.addressId + ", ");
		returnString.append( "street = " + this.street + ", ");
		returnString.append( "city = " + this.city + ", ");
		returnString.append( "state = " + this.state + ", ");
		returnString.append( "zipCode = " + this.zipCode + ", ");
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
		StringBuilder identity = new StringBuilder( "Address" );
		
			identity.append(  "::" );
		identity.append( addressId );
	        return ( identity.toString() );
    }

    public String getObjectType()
    {
        return ("Address");
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
			
	    if (!(object instanceof Address)) 
	        return false;
	        
		Address bo = (Address)object;
		
		return( getAddressPrimaryKey().equals( bo.getAddressPrimaryKey() ) ); 
	}
	
	
// attributes

// AIB : #getAttributeDeclarations( true  )
protected Long addressId = null;
 protected String street = null;
 protected String city = null;
 protected String state = null;
 protected String zipCode = null;
// ~AIB

}


