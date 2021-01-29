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
 * Department PrimaryKey class.
 * 
 * @author    
 */
// AIB : #getPrimaryKeyClassDecl() 
public class DepartmentPrimaryKey 

    extends BasePrimaryKey
{
// ~AIB

//************************************************************************
// Public Methods
//************************************************************************

    /** 
     * default constructor - should be normally used for dynamic instantiation
     */
    public DepartmentPrimaryKey() 
    {
    }    

    /** 
     * single value constructor
     */

    public DepartmentPrimaryKey( Object departmentId ) 
    {
    	this.departmentId = departmentId != null ? new Long( departmentId.toString() ) : null;
    }    

//************************************************************************
// Access Methods
//************************************************************************

// AIB : #getKeyFieldAccessMethods()
   /**
	* Returns the departmentId.
	* @return    Long
    */    
	public Long getDepartmentId()
	{
		return( this.departmentId );
	}            
	
   /**
	* Assigns the departmentId.
	* @return    Long
    */    
	public void setDepartmentId( Long id )
	{
		this.departmentId = id;
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
        
		keys.add( departmentId );

        return( keys );
    }

	public Object getFirstKey()
	{
		return( departmentId );
	}

 
//************************************************************************
// Protected / Private Methods
//************************************************************************

    
//************************************************************************
// Attributes
//************************************************************************

 	

// DO NOT ASSIGN VALUES DIRECTLY TO THE FOLLOWING ATTRIBUTES.  SET THE VALUES
// WITHIN THE Department class.

// AIB : #getKeyFieldDeclarations()
	public Long departmentId;
// ~AIB 	        

}


