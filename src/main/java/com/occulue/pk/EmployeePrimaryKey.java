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
 * Employee PrimaryKey class.
 * 
 * @author    
 */
// AIB : #getPrimaryKeyClassDecl() 
public class EmployeePrimaryKey 

    extends BasePrimaryKey
{
// ~AIB

//************************************************************************
// Public Methods
//************************************************************************

    /** 
     * default constructor - should be normally used for dynamic instantiation
     */
    public EmployeePrimaryKey() 
    {
    }    

    /** 
     * single value constructor
     */

    public EmployeePrimaryKey( Object employeeId ) 
    {
    	this.employeeId = employeeId != null ? new Long( employeeId.toString() ) : null;
    }    

//************************************************************************
// Access Methods
//************************************************************************

// AIB : #getKeyFieldAccessMethods()
   /**
	* Returns the employeeId.
	* @return    Long
    */    
	public Long getEmployeeId()
	{
		return( this.employeeId );
	}            
	
   /**
	* Assigns the employeeId.
	* @return    Long
    */    
	public void setEmployeeId( Long id )
	{
		this.employeeId = id;
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
        
		keys.add( employeeId );

        return( keys );
    }

	public Object getFirstKey()
	{
		return( employeeId );
	}

 
//************************************************************************
// Protected / Private Methods
//************************************************************************

    
//************************************************************************
// Attributes
//************************************************************************

 	

// DO NOT ASSIGN VALUES DIRECTLY TO THE FOLLOWING ATTRIBUTES.  SET THE VALUES
// WITHIN THE Employee class.

// AIB : #getKeyFieldDeclarations()
	public Long employeeId;
// ~AIB 	        

}


