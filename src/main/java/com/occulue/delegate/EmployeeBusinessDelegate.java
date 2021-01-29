/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.delegate;

import java.util.*;
import java.util.HashMap;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

    import com.occulue.primarykey.*;
    import com.occulue.dao.*;
    import com.occulue.bo.*;
    
import com.occulue.exception.*;

/**
 * Employee business delegate class.
 * <p>
 * This class implements the Business Delegate design pattern for the purpose of:
 * <ol>
 * <li>Reducing coupling between the business tier and a client of the business tier by hiding all business-tier implementation details</li>
 * <li>Improving the available of Employee related services in the case of a Employee business related service failing.</li>
 * <li>Exposes a simpler, uniform Employee interface to the business tier, making it easy for clients to consume a simple Java object.</li>
 * <li>Hides the communication protocol that may be required to fulfill Employee business related services.</li>
 * </ol>
 * <p>
 * @author 
 */
public class EmployeeBusinessDelegate 
extends BaseBusinessDelegate
{
//************************************************************************
// Public Methods
//************************************************************************
    /** 
     * Default Constructor 
     */
    public EmployeeBusinessDelegate() 
	{
	}

        
   /**
	* Employee Business Delegate Factory Method
	*
	* Returns a singleton instance of EmployeeBusinessDelegate().
	* All methods are expected to be self-sufficient.
	*
	* @return 	EmployeeBusinessDelegate
	*/
	public static EmployeeBusinessDelegate getEmployeeInstance()
	{
	    if ( singleton == null )
	    {
	    	singleton = new EmployeeBusinessDelegate();
	    }
	    
	    return( singleton );
	}
 
    /**
     * Method to retrieve the Employee via an EmployeePrimaryKey.
     * @param 	key
     * @return 	Employee
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    public Employee getEmployee( EmployeePrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "EmployeeBusinessDelegate:getEmployee - ";
        if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        Employee returnBO = null;
                
        EmployeeDAO dao = getEmployeeDAO();
        
        try
        {
            returnBO = dao.findEmployee( key );
        }
        catch( Exception exc )
        {
            String errMsg = "EmployeeBusinessDelegate:getEmployee( EmployeePrimaryKey key ) - unable to locate Employee with key " + key.toString() + " - " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseEmployeeDAO( dao );
        }        
        
        return returnBO;
    }


    /**
     * Method to retrieve a collection of all Employees
     *
     * @return 	ArrayList<Employee> 
     * @exception ProcessingException Thrown if any problems
     */
    public ArrayList<Employee> getAllEmployee() 
    throws ProcessingException
    {
    	String msgPrefix				= "EmployeeBusinessDelegate:getAllEmployee() - ";
        ArrayList<Employee> array	= null;
        
        EmployeeDAO dao = getEmployeeDAO();
    
        try
        {
            array = dao.findAllEmployee();
        }
        catch( Exception exc )
        {
            String errMsg = msgPrefix + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseEmployeeDAO( dao );
        }        
        
        return array;
    }

   /**
    * Creates the provided BO.
    * @param		businessObject 	Employee
    * @return       Employee
    * @exception    ProcessingException
    * @exception	IllegalArgumentException
    */
	public Employee createEmployee( Employee businessObject )
    throws ProcessingException, IllegalArgumentException
    {
		String msgPrefix = "EmployeeBusinessDelegate:createEmployee - ";
		
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        // return value once persisted
        EmployeeDAO dao  = getEmployeeDAO();
        
        try
        {
            businessObject = dao.createEmployee( businessObject );
        }
        catch (Exception exc)
        {
            String errMsg = "EmployeeBusinessDelegate:createEmployee() - Unable to create Employee" + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseEmployeeDAO( dao );
        }        
        
        return( businessObject );
        
    }

   /**
    * Saves the underlying BO.
    * @param		businessObject		Employee
    * @return       what was just saved
    * @exception    ProcessingException
    * @exception  	IllegalArgumentException
    */
    public Employee saveEmployee( Employee businessObject ) 
    throws ProcessingException, IllegalArgumentException
    {
    	String msgPrefix = "EmployeeBusinessDelegate:saveEmployee - ";
    	
		if ( businessObject == null )
        {
            String errMsg = msgPrefix + "null businessObject provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
                
        // --------------------------------
        // If the businessObject has a key, find it and apply the businessObject
        // --------------------------------
        EmployeePrimaryKey key = businessObject.getEmployeePrimaryKey();
                    
        if ( key != null )
        {
            EmployeeDAO dao = getEmployeeDAO();

            try
            {                    
                businessObject = (Employee)dao.saveEmployee( businessObject );
            }
            catch (Exception exc)
            {
                String errMsg = "EmployeeBusinessDelegate:saveEmployee() - Unable to save Employee" + exc;
                LOGGER.warning( errMsg );
                throw new ProcessingException( errMsg  );
            }
            finally
            {
                releaseEmployeeDAO( dao );
            }
            
        }
        else
        {
            String errMsg = "EmployeeBusinessDelegate:saveEmployee() - Unable to create Employee due to it having a null EmployeePrimaryKey."; 
            
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
		        
        return( businessObject );
        
    }
   
   /**
    * Deletes the associatied value object using the provided primary key.
    * @param		key 	EmployeePrimaryKey    
    * @exception 	ProcessingException
    */
    public void delete( EmployeePrimaryKey key ) 
    throws ProcessingException, IllegalArgumentException
    {    	
    	String msgPrefix = "EmployeeBusinessDelegate:saveEmployee - ";
    	
		if ( key == null )
        {
            String errMsg = msgPrefix + "null key provided.";
            LOGGER.warning( errMsg );
            throw new IllegalArgumentException( errMsg );
        }
        
        EmployeeDAO dao  = getEmployeeDAO();

        try
        {                    
            dao.deleteEmployee( key );
        }
        catch (Exception exc)
        {
            String errMsg = msgPrefix + "Unable to delete Employee using key = "  + key + ". " + exc;
            LOGGER.warning( errMsg );
            throw new ProcessingException( errMsg );
        }
        finally
        {
            releaseEmployeeDAO( dao );
        }
        		
        return;
    }
        

// business methods
    /**
     * Returns the Employee specific DAO.
     *
     * @return      Employee DAO
     */
    public EmployeeDAO getEmployeeDAO()
    {
        return( new com.occulue.dao.EmployeeDAO() ); 
    }

    /**
     * Release the EmployeeDAO back to the FrameworkDAOFactory
     */
    public void releaseEmployeeDAO( com.occulue.dao.EmployeeDAO dao )
    {
        dao = null;
    }

// AIB : #getBusinessMethodImplementations( $classObject.getName() $classObject $classObject.getBusinessMethods() $classObject.getInterfaces() )
// ~AIB
     
//************************************************************************
// Attributes
//************************************************************************

   /**
    * Singleton instance
    */
    protected static EmployeeBusinessDelegate singleton = null;
    private static final Logger LOGGER = Logger.getLogger(Employee.class.getName());
    
}



