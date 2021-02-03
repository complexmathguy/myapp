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
 * @author    
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
		    new AddressTest().setHandler(logHandler).testCRUD();
		    new CompanyTest().setHandler(logHandler).testCRUD();
		    new DepartmentTest().setHandler(logHandler).testCRUD();
		    new DivisionTest().setHandler(logHandler).testCRUD();
		    new EmployeeTest().setHandler(logHandler).testCRUD();
        } catch( Throwable exc ) {
        	exc.printStackTrace();
        }
    }
	
}
