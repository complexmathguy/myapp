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

/**
 * CompanyType enumerator class.  
 * 
 * Enumerated types are handled on behalf of Hiberate as VARCHARs.  The necessary
 * methods that implement Hibernat's UserType interface assume that Enumerated
 * types contain one or more values, each named uniquely and declared (modeled) with
 * order, although the order is assumed.
 * 
// AIB : #enumDocumentation()
     * Encapsulates data for business entity CompanyType.
    // ~AIB
 * @author 
 */
public enum CompanyType
{
						S_Corp,LLC,C_Corp;

//************************************************************************
// Access Methods
//************************************************************************

    public static List<CompanyType> getValues()
    {
        return Arrays.asList(CompanyType.values());
    }


    public static CompanyType getDefaultValue()
    {
        return( S_Corp );
    }

    
//************************************************************************
// Helper Methods
//************************************************************************

//************************************************************************
// static implementations
//************************************************************************
    
    public static CompanyType whichOne( String name ) 
    {
        if ( name.equalsIgnoreCase("S_Corp" ) )
        {
            return (CompanyType.S_Corp);
        }
        if ( name.equalsIgnoreCase("LLC" ) )
        {
            return (CompanyType.LLC);
        }
        if ( name.equalsIgnoreCase("C_Corp" ) )
        {
            return (CompanyType.C_Corp);
        }
        else
        {
            return (getDefaultValue());
        }
    }

//************************************************************************
// Protected / Private Methods
//************************************************************************
    
//************************************************************************
// Attributes
//************************************************************************

}


