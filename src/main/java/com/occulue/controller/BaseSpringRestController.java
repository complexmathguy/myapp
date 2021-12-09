/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.controller;

import java.util.concurrent.atomic.AtomicLong;

/** 
 * Base class of all application Spring Controller classes.
 *
 * @author your_name_here
 */
public class BaseSpringRestController
{
	protected AtomicLong counter()
	{ return counter; }
	
	protected void logMessage( String msg )
	{
		System.out.println( msg );
	}
	
	private final AtomicLong counter = new AtomicLong();
}



