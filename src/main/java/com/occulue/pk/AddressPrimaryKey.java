/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.primarykey;

import java.util.*;

import com.occulue.primarykey.*;

/**
 * Address PrimaryKey class.
 * 
 * @author    
 */
// AIB : #getPrimaryKeyClassDecl() 
public class AddressPrimaryKey 

    extends BasePrimaryKey
{
// ~AIB

//************************************************************************
// Public Methods
//************************************************************************

    /** 
     * default constructor - should be normally used for dynamic instantiation
     */
    public AddressPrimaryKey() 
    {
    }    

    /** 
     * single value constructor
     */

    public AddressPrimaryKey( Object addressId ) 
    {
    	this.addressId = addressId != null ? new Long( addressId.toString() ) : null;
    }    

//************************************************************************
// Access Methods
//************************************************************************

// AIB : #getKeyFieldAccessMethods()
   /**
	* Returns the addressId.
	* @return    Long
    */    
	public Long getAddressId()
	{
		return( this.addressId );
	}            
	
   /**
	* Assigns the addressId.
	* @return    Long
    */    
	public void setAddressId( Long id )
	{
		this.addressId = id;
	}            
	
// ~AIB 	         	     

    /**
     * Retrieves the value(s) as a single List
     * @return List
     */
    public List keys()
    {
		// assign the attributes to the Collection back to the parent
        ArrayList keys = new ArrayList();
        
		keys.add( addressId );

        return( keys );
    }

	public Object getFirstKey()
	{
		return( addressId );
	}

 
//************************************************************************
// Protected / Private Methods
//************************************************************************

    
//************************************************************************
// Attributes
//************************************************************************

 	

// DO NOT ASSIGN VALUES DIRECTLY TO THE FOLLOWING ATTRIBUTES.  SET THE VALUES
// WITHIN THE Address class.

// AIB : #getKeyFieldDeclarations()
	public Long addressId;
// ~AIB 	        

}


