/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.test;

import java.util.logging.*;

/**
 * Base class for application Test classes.
 *
 * @author    your_name_here
 */
public class BaseTest
{
	/**
	 * hidden
	 */
	protected BaseTest() {
	}
	
	public static void runTheTest( Handler logHandler ) 
    {
         try {
		    new CompanyTest().setHandler(logHandler).startTest();
		    new DepartmentTest().setHandler(logHandler).startTest();
		    new DivisionTest().setHandler(logHandler).startTest();
		    new EmployeeTest().setHandler(logHandler).startTest();
        } catch( Throwable exc ) {
        	exc.printStackTrace();
        }
    }
	
}
