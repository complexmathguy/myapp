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
 * Company PrimaryKey class.
 * 
 * @author    
 */
// AIB : #getPrimaryKeyClassDecl() 
public class CompanyPrimaryKey 

    extends BasePrimaryKey
{
// ~AIB

//************************************************************************
// Public Methods
//************************************************************************

    /** 
     * default constructor - should be normally used for dynamic instantiation
     */
    public CompanyPrimaryKey() 
    {
    }    

    /** 
     * single value constructor
     */

    public CompanyPrimaryKey( Object companyId ) 
    {
    	this.companyId = companyId != null ? new Long( companyId.toString() ) : null;
    }    

//************************************************************************
// Access Methods
//************************************************************************

// AIB : #getKeyFieldAccessMethods()
   /**
	* Returns the companyId.
	* @return    Long
    */    
	public Long getCompanyId()
	{
		return( this.companyId );
	}            
	
   /**
	* Assigns the companyId.
	* @return    Long
    */    
	public void setCompanyId( Long id )
	{
		this.companyId = id;
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
        
		keys.add( companyId );

        return( keys );
    }

	public Object getFirstKey()
	{
		return( companyId );
	}

 
//************************************************************************
// Protected / Private Methods
//************************************************************************

    
//************************************************************************
// Attributes
//************************************************************************

 	

// DO NOT ASSIGN VALUES DIRECTLY TO THE FOLLOWING ATTRIBUTES.  SET THE VALUES
// WITHIN THE Company class.

// AIB : #getKeyFieldDeclarations()
	public Long companyId;
// ~AIB 	        

}


