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
 * Industry enumerator class.  
 * 
 * Enumerated types are handled on behalf of Hiberate as VARCHARs.  The necessary
 * methods that implement Hibernat's UserType interface assume that Enumerated
 * types contain one or more values, each named uniquely and declared (modeled) with
 * order, although the order is assumed.
 * 
// AIB : #enumDocumentation()
     * Encapsulates data for business entity Industry.
    // ~AIB
 * @author 
 */
public enum Industry
{
												Bank,Insurance,Manufacturer,Technology,Health,Financial;

//************************************************************************
// Access Methods
//************************************************************************

    public static List<Industry> getValues()
    {
        return Arrays.asList(Industry.values());
    }


    public static Industry getDefaultValue()
    {
        return( Bank );
    }

    
//************************************************************************
// Helper Methods
//************************************************************************

//************************************************************************
// static implementations
//************************************************************************
    
    public static Industry whichOne( String name ) 
    {
        if ( name.equalsIgnoreCase("Bank" ) )
        {
            return (Industry.Bank);
        }
        if ( name.equalsIgnoreCase("Insurance" ) )
        {
            return (Industry.Insurance);
        }
        if ( name.equalsIgnoreCase("Manufacturer" ) )
        {
            return (Industry.Manufacturer);
        }
        if ( name.equalsIgnoreCase("Technology" ) )
        {
            return (Industry.Technology);
        }
        if ( name.equalsIgnoreCase("Health" ) )
        {
            return (Industry.Health);
        }
        if ( name.equalsIgnoreCase("Financial" ) )
        {
            return (Industry.Financial);
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


