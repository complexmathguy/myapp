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
 * Division PrimaryKey class.
 * 
 * @author    
 */
// AIB : #getPrimaryKeyClassDecl() 
public class DivisionPrimaryKey 

    extends BasePrimaryKey
{
// ~AIB

//************************************************************************
// Public Methods
//************************************************************************

    /** 
     * default constructor - should be normally used for dynamic instantiation
     */
    public DivisionPrimaryKey() 
    {
    }    

    /** 
     * single value constructor
     */

    public DivisionPrimaryKey( Object divisionId ) 
    {
    	this.divisionId = divisionId != null ? new Long( divisionId.toString() ) : null;
    }    

//************************************************************************
// Access Methods
//************************************************************************

// AIB : #getKeyFieldAccessMethods()
   /**
	* Returns the divisionId.
	* @return    Long
    */    
	public Long getDivisionId()
	{
		return( this.divisionId );
	}            
	
   /**
	* Assigns the divisionId.
	* @return    Long
    */    
	public void setDivisionId( Long id )
	{
		this.divisionId = id;
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
        
		keys.add( divisionId );

        return( keys );
    }

	public Object getFirstKey()
	{
		return( divisionId );
	}

 
//************************************************************************
// Protected / Private Methods
//************************************************************************

    
//************************************************************************
// Attributes
//************************************************************************

 	

// DO NOT ASSIGN VALUES DIRECTLY TO THE FOLLOWING ATTRIBUTES.  SET THE VALUES
// WITHIN THE Division class.

// AIB : #getKeyFieldDeclarations()
	public Long divisionId;
// ~AIB 	        

}


