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
 * EmploymentType enumerator class.  
 * 
 * Enumerated types are handled on behalf of Hiberate as VARCHARs.  The necessary
 * methods that implement Hibernat's UserType interface assume that Enumerated
 * types contain one or more values, each named uniquely and declared (modeled) with
 * order, although the order is assumed.
 * 
// AIB : #enumDocumentation()
     * Encapsulates data for business entity EmploymentType.
    // ~AIB
 * @author 
 */
public enum EmploymentType
{
																Manager,Board_Member,Team_Lead,Consultant,Vice_President,Sr_Mananager,Director,Engineer;

//************************************************************************
// Access Methods
//************************************************************************

    public static List<EmploymentType> getValues()
    {
        return Arrays.asList(EmploymentType.values());
    }


    public static EmploymentType getDefaultValue()
    {
        return( Manager );
    }

    
//************************************************************************
// Helper Methods
//************************************************************************

//************************************************************************
// static implementations
//************************************************************************
    
    public static EmploymentType whichOne( String name ) 
    {
        if ( name.equalsIgnoreCase("Manager" ) )
        {
            return (EmploymentType.Manager);
        }
        if ( name.equalsIgnoreCase("Board_Member" ) )
        {
            return (EmploymentType.Board_Member);
        }
        if ( name.equalsIgnoreCase("Team_Lead" ) )
        {
            return (EmploymentType.Team_Lead);
        }
        if ( name.equalsIgnoreCase("Consultant" ) )
        {
            return (EmploymentType.Consultant);
        }
        if ( name.equalsIgnoreCase("Vice_President" ) )
        {
            return (EmploymentType.Vice_President);
        }
        if ( name.equalsIgnoreCase("Sr_Mananager" ) )
        {
            return (EmploymentType.Sr_Mananager);
        }
        if ( name.equalsIgnoreCase("Director" ) )
        {
            return (EmploymentType.Director);
        }
        if ( name.equalsIgnoreCase("Engineer" ) )
        {
            return (EmploymentType.Engineer);
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


